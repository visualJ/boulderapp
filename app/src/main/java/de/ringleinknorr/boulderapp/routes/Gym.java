package de.ringleinknorr.boulderapp.routes;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Gym {
    @Id
    private long id;
    private String name;

    public Gym() {

    }

    public Gym(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
