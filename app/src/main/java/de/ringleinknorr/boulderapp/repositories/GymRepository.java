package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.Gym;

@Singleton
public class GymRepository {

    private GymDB db;

    @Inject
    public GymRepository(GymDB db) {
        this.db = db;
    }

    public Gym getGymWithName(String name) {
        return db.getGymWithName(name);
    }

    public LiveData<List<Gym>> getAllGyms() {
        return db.getAllGyms();
    }

    public LiveData<List<String>> getAllGymNames() {
        return db.getAllGymNames();
    }
}
