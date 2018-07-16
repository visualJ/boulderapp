package de.ringleinknorr.boulderapp.repositories;

import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.GymResponse;
import retrofit2.Call;
import retrofit2.http.GET;

@Singleton
public interface GymService {

    @GET("gyms.json")
    Call<GymResponse> getGyms();

}
