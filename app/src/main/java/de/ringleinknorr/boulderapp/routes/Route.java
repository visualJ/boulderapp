package de.ringleinknorr.boulderapp.routes;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Route {
    @Id
    private long id;
    private String level;

    public long getId() {
        return id;
    }

    public Route(String level) {
        this.level = level;
    }

    public Route() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }
}
