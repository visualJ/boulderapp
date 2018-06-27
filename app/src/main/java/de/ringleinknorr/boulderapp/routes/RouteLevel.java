package de.ringleinknorr.boulderapp.routes;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class RouteLevel {
    @Id
    private long id;
    private String levelName;
    private int levelColor;
    private int levelNumber;
    @Backlink
    private ToMany<Route> routes;
    private ToOne<Gym> gym;

    public RouteLevel(String levelName, int levelNumber, int levelColor, Gym gym) {
        this.levelName = levelName;
        this.levelColor = levelColor;
        this.levelNumber = levelNumber;
        this.gym.setTarget(gym);
    }

    public RouteLevel() {
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getLevelColor() {
        return levelColor;
    }

    public void setLevelColor(int levelColor) {
        this.levelColor = levelColor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToMany<Route> getRoutes() {
        return routes;
    }

    public ToOne<Gym> getGym() {
        return gym;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
