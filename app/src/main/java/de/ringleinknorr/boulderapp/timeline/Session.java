package de.ringleinknorr.boulderapp.timeline;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Session {
    @Id
    private long id;
    private Date date;

    public Session(long id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Session() {
        // empty constructor for serialization
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
