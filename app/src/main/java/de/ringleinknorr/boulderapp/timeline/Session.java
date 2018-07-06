package de.ringleinknorr.boulderapp.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.ringleinknorr.boulderapp.routes.Gym;
import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Session {
    @Id
    private long id;
    private Date date;
    private ToOne<Gym> gym;
    @Backlink
    private ToMany<SessionRoute> routes;

    public Session(Date date, Gym gym) {
        this.date = date;
        this.gym.setTarget(gym);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return id == session.id;
    }

    @Override
    public int hashCode() {
        return (int) id;
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

    public ToMany<SessionRoute> getRoutes() {
        return routes;
    }

    public List<SessionRoute> getSuccessfulSessionRoutes() {
        List<SessionRoute> routes = new ArrayList<>();
        for (SessionRoute route : getRoutes()) {
            if (route.getResult() == SessionRoute.Result.SUCCESS) {
                routes.add(route);
            }
        }
        return routes;
    }
}
