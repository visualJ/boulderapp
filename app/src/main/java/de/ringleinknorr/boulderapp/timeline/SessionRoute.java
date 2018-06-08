package de.ringleinknorr.boulderapp.timeline;

import de.ringleinknorr.boulderapp.routes.Route;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class SessionRoute {
    @Id
    private long id;
    private ToOne<Route> route;
    private ToOne<Session> session;

    public SessionRoute() {
    }

    public SessionRoute(long routeId, long sessionId) {
        this.route.setTargetId(routeId);
        this.session.setTargetId(sessionId);
    }

    public SessionRoute(Route route, Session session) {
        this.route.setTarget(route);
        this.session.setTarget(session);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToOne<Route> getRoute() {
        return route;
    }

    public void setRoute(ToOne<Route> route) {
        this.route = route;
    }

    public ToOne<Session> getSession() {
        return session;
    }

    public void setSession(ToOne<Session> session) {
        this.session = session;
    }
}
