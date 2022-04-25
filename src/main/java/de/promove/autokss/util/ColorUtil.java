package de.promove.autokss.util;

import java.awt.*;

public class ColorUtil {

    public static String convertToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
