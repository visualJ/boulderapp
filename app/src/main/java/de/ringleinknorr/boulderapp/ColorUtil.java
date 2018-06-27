package de.ringleinknorr.boulderapp;

import android.graphics.Color;

public class ColorUtil {

    /**
     * Return either black or white, depending on the given background color
     *
     * @param backgroundColor The background color the text color should be visible on
     * @return Black, if the background is ligth, otherwise white
     */
    public static int getReadableTextColor(int backgroundColor) {
        int red = Color.red(backgroundColor);
        int green = Color.red(backgroundColor);
        int blue = Color.red(backgroundColor);
        int value = (red + green + blue) / 3;
        return value > 200 ? Color.BLACK : Color.WHITE;
    }
}
