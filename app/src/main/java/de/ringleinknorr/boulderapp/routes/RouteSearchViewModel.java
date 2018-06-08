package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

public class RouteSearchViewModel extends ViewModel {
    private LiveData<List<Route>> routes;
    private RouteRepository routeRepository;

    @Inject
    public RouteSearchViewModel(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
        routes = routeRepository.getAllRoutes();
    }

    public LiveData<List<Route>> getRoutes() {
        return routes;
    }

}
