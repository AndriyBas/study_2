package tpe.lab7new;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.geometry.Point2D;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class MainJ extends JFrame {
    protected static final int WIDTH = 600;
    protected static final int HEIGHT = 600;
    private DrawPanel drPanel;
    public static final double X0 = 20;
    public static final double Y0 = 5;
    private SymplexBean symplex;

    public static void main(String[] args) {
        MainJ m = new MainJ();
    }

    public MainJ() {
        this.addNotify();
        this.setSize(this.getInsets().left + this.getInsets().right + WIDTH,
                this.getInsets().top + this.getInsets().bottom + HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        symplex = new SymplexBean(X0, Y0, InitializeWay.FIRST);

        drPanel = new DrawPanel(this, symplex);
        this.add(drPanel);

        this.setVisible(true);
    }

    class DrawPanel extends JComponent implements Runnable {
        private MainJ drBoard;

        private int width, height;
        private BufferedImage dbImage;
        private Graphics2D graphics;

        private int scale = 40;
        public int minX;
        public int minY;
        public int maxX;
        public int maxY;

        public SymplexBean symplex;
        public int dx, dy;
        public int radius = 4;
        public int xBound = 0;
        public int yBound = 0;

        public DrawPanel(MainJ drb, SymplexBean symplex) {
            super();
            width = MainJ.WIDTH;
            height = MainJ.HEIGHT;
            this.setSize(width, height);

            dbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            graphics = dbImage.createGraphics();
            graphics.setBackground(Color.RED);

            this.symplex = symplex;
            new Thread(this).start();
        }

        public void drawTriangle(Point2D p1, Point2D p2, Point2D p3) {
            drawLine(p1, p2);
            drawLine(p2, p3);
            drawLine(p3, p1);
            drawPoint(p1);
            drawPoint(p2);
            drawPoint(p3);
        }

        public int x(double x) {
            return (int) Math.round(scale * (x + dx) + xBound);
        }

        public int y(double y) {
            return (int) Math.round(scale * (y + dy) + yBound);
        }

        boolean randomColor = true;

        public void drawPoint(Point2D p) {
            if (randomColor)
                graphics.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));//Color.BLUE);
            graphics.fillOval(x(p.getX()) - radius, y(p.getY()) - radius, radius * 2, radius * 2);
            //System.out.println("x = " + p.getX() + " ; y = " + p.getY());
        }

        public void drawLine(Point2D p1, Point2D p2) {
            graphics.setColor(Color.GREEN);
            graphics.setStroke(new BasicStroke(2));
            graphics.drawLine(x(p1.getX()), y(p1.getY()),
                    x(p2.getX()), y(p2.getY()));
        }

        public void drawBounds() {
            graphics.setColor(Color.CYAN);
            graphics.drawRect(minX + dx + xBound, minY + dy + xBound, scale * (maxX - minX), scale * (maxY - minY));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(dbImage, 0, 0, this);
        }

        public void drawHeightMap() {
            double h;
            System.out.println("X min = " + symplex.getMinX() + " ; X max = " + symplex.getMaxX());
            System.out.println("Y min = " + symplex.getMinY() + " ; Y max = " + symplex.getMaxY());
            for (double x = minX; x <= maxX; x += 1.0 / scale) {
                //System.out.println("x = " + x);
                for (double y = minY; y <= maxY; y += 1.0 / scale) {
                    graphics.setColor(translateHeight(symplex.getHeight(x, y)));
                    graphics.drawRect(x(x), y(y), 1, 1);
                }
            }
        }

        public Color translateHeight(double height) {
            if (height > 100)
                return new Color(1.0f, 1.0f, 1.0f);
            else {
                if (height > -100) {
                    float d = (float) (height + 100.0) / 200;
                    return new Color(d, d, d);
                } else {
                    return new Color(0f, 0f, 0f);
                }
            }
        }

        @Override
        public void run() {
            symplex.initialize();
            minX = (int) Math.round(symplex.getMinX());
            minY = (int) Math.round(symplex.getMinY());
            maxX = (int) Math.round(symplex.getMaxX());
            maxY = (int) Math.round(symplex.getMaxY());
            dx = -(int) Math.round(symplex.getMinX());
            dy = -(int) Math.round(symplex.getMinY());
            drawHeightMap();
            drawBounds();
            repaint();
            Point2D[] p = symplex.getPoints();
            drawTriangle(p[0], p[1], p[2]);
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int[] counters = new int[3];
            Point2D[] t = symplex.getPoints();
            Point2D temp = new Point2D(0.0, 0.0);
            boolean stable = false;
            boolean[] decreased = new boolean[3];

            while (!stable) {
                temp = symplex.step();
                t = symplex.getPoints();
                for (int i = 0; i < p.length; i++) {
                    if ((t[i].getX() != p[i].getX()) || (t[i].getY() != t[i].getY())) {
                        p[i] = temp;
                        counters[i] = 0;
                    } else {
                        counters[i]++;
                    }
                    if (counters[i] > 3) {
                        if (counters[i] > 10)
                            stable = true;
                        else {
                            if (!decreased[i]) {
                                symplex.decreaseStep();
                                counters[i]--;
                                decreased[i] = true;
                            }
                        }
                    }
                }
                drawTriangle(p[0], p[1], p[2]);
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            randomColor = false;
            boolean color = false;
            temp = p[0];
            for (int i = 1; i < p.length; i++)
                if (symplex.getHeight(temp) < symplex.getHeight(p[i]))
                    temp = p[i];
            System.out.println("Максимум знайдено у точці з координатами:\n\tX = " + temp.getX() + "\n\tY = " + temp.getY());
            while (true) {
                if (color)
                    graphics.setColor(Color.RED);
                else
                    graphics.setColor(Color.YELLOW);
                color = !color;
                drawPoint(temp);
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
