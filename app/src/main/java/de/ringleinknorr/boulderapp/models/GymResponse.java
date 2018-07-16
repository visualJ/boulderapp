package de.ringleinknorr.boulderapp.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * A server response containing gyms and related objects
 */
public class GymResponse {

    @Expose
    private List<Gym> gyms;
    @Expose
    private List<GymSector> gymSectors;
    @Expose
    private List<GymSectorCoord> gymSectorCoords;
    @Expose
    private List<RouteLevel> routeLevels;

    public GymResponse(List<Gym> gyms, List<GymSector> gymSectors, List<GymSectorCoord> gymSectorCoords, List<RouteLevel> routeLevels) {
        this.gyms = gyms;
        this.gymSectors = gymSectors;
        this.gymSectorCoords = gymSectorCoords;
        this.routeLevels = routeLevels;
    }

    public List<Gym> getGyms() {

        return gyms;
    }

    public void setGyms(List<Gym> gyms) {
        this.gyms = gyms;
    }

    public List<GymSector> getGymSectors() {
        return gymSectors;
    }

    public void setGymSectors(List<GymSector> gymSectors) {
        this.gymSectors = gymSectors;
    }

    public List<GymSectorCoord> getGymSectorCoords() {
        return gymSectorCoords;
    }

    public void setGymSectorCoords(List<GymSectorCoord> gymSectorCoords) {
        this.gymSectorCoords = gymSectorCoords;
    }

    public List<RouteLevel> getRouteLevels() {
        return routeLevels;
    }

    public void setRouteLevels(List<RouteLevel> routeLevels) {
        this.routeLevels = routeLevels;
    }

}
