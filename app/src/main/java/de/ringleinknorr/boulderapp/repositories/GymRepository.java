package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.Gym;
import de.ringleinknorr.boulderapp.models.GymResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class GymRepository {

    private GymDB db;
    private GymService gymService;

    @Inject
    public GymRepository(GymDB db, GymService gymService) {
        this.db = db;
        this.gymService = gymService;
    }

    /**
     * Get a gym instance with the given name.
     *
     * @param name The gyms name.
     * @return The gym or null, if no gym with the given name exists.
     */
    public Gym getGymWithName(String name) {
        return db.getGymWithName(name);
    }

    /**
     * Get a list of all gym names
     *
     * @return A live list of all gym names.
     */
    public LiveData<List<String>> getAllGymNames() {
        gymService.getGyms().enqueue(new Callback<GymResponse>() {
            @Override
            public void onResponse(@NonNull Call<GymResponse> call, @NonNull Response<GymResponse> response) {
                GymResponse gymResponse = response.body();
                if (gymResponse != null) {
                    db.putGyms(gymResponse.getGyms());
                    db.putGymSectors(gymResponse.getGymSectors());
                    db.putGymSectorCoords(gymResponse.getGymSectorCoords());
                    db.putRouteLevels(gymResponse.getRouteLevels());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GymResponse> call, @NonNull Throwable t) {
                Log.e(getClass().getSimpleName(), "onFailure: " + t.getLocalizedMessage(), t);
            }
        });
        return db.getAllGymNames();
    }
}
