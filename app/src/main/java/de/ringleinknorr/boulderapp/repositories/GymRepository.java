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
     * Get a list of all gyms.
     * @return A live list of all gyms that is updated, when gyms are added or removed.
     */
    public LiveData<List<Gym>> getAllGyms() {
        return db.getAllGyms();
    }

    /**
     * Get a list of all gym names
     * @return A live list of all gym names.
     */
    public LiveData<List<String>> getAllGymNames() {
        return db.getAllGymNames();
    }
}
