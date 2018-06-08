package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;

public class RouteDB {
    private Box<Route> box;

    @Inject
    public RouteDB(BoxStore boxStore) {
        this.box = boxStore.boxFor(Route.class);
        if (box.count() == 0) {
            box.put(new Route("Leicht"));
            box.put(new Route("Schwer"));
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
}
