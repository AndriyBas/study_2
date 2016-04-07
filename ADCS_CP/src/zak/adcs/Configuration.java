package zak.adcs;

import java.awt.*;

public class Configuration {

    private int mainframex;
    private int mainframey;
    private int mainframewidth;
    private int mainframeheight;

    public Configuration() {
    }

    public Configuration(Rectangle mainFrameBounds) {
        mainframex = (int) mainFrameBounds.getX();
        mainframey = (int) mainFrameBounds.getY();
        mainframewidth = (int) mainFrameBounds.getWidth();
        mainframeheight = (int) mainFrameBounds.getHeight();
    }

    public Rectangle getMainFrameBounds() {
        return new Rectangle(mainframex, mainframey, mainframewidth, mainframeheight);
    }

}
