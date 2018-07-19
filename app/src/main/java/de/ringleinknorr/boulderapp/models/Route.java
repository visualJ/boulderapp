package de.ringleinknorr.boulderapp.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Route implements Comparable<Route> {
    @Id(assignable = true)
    private long id;

    private String imageId;
    private long gymId;
    private long gymSectorId;
    private long routeLevelId;
    private ToOne<Gym> gym;
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
        // Required empty constructor
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

    /**
     * Get the number of times this route was completed in a session.
     *
     * @return The count this route was completed as an int
     */
    public int getRouteCompletedCount(){
        int routeCompletedCount = 0;
        for(SessionRoute sessionRoute : getSessionRoutes()){
            if (sessionRoute.getResult() == SessionRoute.Result.SUCCESS){
                routeCompletedCount++;
            }
        }
        return routeCompletedCount;
    }

    /**
     * Get the count of how often this route is included in a session.
     *
     * @return The count this route is included in a session as an int
     */
    public int getRouteAddedCount(){
        return getSessionRoutes().size();
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

    @Override
    public int compareTo(@NonNull Route route) {
        return getRouteLevel().getTarget().getLevelNumber() - route.getRouteLevel().getTarget().getLevelNumber();
    }

    public long getGymId() {
        return gymId;
    }

    public void setGymId(long gymId) {
        this.gymId = gymId;
    }

    public long getGymSectorId() {
        return gymSectorId;
    }

    public void setGymSectorId(long gymSectorId) {
        this.gymSectorId = gymSectorId;
    }

    public long getRouteLevelId() {
        return routeLevelId;
    }

    public void setRouteLevelId(long routeLevelId) {
        this.routeLevelId = routeLevelId;
    }
}
