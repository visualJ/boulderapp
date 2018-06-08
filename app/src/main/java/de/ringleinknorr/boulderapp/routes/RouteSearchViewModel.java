package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

public class RouteSearchViewModel extends ViewModel {
    private MediatorLiveData<List<Route>> routes;
    private RouteRepository routeRepository;

    @Inject
    public RouteSearchViewModel(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
        routes = new MediatorLiveData<>();
    }

    public LiveData<List<Route>> getRoutes() {
        return routes;
    }

    public void queryRoutes(RouteSearchParameter routeSearchParameter) {
        routes.addSource(routeRepository.queryRoutes(routeSearchParameter), routeList -> routes.postValue(routeList));
    }

}
