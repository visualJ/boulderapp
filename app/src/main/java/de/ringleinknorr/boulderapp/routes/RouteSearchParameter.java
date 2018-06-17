package de.ringleinknorr.boulderapp.routes;

public class RouteSearchParameter {
    private int minLevel;
    private int maxLevel;
    private String gymName;
    private long sectorId;

    public RouteSearchParameter(int minLevel, int maxLevel, String gymName, long sectorId) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.gymName = gymName;
        this.sectorId = sectorId;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getGymName() {
        return gymName;
    }

    public long getSectorId() {
        return sectorId;
    }
}
