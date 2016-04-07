package zak.adcs.logicscheme.elements;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.awt.*;

public abstract class ClosedElement implements VisualElement {

    @XStreamAsAttribute
    protected int x;
    @XStreamAsAttribute
    protected int y;
    @XStreamAsAttribute
    protected int width;
    @XStreamAsAttribute
    protected int height;

    @XStreamAlias("standartcolor")
    protected Color standartColor;
    @XStreamAlias("selectedcolor")
    protected Color selectedColor;
    @XStreamAlias("textcolor")
    protected Color textColor;
    protected Color color;

    protected Font font;

    protected String name;

    public ClosedElement() {
    }

    protected ClosedElement(int x, int y, int width, int height, Color standartColor, Color selectedColor,
                            Color textColor, Font font, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.standartColor = standartColor;
        this.selectedColor = selectedColor;
        this.textColor = textColor;
        this.font = font;
        this.name = name;
        color = standartColor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getStandartColor() {
        return standartColor;
    }

    public void setStandartColor(Color standartColor) {
        this.standartColor = standartColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void setSelected(boolean selected) {
        if (selected) {
            color = selectedColor;
        } else {
            color = standartColor;
        }
    }

    public boolean contains(Point p) {
        Rectangle r = new Rectangle(x, y, width, height);
        return r.contains(p);
    }

    public boolean isOverlaped(Rectangle r) {
        if ((r.contains(new Point(x, y))) || (r.contains(new Point(x + width, y))) ||
                (r.contains(new Point(x, y + height))) || (r.contains(new Point(x + width, y + height)))) {
            return true;
        }
        Rectangle bounds = new Rectangle(x, y, width, height);
        if ((bounds.contains(new Point((int) r.getX(), (int) r.getY()))) ||
                (bounds.contains(new Point((int) (r.getX() + r.getWidth()), (int) r.getY()))) ||
                (bounds.contains(new Point((int) r.getX(), (int) (r.getY() + r.getHeight())))) ||
                (bounds.contains(new Point((int) (r.getX() + r.getWidth()), (int) (r.getY() + r.getHeight()))))) {
            return true;
        }
        return false;
    }

}
