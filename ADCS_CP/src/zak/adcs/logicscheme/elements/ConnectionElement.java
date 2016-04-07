package zak.adcs.logicscheme.elements;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

@XStreamAlias("connectionelement")
public class ConnectionElement implements VisualElement, Exitable {

    private static final int POINT_DIAMETER = 8;
    private static final int HALF_RECTANGLE = 5;

    private static final int DEFAULT_NUMBER = 1;

    @XStreamAlias("nextnumber")
    private static int nextNumber;

    @XStreamAsAttribute
    @XStreamAlias("number")
    private int number;

    @XStreamAlias("standartcolor")
    private Color standartColor;
    @XStreamAlias("selectedcolor")
    private Color selectedColor;
    private Color color;

    @XStreamAlias("elementfrom")
    private String elementFrom;
    @XStreamAlias("elementto")
    private String elementTo;
    private ArrayList<Point> points;
    @XStreamAlias("ispointtemporary")
    private ArrayList<Boolean> isPointTemporary;
    @XStreamAlias("ispointselected")
    private ArrayList<Boolean> isPointSelected;
    @XStreamAlias("connectionelements")
    private ArrayList<ArrayList<Integer>> connectionElements;

    public ConnectionElement() {
    }

    public ConnectionElement(String elementFrom, String elementTo, Point pointFrom, Point pointTo,
                             Color standartColor, Color selectedColor) {
        this.elementFrom = elementFrom;
        this.elementTo = elementTo;
        this.standartColor = standartColor;
        this.selectedColor = selectedColor;
        if (nextNumber == 0) {
            nextNumber = DEFAULT_NUMBER + 1;
        }
        number = nextNumber++;
        color = standartColor;
        points = new ArrayList<Point>();
        isPointTemporary = new ArrayList<Boolean>();
        isPointSelected = new ArrayList<Boolean>();
        connectionElements = new ArrayList<ArrayList<Integer>>();
        points.add(pointFrom);
        isPointTemporary.add(false);
        isPointSelected.add(false);
        connectionElements.add(null);
        points.add(pointTo);
        isPointTemporary.add(false);
        isPointSelected.add(false);
        connectionElements.add(null);
        manageTemporaryPoints();
    }

    public void manageTemporaryPoints() {
        for (int i = points.size() - 2; i > 0; i--) {
            if (isPointTemporary.get(i)) {
                points.remove(i);
                isPointTemporary.remove(i);
                isPointSelected.remove(i);
                connectionElements.remove(i);
            }
        }
        for (int i = 0; i < points.size() - 1; i++) {
            if (points.get(i).x != points.get(i + 1).x && points.get(i).y != points.get(i + 1).y) {
                if (points.get(i).x < points.get(i + 1).x) {
                    points.add(i + 1, new Point(points.get(i).x + (points.get(i + 1).x - points.get(i).x) / 2, points.get(i).y));
                    points.add(i + 2, new Point(points.get(i).x + (points.get(i + 2).x - points.get(i).x) / 2, points.get(i + 2).y));
                } else {
                    points.add(i + 1, new Point(points.get(i).x - (points.get(i).x - points.get(i + 1).x) / 2, points.get(i).y));
                    points.add(i + 2, new Point(points.get(i).x - (points.get(i).x - points.get(i + 2).x) / 2, points.get(i + 2).y));
                }
                isPointTemporary.add(i + 1, true);
                isPointSelected.add(i + 1, false);
                connectionElements.add(i + 1, null);
                isPointTemporary.add(i + 2, true);
                isPointSelected.add(i + 2, false);
                connectionElements.add(i + 2, null);
                i += 2;
            }
        }
    }

    public void addConnectionToPoint(ConnectionElement connectionElement, Point point) {
        int pointIndex = getInPointIndex(point);
        connectionElements.get(pointIndex).add(connectionElement.getNumber());
    }

    public String getElementFrom() {
        return elementFrom;
    }

    public String getElementTo() {
        return elementTo;
    }

    public void setElementFrom(String elementFrom) {
        this.elementFrom = elementFrom;
    }

    public void setElementTo(String elementTo) {
        this.elementTo = elementTo;
    }

    public void addPoint(Point p, Point afterPoint) {
        points.add(points.indexOf(afterPoint) + 1, p);
        isPointTemporary.add(points.indexOf(afterPoint) + 1, false);
        isPointSelected.add(points.indexOf(afterPoint) + 1, false);
        connectionElements.add(points.indexOf(afterPoint) + 1, new ArrayList<Integer>());
        manageTemporaryPoints();
    }

    public void addPoint(Point point) {
        int afterNumber = -1;
        for (int i = 0; i < points.size() - 1; i++) {
            Rectangle lineBounds = null;
            if (points.get(i).x == points.get(i + 1).x) {
                if (points.get(i).y < points.get(i + 1).y) {
                    lineBounds = new Rectangle(points.get(i).x - HALF_RECTANGLE, points.get(i).y,
                            2 * HALF_RECTANGLE, points.get(i + 1).y - points.get(i).y);
                } else {
                    lineBounds = new Rectangle(points.get(i).x - HALF_RECTANGLE, points.get(i + 1).y,
                            2 * HALF_RECTANGLE, points.get(i).y - points.get(i + 1).y);
                }
            } else if (points.get(i).y == points.get(i + 1).y) {
                if (points.get(i).x < points.get(i + 1).x) {
                    lineBounds = new Rectangle(points.get(i).x, points.get(i).y - HALF_RECTANGLE,
                            points.get(i + 1).x - points.get(i).x, 2 * HALF_RECTANGLE);
                } else {
                    lineBounds = new Rectangle(points.get(i + 1).x, points.get(i).y - HALF_RECTANGLE,
                            points.get(i).x - points.get(i + 1).x, 2 * HALF_RECTANGLE);
                }
            }
            if (lineBounds.contains(point)) {
                afterNumber = i;
                break;
            }
        }
        if (afterNumber > -1) {
            addPoint(point, points.get(afterNumber));
        }
    }

    public int getInPointIndex(Point p) {
        for (int i = 1; i < points.size() - 1; i++) {
            if (!isPointTemporary.get(i)) {
                Ellipse2D.Double ellipse = new Ellipse2D.Double(points.get(i).getX() - POINT_DIAMETER / 2,
                        points.get(i).getY() - POINT_DIAMETER / 2, POINT_DIAMETER, POINT_DIAMETER);
                if (ellipse.contains(p)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void movePoint(int index, int x, int y) {
        points.get(index).setLocation(points.get(index).getX() + x, points.get(index).getY() + y);
        manageTemporaryPoints();
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        for (int i = 0; i < points.size() - 1; i++) {
            g2.drawLine((int) points.get(i).getX(), (int) points.get(i).getY(), (int) points.get(i + 1).getX(),
                    (int) points.get(i + 1).getY());
        }
        if (points.size() > 2) {
            for (int i = 1; i < points.size() - 1; i++) {
                if (!isPointTemporary.get(i)) {
                    if (isPointSelected.get(i)) {
                        g2.setColor(selectedColor);
                    } else {
                        g2.setColor(color);
                    }
                    g2.fillOval((int) points.get(i).getX() - POINT_DIAMETER / 2,
                            (int) points.get(i).getY() - POINT_DIAMETER / 2,
                            POINT_DIAMETER,
                            POINT_DIAMETER);
                }
            }
        }
    }

    public void move(int x, int y) {
        for (Point p : points) {
            p.setLocation((int) p.getX() + x, (int) p.getY() + y);
        }
    }

    public void removePoint(int index) {
        points.remove(index);
        isPointTemporary.remove(index);
        isPointSelected.remove(index);
        manageTemporaryPoints();
    }

    public void moveFirstPoint(int x, int y) {
        movePoint(0, x, y);
    }

    public void moveLastPoint(int x, int y) {
        movePoint(points.size() - 1, x, y);
    }

    public void setFirstPoint(Point point) {
        points.set(0, point);
        manageTemporaryPoints();
    }

    public void setLastPoint(Point point) {
        points.set(points.size() - 1, point);
        manageTemporaryPoints();
    }

    public Point getFirstPoint() {
        return points.get(0);
    }

    public Point getLastPoint() {
        return points.get(points.size() - 1);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public void setSelectedPoint(int index, boolean selected) {
        isPointSelected.set(index, selected);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            color = selectedColor;
        } else {
            color = standartColor;
        }
    }

    public boolean contains(Point p) {
        for (int i = 0; i < points.size() - 1; i++) {
            Rectangle lineBounds = null;
            if (points.get(i).x == points.get(i + 1).x) {
                if (points.get(i).y < points.get(i + 1).y) {
                    lineBounds = new Rectangle(points.get(i).x - HALF_RECTANGLE, points.get(i).y,
                            2 * HALF_RECTANGLE, points.get(i + 1).y - points.get(i).y);
                } else {
                    lineBounds = new Rectangle(points.get(i).x - HALF_RECTANGLE, points.get(i + 1).y,
                            2 * HALF_RECTANGLE, points.get(i).y - points.get(i + 1).y);
                }
            } else if (points.get(i).y == points.get(i + 1).y) {
                if (points.get(i).x < points.get(i + 1).x) {
                    lineBounds = new Rectangle(points.get(i).x, points.get(i).y - HALF_RECTANGLE,
                            points.get(i + 1).x - points.get(i).x, 2 * HALF_RECTANGLE);
                } else {
                    lineBounds = new Rectangle(points.get(i + 1).x, points.get(i).y - HALF_RECTANGLE,
                            points.get(i).x - points.get(i + 1).x, 2 * HALF_RECTANGLE);
                }
            }
            if (lineBounds != null && lineBounds.contains(p)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOverlaped(Rectangle r) {
        for (int i = 0; i < points.size() - 1; i++) {
            Rectangle lineBounds = null;
            if (points.get(i).x == points.get(i + 1).x) {
                if (points.get(i).y < points.get(i + 1).y) {
                    lineBounds = new Rectangle(points.get(i).x - HALF_RECTANGLE, points.get(i).y,
                            2 * HALF_RECTANGLE, points.get(i + 1).y - points.get(i).y);
                } else {
                    lineBounds = new Rectangle(points.get(i).x - HALF_RECTANGLE, points.get(i + 1).y,
                            2 * HALF_RECTANGLE, points.get(i).y - points.get(i + 1).y);
                }
            } else if (points.get(i).y == points.get(i + 1).y) {
                if (points.get(i).x < points.get(i + 1).x) {
                    lineBounds = new Rectangle(points.get(i).x, points.get(i).y - HALF_RECTANGLE,
                            points.get(i + 1).x - points.get(i).x, 2 * HALF_RECTANGLE);
                } else {
                    lineBounds = new Rectangle(points.get(i + 1).x, points.get(i).y - HALF_RECTANGLE,
                            points.get(i).x - points.get(i + 1).x, 2 * HALF_RECTANGLE);
                }
            }
            if (lineBounds.intersects(r)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInExit(Point point) {
        int pointIndex = getInPointIndex(point);
        if (pointIndex > 0 && pointIndex < points.size() - 1 && !isPointTemporary.get(pointIndex)) {
            return true;
        }
        return false;
    }

    public Point getExitPoint(Point point) throws ConnectionAlreadyExistException {
        if (isInExit(point)) {
            return points.get(getInPointIndex(point));
        }
        return null;
    }

    public String getName() {
        return null;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<Integer> getConnectionNumbers(int index) {
        return connectionElements.get(index);
    }

    public void addExitConnectionName(String name, Point point) {
    }

    public void removeExitConnectionName(String name) {
    }

    public boolean hasChildrenConnections() {
        for (ArrayList<Integer> e : connectionElements) {
            if (e != null && e.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasChild(int number) {
        for (ArrayList<Integer> e : connectionElements) {
            if (e != null && e.size() > 0) {
                for (int n : e) {
                    if (n == number) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<ArrayList<Integer>> getConnectionElements() {
        return connectionElements;
    }

    public void addConnection(ConnectionElement connectionElement, Point point) {
        int index = getInPointIndex(point);
        if (connectionElements.get(index) == null) {
            connectionElements.add(index, new ArrayList<Integer>());
        }
        connectionElements.get(index).add(connectionElement.getNumber());
    }

}
