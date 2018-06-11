package de.ringleinknorr.boulderapp;

import de.ringleinknorr.boulderapp.routes.Gym;
import de.ringleinknorr.boulderapp.routes.Route;
import de.ringleinknorr.boulderapp.timeline.Session;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class DBMockData {

    public static void createMockData(BoxStore boxStore) {

        Box<Session> sessionBox = boxStore.boxFor(Session.class);
        Box<Route> routeBox = boxStore.boxFor(Route.class);

        if (routeBox.count() <= 0) {
            Gym gym1 = new Gym("Wiesbadener Nordwand");
            Gym gym2 = new Gym("Kletterhalle Wiesbaden");
            Gym gym3 = new Gym("Blockwerk Mainz");

            Route routes[] = {
                    new Route(Route.Level.LEICHT, gym1),
                    new Route(Route.Level.MITTEL, gym1),
                    new Route(Route.Level.SCHWER, gym1),
                    new Route(Route.Level.MITTEL, gym2),
                    new Route(Route.Level.SCHWER, gym2),
                    new Route(Route.Level.MITTEL, gym3),
            };

            routeBox.put(routes);
        }

    }

}
