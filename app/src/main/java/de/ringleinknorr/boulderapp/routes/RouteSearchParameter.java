package de.ringleinknorr.boulderapp.routes;

public class RouteSearchParameter {
    private int minLevel;
    private int maxLevel;
    private String gymName;
    private int sector;

    public RouteSearchParameter(int minLevel, int maxLevel, String gymName) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.gymName = gymName;
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

    public int getSector() {
        return sector;
    }
}
