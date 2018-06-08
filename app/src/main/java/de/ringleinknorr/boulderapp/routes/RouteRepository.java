package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

public class RouteRepository {
    private RouteDB db;

    @Inject
    public RouteRepository(RouteDB db) {
        this.db = db;
    }

    public LiveData<List<Route>> getAllRoutes() {
        return db.getAllRoutes();
    }

    public LiveData<Route> getRoute(long routeId) {
        return db.getRoute(routeId);
    }

    public LiveData<List<Route>> queryRoutes(RouteSearchParameter routeSearchParameter) {
        return db.queryRoutes(routeSearchParameter);
    }
}
