package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class GymRepository {

    private List<Gym> mockGyms;

    @Inject
    public GymRepository() {
        mockGyms = Arrays.asList(new Gym("Wiesbadener Nordwand"), new Gym("Kletterhalle Wiesbaden"));
    }

    public LiveData<List<Gym>> getAllGyms() {
        MutableLiveData<List<Gym>> gymData = new MutableLiveData<>();
        gymData.postValue(mockGyms);
        return gymData;
    }

    public LiveData<List<String>> getAllGymNames() {
        MutableLiveData<List<String>> gymData = new MutableLiveData<>();
        List<String> gyms = new ArrayList<>();
        for (Gym gym : mockGyms) {
            gyms.add(gym.getName());
        }
        gymData.postValue(gyms);
        return gymData;
    }
}
