package de.ringleinknorr.boulderapp.routes;

public class RouteSearchParameter {
    int minLevel;
    int maxLevel;
    String gym;
    int sector;

    public RouteSearchParameter(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }
}
