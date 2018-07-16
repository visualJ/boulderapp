package de.ringleinknorr.boulderapp.models;

import com.google.gson.annotations.Expose;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class GymSector {
    @Id(assignable = true)
    @Expose
    private long id;
    @Expose
    private long gymId;
    private ToOne<Gym> gym;
    @Backlink
    private ToMany<GymSectorCoord> gymSectorCoords;

    public GymSector(Gym gym) {
        this.gym.setTarget(gym);
    }

    public GymSector() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymSector sector = (GymSector) o;
        return id == sector.id;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    public ToMany<GymSectorCoord> getGymSectorCoords() {
        return gymSectorCoords;
    }

    public void setGymSectorCoords(ToMany<GymSectorCoord> gymSectorCoords) {
        this.gymSectorCoords = gymSectorCoords;
    }

    public ToOne<Gym> getGym() {
        return gym;
    }

    public void setGym(ToOne<Gym> gym) {
        this.gym = gym;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGymId() {
        return gymId;
    }

    public void setGymId(long gymId) {
        this.gymId = gymId;
    }
}
