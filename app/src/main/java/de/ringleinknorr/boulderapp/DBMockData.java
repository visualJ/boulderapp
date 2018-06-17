package de.ringleinknorr.boulderapp;

import de.ringleinknorr.boulderapp.routes.Gym;
import de.ringleinknorr.boulderapp.routes.GymSector;
import de.ringleinknorr.boulderapp.routes.GymSectorCoord;
import de.ringleinknorr.boulderapp.routes.Route;
import de.ringleinknorr.boulderapp.timeline.Session;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class DBMockData {

    public static void createMockData(BoxStore boxStore) {

        Box<Session> sessionBox = boxStore.boxFor(Session.class);
        Box<Route> routeBox = boxStore.boxFor(Route.class);
        Box<GymSectorCoord> gymSectorCoordsBox = boxStore.boxFor(GymSectorCoord.class);


        if (routeBox.count() <= 0) {
            Gym gym1 = new Gym("Wiesbadener Nordwand");
            Gym gym2 = new Gym("Kletterhalle Wiesbaden");
            Gym gym3 = new Gym("Blockwerk Mainz");

            Route routes[] = {
                    new Route(Route.Level.LEICHT, gym1,"routeTest"),
                    new Route(Route.Level.MITTEL, gym1,"routeTest"),
                    new Route(Route.Level.SCHWER, gym1,"routeTest"),
                    new Route(Route.Level.MITTEL, gym2,"routeTest"),
                    new Route(Route.Level.SCHWER, gym2,"routeTest"),
                    new Route(Route.Level.MITTEL, gym3,"routeTest"),
            };

            GymSector gymSector1 = new GymSector(gym1);
            GymSector gymSector2 = new GymSector(gym1);

            GymSector gymSectors[] = {
                    gymSector1,
                    gymSector2,
            };

            GymSectorCoord coords[] = {
                    new GymSectorCoord(0, 0, gymSector1),
                    new GymSectorCoord(0, 200, gymSector1),
                    new GymSectorCoord(200, 200, gymSector1),
                    new GymSectorCoord(200, 0, gymSector1),

                    new GymSectorCoord(200, 0, gymSector2),
                    new GymSectorCoord(200, 200, gymSector2),
                    new GymSectorCoord(400, 200, gymSector2),
                    new GymSectorCoord(400, 0, gymSector2),
            };

            routeBox.put(routes);
            gymSectorCoordsBox.put(coords);
        }

    }

}
