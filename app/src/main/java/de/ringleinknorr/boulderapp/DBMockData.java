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
                    new Route(Route.Level.LEICHT, gym1,"routeTest", 1),
                    new Route(Route.Level.MITTEL, gym1,"routeTest", 1),
                    new Route(Route.Level.SCHWER, gym1,"routeTest", 2),
                    new Route(Route.Level.MITTEL, gym2,"routeTest",1),
                    new Route(Route.Level.SCHWER, gym2,"routeTest",1),
                    new Route(Route.Level.MITTEL, gym3,"routeTest",1),
            };

            GymSector gym1Sector1 = new GymSector(gym1);
            GymSector gym1Sector2 = new GymSector(gym1);
            GymSector gym1Sector3 = new GymSector(gym1);
            GymSector gym1Sector4 = new GymSector(gym1);

            GymSector gymSector3 = new GymSector(gym2);
            GymSector gymSector4 = new GymSector(gym3);

            GymSector gymSectors[] = {
                    gym1Sector1,
                    gym1Sector2,
                    gym1Sector3,
                    gym1Sector4,

                    gymSector3,
                    gymSector4,
            };

            GymSectorCoord coords[] = {
                    // Sector 1 Gym 1
                    new GymSectorCoord(0, 0, gym1Sector1),
                    new GymSectorCoord(0, 260, gym1Sector1),
                    new GymSectorCoord(245, 170, gym1Sector1),
                    new GymSectorCoord(245, 0, gym1Sector1),
                    // Sector 2 Gym 1
                    new GymSectorCoord(0, 260, gym1Sector2),
                    new GymSectorCoord(0, 400, gym1Sector2),
                    new GymSectorCoord(400, 400, gym1Sector2),
                    new GymSectorCoord(245, 170, gym1Sector2),
                    // Sector 3 Gym 1
                    new GymSectorCoord(245, 0, gym1Sector3),
                    new GymSectorCoord(245, 170, gym1Sector3),
                    new GymSectorCoord(315, 275, gym1Sector3),
                    new GymSectorCoord(640, 0, gym1Sector3),
                    // Sector 4 Gym 1
                    new GymSectorCoord(315, 275, gym1Sector4),
                    new GymSectorCoord(400, 400, gym1Sector4),
                    new GymSectorCoord(640, 400, gym1Sector4),
                    new GymSectorCoord(640, 0, gym1Sector4),

/*
                    new GymSectorCoord(0, 0, gymSector1),
                    new GymSectorCoord(0, 200, gymSector1),
                    new GymSectorCoord(200, 200, gymSector1),
                    new GymSectorCoord(200, 0, gymSector1),

                    new GymSectorCoord(200, 0, gymSector2),
                    new GymSectorCoord(200, 200, gymSector2),
                    new GymSectorCoord(400, 200, gymSector2),
                    new GymSectorCoord(400, 0, gymSector2),
*/
                    new GymSectorCoord(200, 0, gymSector3),
                    new GymSectorCoord(200, 200, gymSector3),
                    new GymSectorCoord(400, 200, gymSector3),
                    new GymSectorCoord(400, 0, gymSector3),

                    new GymSectorCoord(200, 0, gymSector4),
                    new GymSectorCoord(200, 200, gymSector4),
                    new GymSectorCoord(400, 200, gymSector4),
                    new GymSectorCoord(400, 0, gymSector4),

            };

            routeBox.put(routes);
            gymSectorCoordsBox.put(coords);
        }

    }

}
