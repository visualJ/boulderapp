package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.Gym;
import de.ringleinknorr.boulderapp.models.GymSector;
import de.ringleinknorr.boulderapp.models.GymSectorCoord;
import de.ringleinknorr.boulderapp.models.Gym_;
import de.ringleinknorr.boulderapp.models.RouteLevel;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;

@Singleton
public class GymDB {

    private Box<Gym> gymBox;
    private Box<GymSector> gymSectorBox;
    private Box<GymSectorCoord> gymSectorCoordBox;
    private Box<RouteLevel> routeLevelBox;

    @Inject
    public GymDB(BoxStore boxStore) {
        this.gymBox = boxStore.boxFor(Gym.class);
        this.gymSectorBox = boxStore.boxFor(GymSector.class);
        this.gymSectorCoordBox = boxStore.boxFor(GymSectorCoord.class);
        this.routeLevelBox = boxStore.boxFor(RouteLevel.class);
    }

    public Gym getGymWithName(String name) {
        return gymBox.query().equal(Gym_.name, name).build().findFirst();
    }

    public LiveData<List<String>> getAllGymNames() {
        MediatorLiveData<List<String>> liveData = new MediatorLiveData<>();
        liveData.addSource(new ObjectBoxLiveData<>(gymBox.query().build()), gyms -> {
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

    public void putGyms(List<Gym> gyms) {
        gymBox.put(gyms);
    }

    public void putGymSectors(List<GymSector> gymSectors) {
        gymSectorBox.put(gymSectors);
    }

    public void putGymSectorCoords(List<GymSectorCoord> gymSectorCoords) {
        gymSectorCoordBox.put(gymSectorCoords);
    }

    public void putRouteLevels(List<RouteLevel> routeLevels) {
        routeLevelBox.put(routeLevels);
    }
}
