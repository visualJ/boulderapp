package de.ringleinknorr.boulderapp.repositories;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.SessionRoute;

@Singleton
public class SessionRouteRepository {

    private SessionRouteDB sessionRouteDB;

    @Inject
    public SessionRouteRepository(SessionRouteDB sessionRouteDB) {
        this.sessionRouteDB = sessionRouteDB;
    }

    /**
     * Save a session route (route in a session with additional meta data).
     *
     * @param sessionRoute The session route to put into the db.
     * @return The session route id.
     */
    public long putSessionRoute(SessionRoute sessionRoute) {
        return sessionRouteDB.putSessionRoute(sessionRoute);
    }

    /**
     * Save multiple session routes. This is more efficient than using putSessionRoute multiple times.
     *
     * @param routes The routes to save.
     */
    public void putSessionRoutes(List<SessionRoute> routes) {
        sessionRouteDB.putSessionRoutes(routes);
    }
}
