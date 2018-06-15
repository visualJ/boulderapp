package de.ringleinknorr.boulderapp.routes;

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
