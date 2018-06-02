package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class GymRepository {

    @Inject
    public GymRepository() {
    }

    public LiveData<List<Gym>> getAllGyms() {
        MutableLiveData<List<Gym>> gymData = new MutableLiveData<>();
        List<Gym> gyms = Arrays.asList(new Gym("Wiesbadener Nordwand"), new Gym("Kletterhalle Wiesbaden"));
        gymData.postValue(gyms);
        return gymData;
    }
}
