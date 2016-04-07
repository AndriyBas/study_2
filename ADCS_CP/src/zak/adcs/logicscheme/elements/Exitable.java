package zak.adcs.logicscheme.elements;

import java.awt.*;

public interface Exitable {

    public boolean isInExit(Point point);
    public Point getExitPoint(Point point) throws ConnectionAlreadyExistException;
    public String getName();
    public void addExitConnectionName(String name, Point point);
    public void removeExitConnectionName(String name);

}
