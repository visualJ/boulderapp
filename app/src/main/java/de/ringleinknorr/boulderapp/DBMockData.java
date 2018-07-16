package de.ringleinknorr.boulderapp;

import android.content.Context;
import android.graphics.Color;

import de.ringleinknorr.boulderapp.models.Gym;
import de.ringleinknorr.boulderapp.models.GymSector;
import de.ringleinknorr.boulderapp.models.GymSectorCoord;
import de.ringleinknorr.boulderapp.models.RouteLevel;
import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Creates some mock data to play with, since not all data is available from a web service yet.
 */
public class DBMockData {

    public static void createMockData(Context context, BoxStore boxStore) {

        Box<GymSectorCoord> gymSectorCoordsBox = boxStore.boxFor(GymSectorCoord.class);
        Box<RouteLevel> routeLevelBox = boxStore.boxFor(RouteLevel.class);

        if (routeLevelBox.count() <= 0) {
            Gym gym1 = new Gym("Wiesbadener Nordwand", "map_gym_1.png");
            Gym gym2 = new Gym("Kletterhalle Wiesbaden", "map_gym_2.png");
            Gym gym3 = new Gym("Blockwerk Mainz", "map_gym_1.png");

            RouteLevel gym1levelyellow = new RouteLevel("Leicht", 1, context.getResources().getColor(R.color.colorLevelYellow), gym1);
            RouteLevel gym1levelgreen = new RouteLevel("Mittel", 2, context.getResources().getColor(R.color.colorLevelGreen), gym1);
            RouteLevel gym1levelblue = new RouteLevel("Sportlich", 3, context.getResources().getColor(R.color.colorLevelBlue), gym1);
            RouteLevel gym1levelred = new RouteLevel("Anspruchsvoll", 4, context.getResources().getColor(R.color.colorRed), gym1);
            RouteLevel gym1levelwhite = new RouteLevel("Schwer", 5, context.getResources().getColor(R.color.colorLevelWhite), gym1);
            RouteLevel gym1levelblack = new RouteLevel("Brutal", 6, context.getResources().getColor(R.color.colorLevelBlack), gym1);

            RouteLevel gym2level1 = new RouteLevel("Einfach", 1, Color.argb(255, 255, 100, 200), gym2);
            RouteLevel gym2level2 = new RouteLevel("Schwierig", 2, Color.argb(255, 200, 150, 0), gym2);
            RouteLevel level3 = new RouteLevel("Sportlich", 2, Color.BLUE, gym3);

            RouteLevel routeLevels[] = {gym1levelblack, gym1levelblue, gym1levelyellow, gym1levelwhite, gym1levelgreen, gym1levelred, gym2level1, gym2level2, level3};

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

            routeLevelBox.put(routeLevels);
            gymSectorCoordsBox.put(coords);
        }
    }
}
