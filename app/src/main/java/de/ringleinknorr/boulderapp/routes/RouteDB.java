package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

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
        if (box.count() == 0) {
            box.put(new Route(Route.Level.LEICHT, new Gym("Kletterhalle Wiesbaden")));
            box.put(new Route(Route.Level.SCHWER, new Gym("Wiesbadener Nordwand")));
            box.put(new Route(Route.Level.MITTEL, new Gym("Kletterhalle Wiesbaden")));
        }
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
        int minLevel = routeSearchParameter.getMinLevel();
        int maxLevel = routeSearchParameter.getMaxLevel();
        String gymName = routeSearchParameter.getGymName();
        MediatorLiveData<List<Route>> liveData = new MediatorLiveData<>();
        QueryBuilder<Route> builder = box.query();
        builder.between(Route_.level, minLevel, maxLevel).filter((route) -> route.getGym().getTarget().getName().equals(gymName));
        LiveData<List<Route>> query = new ObjectBoxLiveData<>(builder.build());
        liveData.addSource(query, list -> liveData.postValue(list));
        return liveData;
    }
}
