package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.QueryBuilder;

public class RouteDB {
    private Box<Route> box;

    @Inject
    public RouteDB(BoxStore boxStore) {
        this.box = boxStore.boxFor(Route.class);
    }

    public LiveData<List<Route>> getAllRoutes() {
        return new ObjectBoxLiveData<>(box.query().build());
    }

    public LiveData<Route> getRoute(long routeId) {
        MediatorLiveData<Route> liveData = new MediatorLiveData<>();
        LiveData<List<Route>> query = new ObjectBoxLiveData<>(box.query().equal(Route_.id, routeId).build());
        liveData.addSource(query, list -> liveData.postValue(list.get(0)));
        return liveData;
    }

    public LiveData<List<Route>> queryRoutes(RouteSearchParameter routeSearchParameter) {
        String gymName = routeSearchParameter.getGymName();
        Long gymSectorId = routeSearchParameter.getSectorId();
        MediatorLiveData<List<Route>> liveData = new MediatorLiveData<>();
        QueryBuilder<Route> builder = box.query();
        List<RouteLevel> routeLevels = routeSearchParameter.getRouteLevels();

        if (gymSectorId != null) {
            builder.equal(Route_.gymSectorId, gymSectorId);
        }

        List<String> routeLevelNames = new ArrayList<>();
        if (routeSearchParameter.getRouteLevels() != null && !routeSearchParameter.getRouteLevels().isEmpty()) {
            for (RouteLevel level : routeSearchParameter.getRouteLevels()) {
                routeLevelNames.add(level.getLevelName());

            }
            builder.filter((route) -> route.getGym().getTarget().getName().equals(gymName) && routeLevelNames.contains(route.getRouteLevel().getTarget().getLevelName()));
        } else {
            builder.filter((route) -> route.getGym().getTarget().getName().equals(gymName));
        }

        LiveData<List<Route>> query = new ObjectBoxLiveData<>(builder.build());
        liveData.addSource(query, liveData::postValue);
        return liveData;
    }
}
