package de.ringleinknorr.boulderapp.routes;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToMany;

@Entity
public class Gym {
    @Id
    private long id;
    @Index
    private String name;
    private String imageId;

    @Backlink
    private ToMany<Route> routes;
    @Backlink
    private ToMany<GymSector> gymSectors;
    @Backlink
    private ToMany<RouteLevel> routeLevels;

    public Gym() {

    }

    public Gym(String name, String imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToMany<Route> getRoutes() {
        return routes;
    }

    public ToMany<GymSector> getGymSectors() {
        return gymSectors;
    }

    public void setGymSectors(ToMany<GymSector> gymSectors) {
        this.gymSectors = gymSectors;
    }

    public ToMany<RouteLevel> getRouteLevels() {
        return routeLevels;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
