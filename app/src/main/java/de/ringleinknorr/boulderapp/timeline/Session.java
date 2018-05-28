package de.ringleinknorr.boulderapp.timeline;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Session {
    @Id private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
