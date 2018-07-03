package de.ringleinknorr.boulderapp;

import android.content.Context;
import android.graphics.Color;

import de.ringleinknorr.boulderapp.routes.Gym;
import de.ringleinknorr.boulderapp.routes.GymSector;
import de.ringleinknorr.boulderapp.routes.GymSectorCoord;
import de.ringleinknorr.boulderapp.routes.Route;
import de.ringleinknorr.boulderapp.routes.RouteLevel;
import de.ringleinknorr.boulderapp.timeline.Session;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class DBMockData {


    public static void createMockData(Context context, BoxStore boxStore) {


        Box<Session> sessionBox = boxStore.boxFor(Session.class);
        Box<Route> routeBox = boxStore.boxFor(Route.class);
        Box<GymSectorCoord> gymSectorCoordsBox = boxStore.boxFor(GymSectorCoord.class);


        if (routeBox.count() <= 0) {
            Gym gym1 = new Gym("Wiesbadener Nordwand", "map_gym_1.png");
            Gym gym2 = new Gym("Kletterhalle Wiesbaden", "map_gym_2.png");
            Gym gym3 = new Gym("Blockwerk Mainz", "map_gym_1.png");

            RouteLevel gym1level1 = new RouteLevel("Brutal",6, context.getResources().getColor(R.color.colorLevelBlack) , gym1);
            RouteLevel gym1level2 = new RouteLevel("Sportlich", 3,context.getResources().getColor(R.color.colorLevelBlue), gym1);
            RouteLevel gym1level3 = new RouteLevel("Leicht", 1,context.getResources().getColor(R.color.colorLevelYellow), gym1);
            RouteLevel gym1level4 = new RouteLevel("Schwer",5, context.getResources().getColor(R.color.colorLevelWhite), gym1);
            RouteLevel gym1level5 = new RouteLevel("Mittel", 2,context.getResources().getColor(R.color.colorLevelGreen), gym1);
            RouteLevel gym1level6 = new RouteLevel("Anspruchsvoll",4,context.getResources().getColor(R.color.colorRed), gym1);


            RouteLevel level2 = new RouteLevel("Anspruchsvoll", 1,Color.argb(1, 255, 0, 0), gym2);
            RouteLevel level3 = new RouteLevel("Sportlich", 2,Color.BLUE, gym3);

            Route routes[] = {
                    new Route(gym1, "black1.jpg", 1, gym1level1),
                    new Route(gym1, "blue1.jpg", 2, gym1level2),
                    new Route(gym1, "yellow1.jpg", 4, gym1level3),
                    new Route(gym1, "yellow2.jpg", 2, gym1level3),
                    new Route(gym1, "yellow3.jpg", 1, gym1level3),
                    new Route(gym1, "routeTest", 3, gym1level4),
                    new Route(gym1, "green1.jpg", 2, gym1level5),
                    new Route(gym1, "red1.jpg", 4, gym1level6),
                    new Route(gym1, "red2.jpg", 1, gym1level6),


                    new Route(gym2, "routeTest", 5, level2),
                    new Route(gym2, "routeTest", 6, level2),

                    new Route(gym3, "routeTest", 1, level3),
            };

            GymSector gym1Sector1 = new GymSector(gym1);
            GymSector gym1Sector2 = new GymSector(gym1);
            GymSector gym1Sector3 = new GymSector(gym1);
            GymSector gym1Sector4 = new GymSector(gym1);

            GymSector gym2Sector1 = new GymSector(gym2);
            GymSector gym2Sector2 = new GymSector(gym2);
            GymSector gym2Sector3 = new GymSector(gym2);
            GymSector gym2Sector4 = new GymSector(gym2);
            GymSector gym2Sector5 = new GymSector(gym2);

            GymSector gymSector4 = new GymSector(gym3);

            GymSector gymSectors[] = {
                    gym1Sector1,
                    gym1Sector2,
                    gym1Sector3,
                    gym1Sector4,

                    gym2Sector1,
                    gym2Sector2,
                    gym2Sector3,
                    gym2Sector4,
                    gym2Sector5,

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

                    // Sector 1 Gym 2
                    new GymSectorCoord(0, 0, gym2Sector1),
                    new GymSectorCoord(0, 240, gym2Sector1),
                    new GymSectorCoord(495, 0, gym2Sector1),
                    // Sector 2 Gym 2
                    new GymSectorCoord(0, 240, gym2Sector2),
                    new GymSectorCoord(0, 400, gym2Sector2),
                    new GymSectorCoord(360, 400, gym2Sector2),
                    new GymSectorCoord(360, 280, gym2Sector2),
                    new GymSectorCoord(195, 145, gym2Sector2),
                    // Sector 3 Gym 2
                    new GymSectorCoord(360, 400, gym2Sector3),
                    new GymSectorCoord(640, 400, gym2Sector3),
                    new GymSectorCoord(640, 290, gym2Sector3),
                    new GymSectorCoord(495, 220, gym2Sector3),
                    new GymSectorCoord(360, 280, gym2Sector3),
                    // Sector 4 Gym 2
                    new GymSectorCoord(495, 220, gym2Sector4),
                    new GymSectorCoord(495, 0, gym2Sector4),
                    new GymSectorCoord(495, 0, gym2Sector4),
                    new GymSectorCoord(195, 145, gym2Sector4),
                    new GymSectorCoord(360, 280, gym2Sector4),
                    // Sector 5 Gym 2
                    new GymSectorCoord(640, 290, gym2Sector5),
                    new GymSectorCoord(495, 220, gym2Sector5),
                    new GymSectorCoord(495, 0, gym2Sector5),
                    new GymSectorCoord(640, 0, gym2Sector5),



            };

            routeBox.put(routes);
            gymSectorCoordsBox.put(coords);
        }

    }

}
