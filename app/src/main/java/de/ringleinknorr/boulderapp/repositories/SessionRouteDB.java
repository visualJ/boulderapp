package de.ringleinknorr.boulderapp.repositories;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.SessionRoute;
import io.objectbox.Box;
import io.objectbox.BoxStore;

@Singleton
public class SessionRouteDB {

    private Box<SessionRoute> box;

    @Inject
    public SessionRouteDB(BoxStore boxStore) {
        this.box = boxStore.boxFor(SessionRoute.class);
    }

    public long putSessionRoute(SessionRoute sessionRoute) {
        return box.put(sessionRoute);
    }

    public void putSessionRoutes(List<SessionRoute> routes) {
        box.put(routes);
    }
}
