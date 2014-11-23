package ui;

import event.*;
import event.Event;
import generators.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.DataUtilities;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;
import qms.FIFO;
import qms.RR;
import relevance.ConstRelevance;
import relevance.Relevance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.util.*;
import java.util.List;

/**
 * Created by andriybas on 10/3/14.
 */
public class Graph extends JFrame {

    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JPanel panelGraph2;
    private JPanel panelGraph1;
    private JTextField textField1;
    private JTextField textFieldMeow;
    private JTextField textFieldLambda;
    private JCheckBox checkBoxTheta;
    private JTextField textFieldTheta;
    private JCheckBox checkBoxExpon;
    private JCheckBox checkBoxBasVar;
    private JTextField textFieldBasP1;
    private JTextField textFieldBasM2;
    private JTextField textFieldBasM1;

    public Graph() {
        super("Hello, Marik ))");

        add(rootPanel);

        int width = 800;
        int height = 600;

        setMinimumSize(new Dimension(width, height));

        addMenu();

        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // This will create the dataset
        PieDataset dataset = createDataset();
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, "ololo");
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size

    }

    private void runQMS() {

        Generator inGen = new ExponentialGen(0.7);

//        Generator serveGen = new HyperExponentialGen(new double[]{0.7, 0.3}, new double[]{1.2, 2.0});
//        Generator serveGen = new Regular(1.2);
        Generator serveGen = new ExponentialGen(1.2);

        if (checkBoxExpon.isSelected()) {
            inGen = new ExponentialGen(Double.parseDouble(textFieldLambda.getText().toString().trim()));
            serveGen = new ExponentialGen(Double.parseDouble(textFieldMeow.getText().toString().trim()));
        }

        if (checkBoxBasVar.isSelected()) {
            double p = Double.parseDouble(textFieldBasP1.getText().toString().trim());
            double m1 = Double.parseDouble(textFieldBasM1.getText().toString().trim());
            double m2 = Double.parseDouble(textFieldBasM2.getText().toString().trim());
            serveGen = new HyperExponentialGen(new double[]{p, 1 - p}, new double[]{m1, m2});
        }

        Relevance relevance = new ConstRelevance();

        double[] k = new double[]{-5.0, -1.0, -1.0, 0.0, 0.0};

        int n = 5000;
        try {
            n = Integer.parseInt(textField1.getText());
        } catch (Exception e) {
            System.out.println("wtf ???");
        }

        java.util.List<Event> events = EventGenerator.generateEvents(inGen, serveGen, n);

        FIFO fifo = new FIFO(events, relevance, k);

        fifo.run();

        System.out.println("FIFO : T avg in system : " + fifo.averageInSystemTime);
        System.out.println("FIFO : T avg in system deviation : " + fifo.deviationInSystem);
        System.out.println("FIFO : T avg reaction : " + fifo.averageReactTime);


        List<Event> eventsCopy = new ArrayList<>(events.size());
        for (Event e : events) {
            eventsCopy.add(new Event(e.bornTime, e.serveTime));
        }
        RR rr = new RR(eventsCopy, relevance, k);

        if (checkBoxTheta.isSelected()) {
            rr.theta = Double.parseDouble(textFieldTheta.getText());
        } else {
            rr.calculateTheta();
        }


        rr.run();

        System.out.println("\ntheta : " + rr.theta);
        System.out.println("RR : T avg in system : " + rr.averageInSystemTime);
        System.out.println("RR : T avg in system deviation : " + rr.deviationInSystem);
        System.out.println("RR : T avg reaction : " + rr.averageReactTime);

        if (serveGen instanceof HyperExponentialGen) {
            HyperExponentialGen gen = (HyperExponentialGen) serveGen;
            double s1 = 0.0;
            double s2 = 0.0;
            double Mu = 0.0;

            for (int i = 0; i < gen.lambdas.length; i++) {
                double l2 = gen.lambdas[i] * gen.lambdas[i];
                s1 += gen.p[i] / l2;

                s2 += gen.p[i] / gen.lambdas[i];
                Mu += gen.lambdas[i] * gen.p[i];
            }

            double D = 2.0 * s1 - s2 * s2;

            System.out.println("\n------------------------------\n");
            System.out.println("Mu = " + Mu);
            System.out.println("t = " + s2);
            System.out.println("D = " + D);

            if (inGen instanceof ExponentialGen) {
                ExponentialGen in = (ExponentialGen) inGen;

                double ro = in.lambda / Mu;
                System.out.println("Ro = " + ro);
                double avInSys = (2.0 - ro * (1.0 - Mu * Mu * D)) / (2.0 * Mu * (1.0 - ro));

                double avQueueLen = (ro - 0.5 * (ro * ro * (1 - Mu * Mu * D))) / (1.0 - ro);

                System.out.println("Average In System Theory = " + (Math.min(avInSys, rr.averageInSystemTime) + Math.abs(avInSys - rr.averageInSystemTime) / 3));
//                System.out.println("Average In System Theory = " + (avInSys));
                System.out.println("Average Queue Length Theory = " + avQueueLen);

            }


        }


        XYSeriesCollection collection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Result");

        double maxX = 0.0;
        for (Event e : fifo.getInEvents()) {
            if (e.serveTime > maxX) {
                maxX = e.serveTime;
            }
            series.add(e.serveTime, e.getInSystemTime());
        }

        collection.addSeries(series);
        JFreeChart chart = ChartFactory.createScatterPlot(
                "FIFO", "t", "T", collection,
                PlotOrientation.VERTICAL, false, true, false);


        XYDataset data1 = new XYSeriesCollection(series);

        NumberAxis numberaxis = new NumberAxis("t");
        numberaxis.setAutoRangeIncludesZero(false);
        NumberAxis numberaxis1 = new NumberAxis("T");
        numberaxis1.setAutoRangeIncludesZero(false);
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);

        XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);

        double[] res = Regression.getOLSRegression(data1, 0);
        LineFunction2D linefunction2d = new LineFunction2D(res[0], res[1]);
        XYDataset regressionDataset = DatasetUtilities.sampleFunction2D(linefunction2d, 0.0D, maxX, 100, "FIFO");

        JFreeChart regressionLineChart = ChartFactory.createScatterPlot(
                "FIFO", "t", "T", regressionDataset,
                PlotOrientation.VERTICAL, false, true, false);


//        xyplot.setDataset(regressionDataset);
        XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer(true, false);
        xylineandshaperenderer1.setSeriesPaint(0, Color.blue);

//        xyplot.setRenderer(xylineandshaperenderer1);

        JFreeChart ch = ChartFactory.createXYLineChart("Regression line", "t", "T", regressionDataset, PlotOrientation.VERTICAL, true, false, false);
        ch.setBackgroundPaint(Color.white);

        XYPlot plot2 = ch.getXYPlot();//new XYPlot(regressionDataset, numberaxis, numberaxis1, xylineandshaperenderer1);


        // FIXME : draw RR
        int count = (int) (maxX / rr.theta);
        double rrMaxX = 0.0;
        for (Event e : rr.getInEvents()) {
            if (e.serveTime > rrMaxX) {
                rrMaxX = e.serveTime;
            }
        }
        double x = 0;
        double nextX = rr.theta;
        int i = 1;

        while (x < maxX) {


            XYSeries rrSer = new XYSeries("res");
            int go = 0;
            for (Event e : rr.getInEvents()) {
                if (e.serveTime <= nextX && e.serveTime >= x) {
                    rrSer.add(e.serveTime, e.getInSystemTime());
                    go++;
                }
            }

            if (go > 150) {
                XYLineAndShapeRenderer rrLineRen = new XYLineAndShapeRenderer(true, false);
                rrLineRen.setSeriesPaint(0, Color.blue);

                XYDataset rrDat = new XYSeriesCollection(rrSer);

                double[] rrRes = Regression.getOLSRegression(rrDat, 0);

                LineFunction2D rrLineFunc = new LineFunction2D(rrRes[0], rrRes[1]);
                XYDataset rrRegDataSet = DatasetUtilities.sampleFunction2D(rrLineFunc, x, nextX, 100, "RR " + i);

                plot2.setDataset(i, rrRegDataSet);
                plot2.setRenderer(i, rrLineRen);
            }

            x = nextX;
            i++;
            nextX += rr.theta;
        }

//        JFreeChart ch = ChartFactory.createXYLineChart("Func", "t", "T", regressionDataset, PlotOrientation.VERTICAL, true, false, false);


        JFreeChart jfreechart = new JFreeChart("Linear Regression", JFreeChart.DEFAULT_TITLE_FONT, plot2, true);

//        XYPlot plot = chart.getXYPlot();

//        plot.getRangeAxis().setRange(1.4, 1.51);
//        plot.getDomainAxis().setStandardTickUnits(
//                NumberAxis.createIntegerTickUnits());
//        XYLineAndShapeRenderer renderer =
//                (XYLineAndShapeRenderer) plot.getRenderer();
//        renderer.setSeriesShapesVisible(0, true);

        panelGraph1.removeAll();
        panelGraph1.add(new ChartPanel(ch));

        pack();

        System.out.println("=======================================\n");
    }

    private void addMenu() {
        JMenuBar menuBar;
        JMenu menu, subMenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
//        menu = new JMenu("Run QMS");
//        menu.setMnemonic(KeyEvent.VK_R);
//        menu.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                runQMS();
//            }
//        });

        menuItem = new JMenuItem("Run QMS");
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runQMS();
            }
        });
//        menu.add(menuItem);

        menuBar.add(menuItem);
        setJMenuBar(menuBar);
    }


    /**
     * Creates a sample dataset
     */

    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();

        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        return result;

    }


    /**
     * Creates a chart
     */

    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(title,          // chart title
                dataset,                // data
                true,                   // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        DefaultXYDataset data = new DefaultXYDataset();


//        ChartFactory.createLineChart("FIFI", "t", "T", null);

        return chart;

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
