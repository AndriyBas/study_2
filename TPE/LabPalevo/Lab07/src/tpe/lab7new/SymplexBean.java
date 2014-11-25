package tpe.lab7new;

import javafx.geometry.Point2D;

public class SymplexBean {
    private int n = 2;
    private double dx = 10;//1 + (220 % 3) + 10;
    private double dy = 10;//2 + (220 % 3) + 10;
    private double p = (n - 1 + Math.sqrt(n + 1)) / (n * Math.sqrt(2.0));
    private double q = (Math.sqrt(n + 1) - 1) / (n * Math.sqrt(2.0));
    private double ro = 0.1;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    private double x0;
    private double y0;
    private InitializeWay way;
    private Point2D a, b, c;
    private int step;

    private abstract class Function {
        abstract double value(double x, double y);

        abstract double value(Point2D point);
    }

    private Function f = new Function() {
        @Override
        double value(Point2D point) {
            return value(point.getX(), point.getY());
        }

        @Override
        double value(double x, double y) {
//            return x + 10 * y;
            return x / y;
            //return 5*Math.cos(x)*Math.cos(y);
        }
    };

    public SymplexBean(double x0, double y0, InitializeWay way) {
        this.x0 = x0;
        this.y0 = y0;
        this.way = way;

        minX = x0 - dx;
        minY = y0 - dy;
        maxX = x0 + dx;
        maxY = y0 + dy;
    }

    public Point2D[] getPoints() {
        return new Point2D[]{a, b, c};
    }

    public void initialize() {
        switch (way) {
            case FIRST:
                firstWay();
                break;
            case SECOND:
                secondWay();
                break;
            case RANDOM:
                randomWay();
                break;
            default:
                secondWay();
        }
        step = 0;
    }

    private void firstWay() {
        a = new Point2D(x0, y0);
        b = new Point2D(x0 + p * dx * ro, y0 + q * dy * ro);
        c = new Point2D(x0 + q * dx * ro, y0 + p * dy * ro);
    }

    private void secondWay() {
        a = new Point2D(x0 - r(1) * dx * ro, y0 - r(2) * dy * ro);
        b = new Point2D(x0 - R(1) * dx * ro, y0 - r(2) * dy * ro);
        c = new Point2D(x0 + R(1) * dx * ro, y0 - R(2) * dy * ro);
    }

    private double R(int i) {
        return 1.0 / Math.sqrt(2 * (i + 1));
    }

    private double r(int i) {
        return 1.0 / Math.sqrt(2 * i * (i + 1));
    }

    private void randomWay() {
        a = new Point2D(x0, y0);
        b = new Point2D(x0 - 0.5 + Math.random(), y0 - 0.5 + Math.random());
        c = new Point2D(x0 - 0.5 + Math.random(), y0 - 0.5 + Math.random());
    }

    private double ya, yb, yc;

    public Point2D step() {
        step++;
        ya = f.value(a);
        yb = f.value(b);
        yc = f.value(c);
        if (ya < yb) {
            if (ya < yc) {
                a = getMirrored(b, c, a);
                return a;
            } else {
                c = getMirrored(a, b, c);
                return c;
            }
        } else {
            if (yb < yc) {
                b = getMirrored(a, c, b);
                return b;
            } else {
                c = getMirrored(a, b, c);
                return c;
            }
        }
    }

    private double coef = 1.0;

    private Point2D getMirrored(Point2D p1, Point2D p2, Point2D p) {
        return new Point2D(p.getX() + coef * (p1.getX() + p2.getX() - 2 * p.getX()), p.getY() + coef * (p1.getY() + p2.getY() - 2 * p.getY()));
        //return new Point2D(p1.getX() + p2.getX() - p.getX(), p1.getY() + p2.getY() - p.getY());
    }

    public void decreaseStep() {
        coef *= 0.95;
    }

    public int getStepNumber() {
        return step;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getHeight(double x, double y) {
        return f.value(x, y);
    }

    public double getHeight(Point2D p) {
        return f.value(p);
    }
}

