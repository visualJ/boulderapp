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

    /**
     * Retrieve all routes in the db.
     *
     * @return All routes.
     */
    public LiveData<List<Route>> getAllRoutes() {
        return db.getAllRoutes();
    }

    /**
     * Get a specific route.
     * @param routeId The id of the route to retrieve.
     * @return The route or null, if no route with the given id exists.
     */
    public LiveData<Route> getRoute(long routeId) {
        return db.getRoute(routeId);
    }

    /**
     * Query for routes with certain parameters. Load routes from service first, if available.
     * @param routeSearchParameter The parameters to search for.
     * @return A live list of matching routes.
     */
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
