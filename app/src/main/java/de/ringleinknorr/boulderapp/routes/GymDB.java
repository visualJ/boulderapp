package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;

@Singleton
public class GymDB {

    private Box<Gym> box;

    @Inject
    public GymDB(BoxStore boxStore) {
        this.box = boxStore.boxFor(Gym.class);
    }

    public Gym getGymWithName(String name) {
        return box.query().equal(Gym_.name, name).build().findFirst();
    }

    public LiveData<List<Gym>> getAllGyms() {
        return new ObjectBoxLiveData<>(box.query().build());
    }

    public LiveData<List<String>> getAllGymNames() {
        MediatorLiveData<List<String>> liveData = new MediatorLiveData<>();
        liveData.addSource(new ObjectBoxLiveData<>(box.query().build()), gyms -> {
            List<String> gymNames = new ArrayList<>();
            if (gyms != null) {
                for (Gym gym : gyms) {
                    gymNames.add(gym.getName());
                }
            }
            liveData.postValue(gymNames);
        });
        return liveData;
    }
}
