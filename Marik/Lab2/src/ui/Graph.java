package ui;

import generators.ExponentialGen;
import generators.Generator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import relevance.ConstRelevance;
import relevance.Relevance;
import task.RR2;
import task.Task;
import task.TaskGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
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
    private JTextField textFieldTheta;
    private JTextField textFieldMaxPriority;
    private JTextField meowTF;
    private JTextField lambdaTF;
    private JPanel panelGraph3;
    private JPanel panelGraph4;

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

//        // This will create the dataset
//        PieDataset dataset = createDataset();
//        // based on the dataset we create the chart
//        JFreeChart chart = createChart(dataset, "ololo");
//        // we put the chart into a panel
//        ChartPanel chartPanel = new ChartPanel(chart);
//        // default size

    }

    private void runQMS() {

//        Generator inGen = new ExponentialGen(0.7);
//        Generator serveGen = new HyperExponentialGen(new double[]{0.7, 0.3}, new double[]{1.2, 2.0});
//        Generator serveGen = new Regular(1.2);
//        Generator serveGen = new ExponentialGen(1.2);

//        if (checkBoxExpon.isSelected()) {
//            inGen = new ExponentialGen(Double.parseDouble(textFieldLambda.getText().toString().trim()));
//            serveGen = new ExponentialGen(Double.parseDouble(textFieldMeow.getText().toString().trim()));
//        }
//
//        if (checkBoxBasVar.isSelected()) {
//            double p = Double.parseDouble(textFieldBasP1.getText().toString().trim());
//            double m1 = Double.parseDouble(textFieldBasM1.getText().toString().trim());
//            double m2 = Double.parseDouble(textFieldBasM2.getText().toString().trim());
//            serveGen = new HyperExponentialGen(new double[]{p, 1 - p}, new double[]{m1, m2});
//        }

        Relevance relevance = new ConstRelevance();

        int n = 1000;
        int maxPriority = 32;
        double theta = 0.4;

        double lambda = 0.9;
        double meow = 1.0;


        try {
            n = Integer.parseInt(textField1.getText());
            maxPriority = Integer.parseInt(textFieldMaxPriority.getText());
            theta = Double.parseDouble(textFieldTheta.getText());
            meow = Double.parseDouble(meowTF.getText());
            lambda = Double.parseDouble(lambdaTF.getText());
        } catch (Exception e) {
            System.out.println("wtf ???");
        }

        Generator inGen = new ExponentialGen(lambda);
        Generator serveGen = new ExponentialGen(meow);

        List<Task> tasks = TaskGenerator.generateTasks(inGen, serveGen, n, maxPriority);

        RR2 rr = new RR2(tasks, theta);
        rr.run();

//        double maxX = 10.0;
//
//        LineFunction2D linefunction2d__ = new LineFunction2D(0, 1);
//        XYDataset regressionDataset__ = DatasetUtilities.sampleFunction2D(linefunction2d__, 0.0D, maxX, 100, "FIFO");
//
//        JFreeChart ch = ChartFactory.createXYLineChart("Regression line", "t", "T", regressionDataset__, PlotOrientation.VERTICAL, true, false, false);
//        ch.setBackgroundPaint(Color.white);
//
//        XYPlot plot2 = ch.getXYPlot();//new XYPlot(regressionDataset, numberaxis, numberaxis1, xylineandshaperenderer1);
//
//        // FIXME : draw RR
//        double rrMaxX = 0.0;
//        for (Task e : rr.tasks) {
//            if (e.serveTime > rrMaxX) {
//                rrMaxX = e.serveTime;
//            }
//        }
//        double x = 0;
//        double nextX = rr.theta;
//        int __i = 1;
//
//        while (x < rrMaxX) {
//
//
//            XYSeries rrSer = new XYSeries("res");
//            int go = 0;
//            for (Task e : rr.tasks) {
//                if (e.serveTime <= nextX && e.serveTime >= x) {
//                    rrSer.add(e.serveTime, e.getInSystemTime());
//                    go++;
//                }
//            }
//
//            if (go > 150) {
//                XYLineAndShapeRenderer rrLineRen = new XYLineAndShapeRenderer(true, false);
//                rrLineRen.setSeriesPaint(0, Color.blue);
//
//                XYDataset rrDat = new XYSeriesCollection(rrSer);
//
//                double[] rrRes = Regression.getOLSRegression(rrDat, 0);
//
//                LineFunction2D rrLineFunc = new LineFunction2D(rrRes[0], rrRes[1]);
//                XYDataset rrRegDataSet = DatasetUtilities.sampleFunction2D(rrLineFunc, x, nextX, 100, "RR " + __i);
//
//                plot2.setDataset(__i, rrRegDataSet);
//                plot2.setRenderer(__i, rrLineRen);
//            }
//
//            x = nextX;
//            __i++;
//            nextX += rr.theta;
//        }
//
//
//        panelGraph1.removeAll();
//        panelGraph1.add(new ChartPanel(ch));


        // ------------------------// ------------------------// ------------------------// ------------------------

        double[] avWait = new double[maxPriority + 1];
        Arrays.fill(avWait, 0.0);
        for (Task t : tasks) {
            avWait[t.priority] += t.getWaitTime();
        }
        for (int i = 0; i < avWait.length; i++) {
            avWait[i] /= 1.0 * n;
        }

        XYSeries ser1 = new XYSeries("Wait time (priority)");
        for (int i = 1; i < avWait.length; i++) {
            ser1.add(i, avWait[i]);
        }

        XYSeriesCollection dat1 = new XYSeriesCollection(ser1);
        JFreeChart chart1 = ChartFactory.createXYLineChart("Wait time (priority)", "priority", "T wait", dat1, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel1 = new ChartPanel((chart1));
        panelGraph2.removeAll();
        panelGraph2.add(chartPanel1);


        XYSeries ser2 = new XYSeries("Wait time (lambda)");
        XYSeries ser3 = new XYSeries("Idle % (lambda)");
        for (double lam = 0.1; lam < 2.01; lam += 0.1) {

            ExponentialGen inGen1 = new ExponentialGen(lam);
            ExponentialGen serveGen1 = new ExponentialGen(meow);

            List<Task> tasks1 = TaskGenerator.generateTasks(inGen1, serveGen1, n, maxPriority);

            RR2 rr1 = new RR2(tasks1, theta);
            rr1.run();

            ser2.add(lam, avWait(tasks1));
            double idleTime = (rr1.getSystemTime() - totalServeTime(tasks1)) / rr1.getSystemTime()  * 100;
            ser3.add(lam, idleTime);
        }

        XYSeriesCollection dat2 = new XYSeriesCollection(ser2);
        JFreeChart chart2 = ChartFactory.createXYLineChart("Wait time (lambda)", "lambda", "T wait", dat2, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel2 = new ChartPanel((chart2));
        panelGraph3.removeAll();
        panelGraph3.add(chartPanel2);


        XYSeriesCollection dat3 = new XYSeriesCollection(ser3);
        JFreeChart chart3 = ChartFactory.createXYLineChart("Idle % (lambda)", "lambda", "Idle %", dat3, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel3 = new ChartPanel((chart3));
        panelGraph4.removeAll();
        panelGraph4.add(chartPanel3);




        pack();

        System.out.println("=======================================\n");
    }

    private void clear(List<Task> tasks) {
        for(Task t: tasks)
            t.clear();
    }



    private double totalServeTime(List<Task> tasksList) {
        double w = 0.0;
        for (Task t : tasksList)
            w += t.serveTime;
        return w;
    }

    private double avWait(List<Task> tasksList) {
        double w = 0.0;
        for (Task t : tasksList)
            w += t.getWaitTime();
        return w / tasksList.size();
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

//    private PieDataset createDataset() {
//        DefaultPieDataset result = new DefaultPieDataset();
//
//        result.setValue("Linux", 29);
//        result.setValue("Mac", 20);
//        result.setValue("Windows", 51);
//        return result;
//
//    }


    /**
     * Creates a chart
     */
//
//    private JFreeChart createChart(PieDataset dataset, String title) {
//
//        JFreeChart chart = ChartFactory.createPieChart3D(title,          // chart title
//                dataset,                // data
//                true,                   // include legend
//                true,
//                false);
//
//        PiePlot3D plot = (PiePlot3D) chart.getPlot();
//        plot.setStartAngle(290);
//        plot.setDirection(Rotation.CLOCKWISE);
//        plot.setForegroundAlpha(0.5f);
//
//        DefaultXYDataset data = new DefaultXYDataset();
//
//
////        ChartFactory.createLineChart("FIFI", "t", "T", null);
//
//        return chart;
//
//    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
