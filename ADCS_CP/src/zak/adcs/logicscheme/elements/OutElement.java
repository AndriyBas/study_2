package zak.adcs.logicscheme.elements;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

@XStreamAlias("outelement")
public class OutElement extends ClosedElement implements Enterable {

    private static final String OUT_TEXT = "OUT";

    @XStreamAlias("inname")
    private String inName;

    public OutElement() {
        super();
    }

    public OutElement(int x, int y, int width, int height, Color standartColor, Color selectedColor, Color textColor,
                      Font font, String name) {
        super(x, y, width, height, standartColor, selectedColor, textColor, font, name);
        inName = null;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x + width / 3, y, width / 6 * 4, height);
        g2.fillOval(x + width / 6, y, width / 3, height);
        g2.drawLine(x, y + height / 2, x + width / 6, y + height / 2);
        g2.setColor(textColor);
        g2.setFont(font);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(OUT_TEXT, context);
        g2.drawString(OUT_TEXT, x + width / 6 + (int) ((width / 6 * 5 - stringBounds.getWidth()) / 2),
                y + (int) ((height - stringBounds.getHeight()) / 2 - stringBounds.getY()));
        stringBounds = font.getStringBounds(name, context);
        g2.drawString(name, x + (int) ((width / 6 - stringBounds.getWidth()) / 2),
                y + (int) ((height - stringBounds.getHeight() / 2) / 2));
    }

    public Point getEnterPoint(Point point) throws ConnectionAlreadyExistException {
        if ((point.x >= x) && (point.x <= x + width / 6) && (point.y >= y) && (point.y <= y + height)) {
            if (inName != null) {
                throw new ConnectionAlreadyExistException(true);
            }
            return new Point(x, y + height / 2);
        } else {
            return null;
        }
    }

    public void addEnterConnectionName(String name, Point point) {
        inName = name;
    }

    public void removeEnterConnectionName(String name) {
        inName = null;
    }

    public boolean isInEnter(Point point) {
        try {
            if (getEnterPoint(point) != null) {
                return true;
            }
            return false;
        } catch (ConnectionAlreadyExistException e) {
            return false;
        }
    }

}
