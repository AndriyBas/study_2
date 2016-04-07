package zak.adcs.logicscheme.elements;

import java.awt.*;

public interface VisualElement {

    public void draw(Graphics2D g2);
    public void move(int x, int y);
    public void setSelected(boolean selected);
    public boolean contains(Point p);
    public boolean isOverlaped(Rectangle r);

}