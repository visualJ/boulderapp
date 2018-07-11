package de.ringleinknorr.boulderapp.models;

import java.util.List;

public class RouteSearchParameter {
    private String gymName;
    private Long sectorId;
    private List<RouteLevel> routeLevels;

    public RouteSearchParameter(String gymName, Long sectorId, List<RouteLevel> routeLevels) {
        this.gymName = gymName;
        this.sectorId = sectorId;
        this.routeLevels = routeLevels;
    }

    public String getGymName() {
        return gymName;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public List<RouteLevel> getRouteLevels() {
        return routeLevels;
    }
}
