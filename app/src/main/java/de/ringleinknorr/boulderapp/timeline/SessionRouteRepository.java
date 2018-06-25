package de.ringleinknorr.boulderapp.timeline;

import javax.inject.Inject;

public class SessionRouteRepository {

    private SessionRouteDB sessionRouteDB;

    @Inject
    public SessionRouteRepository(SessionRouteDB sessionRouteDB) {
        this.sessionRouteDB = sessionRouteDB;
    }

    public long putSessionRoute(SessionRoute sessionRoute) {
        return sessionRouteDB.putSessionRoute(sessionRoute);
    }
}
