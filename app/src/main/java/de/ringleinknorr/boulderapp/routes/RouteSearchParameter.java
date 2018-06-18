package de.ringleinknorr.boulderapp.routes;

public class RouteSearchParameter {
    private int minLevel;
    private int maxLevel;
    private String gymName;
    private Long sectorId;

    public RouteSearchParameter(int minLevel, int maxLevel, String gymName, Long sectorId) {
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

    public Long getSectorId() {
        return sectorId;
    }
}
