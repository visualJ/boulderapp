package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.Route;
import de.ringleinknorr.boulderapp.models.RouteLevel;
import de.ringleinknorr.boulderapp.models.RouteLevel_;
import de.ringleinknorr.boulderapp.models.RouteSearchParameter;
import de.ringleinknorr.boulderapp.models.Route_;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.QueryBuilder;

@Singleton
public class RouteDB {
    private Box<Route> box;

    @Inject
    public RouteDB(BoxStore boxStore) {
        this.box = boxStore.boxFor(Route.class);
    }

    public LiveData<List<Route>> getAllRoutes() {
        return new ObjectBoxLiveData<>(box.query().build());
    }

    public void putRoutes(List<Route> routes) {
        box.put(routes);
    }

    public LiveData<Route> getRoute(long routeId) {
        MediatorLiveData<Route> liveData = new MediatorLiveData<>();
        LiveData<List<Route>> query = new ObjectBoxLiveData<>(box.query().equal(Route_.id, routeId).build());
        liveData.addSource(query, list -> liveData.postValue(list.get(0)));
        return liveData;
    }

    public LiveData<List<Route>> queryRoutes(RouteSearchParameter routeSearchParameter) {
        long gymId = routeSearchParameter.getGymId();
        Long gymSectorId = routeSearchParameter.getSectorId();
        List<RouteLevel> routeLevels = routeSearchParameter.getRouteLevels();
        QueryBuilder<Route> builder = box.query();

        if (gymSectorId != null) {
            builder.equal(Route_.gymSectorId, gymSectorId);
        }

        builder.equal(Route_.gymId, gymId);

        if (routeLevels != null && !routeLevels.isEmpty()) {
            long levelIds[] = new long[routeLevels.size()];
            for (int i = 0; i < routeLevels.size(); i++) {
                levelIds[i] = routeLevels.get(i).getId();
            }
            builder.link(Route_.routeLevel).notIn(RouteLevel_.__ID_PROPERTY, levelIds);
        }

        return new ObjectBoxLiveData<>(builder.build());
    }
}
