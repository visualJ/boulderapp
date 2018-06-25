package de.ringleinknorr.boulderapp.routes;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Route {
    @Id
    private long id;

    private ToOne<Gym> gym;
    private String imageId;
    private long gymSectorId;

    private ToOne<RouteLevel> routeLevel;

    public Route(Gym gym, String imageId, long gymSectorId, RouteLevel routeLevel) {
        this.routeLevel.setTarget(routeLevel);
        this.gym.setTarget(gym);
        this.imageId = imageId;
        this.gymSectorId = gymSectorId;
    }

    public Route() {

    }

    public ToOne<RouteLevel> getRouteLevel() {
        return routeLevel;
    }

    public void setRouteLevel(ToOne<RouteLevel> routeLevel) {
        this.routeLevel = routeLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToOne<Gym> getGym() {
        return gym;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public long getGymSectorId() {
        return gymSectorId;
    }

    public void setGymSectorId(long gymSectorId) {
        this.gymSectorId = gymSectorId;
    }

}
