package de.ringleinknorr.boulderapp.routes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.ringleinknorr.boulderapp.timeline.Session;
import de.ringleinknorr.boulderapp.timeline.SessionRoute;
import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Route {
    @Id
    private long id;

    private ToOne<Gym> gym;
    private String imageId;
    private ToOne<GymSector> gymSector;
    private ToOne<RouteLevel> routeLevel;
    @Backlink
    private ToMany<SessionRoute> sessionRoutes;

    public Route(Gym gym, String imageId, long gymSectorId, RouteLevel routeLevel) {
        this.routeLevel.setTarget(routeLevel);
        this.gym.setTarget(gym);
        this.imageId = imageId;
        this.gymSector.setTargetId(gymSectorId);
    }

    public Route() {

    }

    /**
     * Retrieve a list of distinct sessions that contain this route.
     *
     * @return A list of sessions this route is referenced in
     */
    public List<Session> getSessions() {
        Set<Session> sessionSet = new HashSet<>();
        for (SessionRoute sessionRoute : getSessionRoutes()) {
            Session session = sessionRoute.getSession().getTarget();
            if (session != null) {
                sessionSet.add(session);
            }
        }
        return new ArrayList<>(sessionSet);
    }

    public ToOne<GymSector> getGymSector() {
        return gymSector;
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

    public ToMany<SessionRoute> getSessionRoutes() {
        return sessionRoutes;
    }
}
