package de.ringleinknorr.boulderapp.repositories;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.SessionRoute;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.relation.ToMany;

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

    public void putSessionRoutes(ToMany<SessionRoute> routes) {
        box.put(routes);
    }
}
