package zak.adcs.logicscheme.elements;

import java.awt.*;

public interface Enterable {

    public boolean isInEnter(Point point);
    public Point getEnterPoint(Point point) throws ConnectionAlreadyExistException;
    public String getName();
    public void addEnterConnectionName(String name, Point point);
    public void removeEnterConnectionName(String name);

}
