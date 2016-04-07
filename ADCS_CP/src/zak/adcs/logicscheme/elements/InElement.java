package zak.adcs.logicscheme.elements;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

@XStreamAlias("inelement")
public class InElement extends ClosedElement implements Exitable {

    private static final String IN_TEXT = "IN";

    @XStreamAlias("outname")
    private String outName;

    public InElement() {
        super();
    }

    public InElement(int x, int y, int width, int height, Color standartColor, Color selectedColor, Color textColor,
                     Font font, String name) {
        super(x, y, width, height, standartColor, selectedColor, textColor, font, name);
        outName = null;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, width / 6 * 4, height);
        g2.fillOval(x + width / 6 * 3, y, width / 3, height);
        g2.drawLine(x + width / 6 * 5, y + height / 2, x + width, y + height / 2);
        g2.setFont(font);
        g2.setColor(textColor);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(IN_TEXT, context);
        g2.drawString(IN_TEXT, x + (int) ((width / 6 * 5 - stringBounds.getWidth()) / 2),
                y + (int) ((height - stringBounds.getHeight()) / 2 - stringBounds.getY()));
        stringBounds = font.getStringBounds(name, context);
        g2.drawString(name, x + width / 6 * 5 + (int) ((width / 6 - stringBounds.getWidth()) / 2),
                y + (int) ((height - stringBounds.getHeight() / 2) / 2));
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public Point getExitPoint(Point point) throws ConnectionAlreadyExistException {
        if ((point.x >= x + width / 6 * 5) && (point.x <= x + width) && (point.y >= y) && (point.y <= y + height)) {
            if (outName != null) {
                throw new ConnectionAlreadyExistException(false);
            }
            return new Point(x + width, y + height / 2);
        } else {
            return null;
        }
    }

    public void addExitConnectionName(String name, Point point) {
        outName = name;
    }

    public void removeExitConnectionName(String name) {
        outName = null;
    }

    public boolean isInExit(Point point) {
        try {
            if (getExitPoint(point) != null) {
                return true;
            }
            return false;
        } catch (ConnectionAlreadyExistException e) {
            return false;
        }
    }

}
