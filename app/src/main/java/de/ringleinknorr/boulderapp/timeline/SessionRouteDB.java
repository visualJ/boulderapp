package de.ringleinknorr.boulderapp.timeline;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.relation.ToMany;

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
