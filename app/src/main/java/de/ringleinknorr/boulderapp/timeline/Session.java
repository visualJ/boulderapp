package de.ringleinknorr.boulderapp.timeline;

import java.util.Date;

import de.ringleinknorr.boulderapp.routes.Gym;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Session {
    @Id
    private long id;
    private Date date;
    private ToOne<Gym> gym;

    public Session(Date date, Gym gym) {
        this.date = date;
        this.gym.setTarget(gym);
    }

    public Session() {
        // empty constructor for serialization
    }

    public ToOne<Gym> getGym() {
        return gym;
    }

    public void setGym(ToOne<Gym> gym) {
        this.gym = gym;
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
