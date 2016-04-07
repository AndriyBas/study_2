package zak.adcs.logicscheme.elements;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public abstract class LogicElement extends ClosedElement implements LIDElement, Enterable, Exitable {

    @XStreamAlias("inertiadelay")
    protected int inertiaDelay;
    @XStreamAlias("dynamicdelay")
    protected int dynamicDelay;

    @XStreamAlias("incount")
    protected int inCount;
    @XStreamAlias("innames")
    protected String[] inNames;
    @XStreamAlias("outnames")
    protected String outName;

    public LogicElement() {
        super();
    }

    protected LogicElement(int x, int y, int width, int height, Color standartColor, Color selectedColor,
                           Color textColor, Font font, String name, int inertiaDelay, int dynamicDelay, int inCount) {
        super(x, y, width, height, standartColor, selectedColor, textColor, font, name);
        this.inertiaDelay = inertiaDelay;
        this.dynamicDelay = dynamicDelay;
        this.inCount = inCount;
        inNames = new String[this.inCount];
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x + width / 6, y, width / 3 * 2, height);
        int inDistance = height / (inCount + 1);
        int inY = y;
        for (int i = 0; i < inCount; i++) {
            inY += inDistance;
            g2.drawLine(x, inY, x + width / 6, inY);
        }
        g2.drawLine(x + width / 6 * 5, y + height / 2, x + width, y + height / 2);
        g2.setColor(textColor);
        g2.setFont(font);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(name, context);
        g2.drawString(name, x + width / 6 + (int) ((width / 3 * 2 - stringBounds.getWidth()) / 2), y + height / 12 * 11);
    }

    public int getInertiaDelay() {
        return inertiaDelay;
    }

    public int getDynamicDelay() {
        return dynamicDelay;
    }

    public void setInertiaDelay(int inertiaDelay) {
        this.inertiaDelay = inertiaDelay;
    }

    public void setDynamicDelay(int dynamicDelay) {
        this.dynamicDelay = dynamicDelay;
    }

    public int getInCount() {
        return inCount;
    }

    public void setInCount(int inCount) {
        if (inCount < this.inCount) {
            String[] newInNames = new String[inCount];
            for (int i = 0; i < inCount; i++) {
                newInNames[i] = inNames[i];
            }
            inNames = newInNames;
        }
        this.inCount = inCount;
    }

    public Point getEnterPoint(Point point) throws ConnectionAlreadyExistException {
        int enterDistance = height / (inCount + 1);
        if ((point.x >= x) && (point.x <= x + width / 6) &&
                (point.y >= y + enterDistance / 2) && (point.y <= y + height - enterDistance / 2)) {
            int inNumber = (point.y - y - enterDistance / 2) / enterDistance;
            if (inNames[inNumber] != null) {
                throw new ConnectionAlreadyExistException(true);
            }
            return new Point(x, y + enterDistance * (inNumber + 1));
        } else {
            return null;
        }
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

    public void addEnterConnectionName(String name, Point point) {
        int enterDistance = height / (inCount + 1);
        if ((point.x >= x) && (point.x <= x + width / 6) &&
                (point.y >= y + enterDistance / 2) && (point.y <= y + height - enterDistance / 2)) {
            int inNumber = (point.y - y - enterDistance / 2) / enterDistance;
            inNames[inNumber] = name;
        }
    }

    public void addExitConnectionName(String name, Point point) {
        outName = name;
    }

    public void removeEnterConnectionName(String name) {
        for (int i = 0; i < inNames.length; i++) {
            if (inNames[i] != null && inNames[i] == name) {
                inNames[i] = null;
            }
        }
    }

    public void removeExitConnectionName(String name) {
        outName = null;
    }

}
