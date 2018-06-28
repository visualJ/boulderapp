package de.ringleinknorr.boulderapp.timeline;

import javax.inject.Inject;

import io.objectbox.relation.ToMany;

public class SessionRouteRepository {

    private SessionRouteDB sessionRouteDB;

    @Inject
    public SessionRouteRepository(SessionRouteDB sessionRouteDB) {
        this.sessionRouteDB = sessionRouteDB;
    }

    public long putSessionRoute(SessionRoute sessionRoute) {
        return sessionRouteDB.putSessionRoute(sessionRoute);
    }

    public void putSessionRoutes(ToMany<SessionRoute> routes) {
        sessionRouteDB.putSessionRoutes(routes);
    }
}
