package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.models.Route;
import de.ringleinknorr.boulderapp.models.RouteSearchParameter;

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
