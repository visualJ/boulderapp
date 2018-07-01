package de.ringleinknorr.boulderapp.routes;

import java.util.Objects;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class GymSector {
    @Id
    private long id;
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
}
