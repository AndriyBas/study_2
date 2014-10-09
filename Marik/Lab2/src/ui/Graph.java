package ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Rotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;

/**
 * Created by andriybas on 10/3/14.
 */
public class Graph extends JFrame {

    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JPanel panelGraph2;
    private JPanel panelGraph1;

    public Graph() {
        super("Hello, Marik ))");

        add(rootPanel);

        int width = 600;
        int height = 400;

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

        panelGraph2.add(chartPanel);
    }

    private void runQMS() {
        System.out.println("ololo");
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
        menu = new JMenu("Action");
        menu.setMnemonic(KeyEvent.VK_R);

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
        menu.add(menuItem);

        menuBar.add(menu);
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
