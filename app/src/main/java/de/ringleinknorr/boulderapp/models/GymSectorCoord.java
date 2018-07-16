package de.ringleinknorr.boulderapp.models;

import com.google.gson.annotations.Expose;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class GymSectorCoord {
    @Id(assignable = true)
    @Expose
    private long id;

    @Expose
    private int x;
    @Expose
    private int y;
    @Expose
    private long gymSectorId;

    private ToOne<GymSector> gymSector;

    public GymSectorCoord() {
    }

    public GymSectorCoord(int x, int y, GymSector gymSector) {
        this.x = x;
        this.y = y;
        this.gymSector.setTarget(gymSector);
    }

    public GymSectorCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public ToOne<GymSector> getGymSector() {
        return gymSector;
    }

    public void setGymSector(ToOne<GymSector> gymSector) {
        this.gymSector = gymSector;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGymSectorId() {
        return gymSectorId;
    }

    public void setGymSectorId(long gymSectorId) {
        this.gymSectorId = gymSectorId;
    }
}
