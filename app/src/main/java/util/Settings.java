package util;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Settings {
    public static int GRID_WIDTH = 32;
    public static int GRID_HEIGHT = 32;

    private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static int getScreenWidth() {
        return gd.getDisplayMode().getWidth();
    }

    public static int getScreenHeight() {
        return gd.getDisplayMode().getHeight();
    }

    public static int getScreenRefreshRate() {
        return gd.getDisplayMode().getRefreshRate();
    }

    public static double getScreenAspectRatio() {
        return (double)getScreenWidth() / (double)getScreenHeight();
    }
}
