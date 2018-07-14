package de.ringleinknorr.boulderapp.repositories;

import java.util.List;

import de.ringleinknorr.boulderapp.models.Route;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RouteService {

    @GET("routes/{gym}.json")
    Call<List<Route>> getRoutes(@Path("gym") long gymId);

}
