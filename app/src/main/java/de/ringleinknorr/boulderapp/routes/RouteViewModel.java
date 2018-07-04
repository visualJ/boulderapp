package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class RouteViewModel extends ViewModel {

    private long routeId;
    private LiveData<Route> route;

    private RouteRepository routeRepository;

    @Inject
    public RouteViewModel(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void init(long routeId) {
        if (this.route == null) {
            this.routeId = routeId;
            this.route = routeRepository.getRoute(routeId);
        }
    }

    public long getRouteId() {
        return routeId;
    }

    public LiveData<Route> getRoute() {
        return route;
    }
}
