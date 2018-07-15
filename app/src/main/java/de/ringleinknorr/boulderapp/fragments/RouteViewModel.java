package de.ringleinknorr.boulderapp.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.models.Route;
import de.ringleinknorr.boulderapp.repositories.RouteRepository;

/**
 * The view model for {@link RouteFragment}.
 */
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
