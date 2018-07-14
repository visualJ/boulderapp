package de.ringleinknorr.boulderapp.models;

import java.util.List;

public class RouteSearchParameter {
    private String gymName;
    private long gymId;
    private Long sectorId;
    private List<RouteLevel> routeLevels;

    public RouteSearchParameter(long gymId, Long sectorId, List<RouteLevel> routeLevels) {
        this.gymId = gymId;
        this.sectorId = sectorId;
        this.routeLevels = routeLevels;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public List<RouteLevel> getRouteLevels() {
        return routeLevels;
    }

    public long getGymId() {
        return gymId;
    }
}
