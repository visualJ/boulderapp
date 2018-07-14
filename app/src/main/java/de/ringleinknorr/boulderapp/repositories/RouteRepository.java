package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.models.Route;
import de.ringleinknorr.boulderapp.models.RouteSearchParameter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteRepository {
    private RouteDB db;
    private RouteService routeService;

    @Inject
    public RouteRepository(RouteDB db, RouteService routeService) {
        this.db = db;
        this.routeService = routeService;
    }

    public LiveData<List<Route>> getAllRoutes() {
        return db.getAllRoutes();
    }

    public LiveData<Route> getRoute(long routeId) {
        return db.getRoute(routeId);
    }

    public LiveData<List<Route>> queryRoutes(RouteSearchParameter routeSearchParameter) {
        routeService.getRoutes(routeSearchParameter.getGymId()).enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                db.putRoutes(response.body());
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "onFailure: " + t.getLocalizedMessage(), t);
            }
        });
        return db.queryRoutes(routeSearchParameter);
    }
}
