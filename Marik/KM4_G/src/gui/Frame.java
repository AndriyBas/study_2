package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import logic.CPU;
import logic.Calculator;
import logic.Controller;
import logic.GraphBuilder;
import logic.State;
import logic.TreeBuilder;
import logic.Unit;

public class Frame extends JFrame {
    private HashMap<String, GUnit> gmap;
    private HashMap<String, Unit> umap;
    private ArrayList<Unit> uList;
    private JTable table;

    private TreeBuilder treeBuilder;
    private GraphBuilder graphBuilder;
    private Controller controller;


    public Frame() {
        super("Гненний Андрій | Лабораторна робота №4 з КМ");

        init();
        buildUI();

    }

    void init() {
        gmap = new HashMap<String, GUnit>();
        gmap.put("ЦП", new GUnit("ЦП", 2700.0 * 1_000_000.0, new double[]{0.9, 0.1}, new String[]{"ЦП", "Півн. міст"}));
        gmap.put("Півн. міст", new GUnit("Півн. міст", 2000.0 * 1_000_000.0, new double[]{0.4, 0.2, 0.2, 0.2}, new String[]{"ЦП", "Півд. міст", "ГП", "ОП"}));
        gmap.put("ГП", new GUnit("ГП", 1100.0 * 1_000_000.0, new double[]{1.0}, new String[]{"Півн. міст"}));
        gmap.put("ОП", new GUnit("ОП", 1000.0 * 1_000_000.0, new double[]{1.0}, new String[]{"Півн. міст"}));
        gmap.put("Півд. міст", new GUnit("Півд. міст", 10.0 * 1_000_000.0, new double[]{0.3333333333333333, 0.3333333333333333, 0.3333333333333333}, new String[]{"Півн. міст", "КМД", "ISA"}));
        gmap.put("ISA", new GUnit("ISA", 8.0 * 1_000_000.0, new double[]{0.5, 0.5}, new String[]{"Півд. міст", "COM"}));
        gmap.put("COM", new GUnit("COM", 115.0 * 1_000.0, new double[]{1.0}, new String[]{"ISA"}));
        gmap.put("КМД", new GUnit("КМД", 1500.0 * 1_000_000.0, new double[]{0.5, 0.5}, new String[]{"Півд. міст", "Дисковод"}));
        gmap.put("Дисковод", new GUnit("Дисковод", 4.0 * 1_000_000.0, new double[]{1.0}, new String[]{"КМД"}));
    }

    void update() {

        umap = new HashMap<String, Unit>();
        umap.put("ЦП", new CPU("ЦП", 3, gmap.get("ЦП").getIntensity(), 0));
        umap.put("Півн. міст", new Unit("Півн. міст", gmap.get("Півн. міст").getIntensity(), 1));
        umap.put("ГП", new Unit("ГП", gmap.get("ГП").getIntensity(), 2));
        umap.put("ОП", new Unit("ОП", gmap.get("ОП").getIntensity(), 3));
        umap.put("Півд. міст", new Unit("Півд. міст", gmap.get("Півд. міст").getIntensity(), 4));
        umap.put("ISA", new Unit("ISA", gmap.get("ISA").getIntensity(), 5));
        umap.put("COM", new Unit("COM", gmap.get("COM").getIntensity(), 6));
        umap.put("КМД", new Unit("КМД", gmap.get("КМД").getIntensity(), 7));
        umap.put("Дисковод", new Unit("Дисковод", gmap.get("Дисковод").getIntensity(), 8));

        umap.get("ЦП").link(umap.get("ЦП"), gmap.get("ЦП").getProbTo("ЦП"));
        umap.get("ЦП").link(umap.get("Півн. міст"), gmap.get("ЦП").getProbTo("Півн. міст"));

        umap.get("Півн. міст").link(umap.get("ЦП"), gmap.get("Півн. міст").getProbTo("ЦП"));
        umap.get("Півн. міст").link(umap.get("Півд. міст"), gmap.get("Півн. міст").getProbTo("Півд. міст"));
        umap.get("Півн. міст").link(umap.get("ГП"), gmap.get("Півн. міст").getProbTo("ГП"));
        umap.get("Півн. міст").link(umap.get("ОП"), gmap.get("Півн. міст").getProbTo("ОП"));

        umap.get("ГП").link(umap.get("Півн. міст"), gmap.get("ГП").getProbTo("Півн. міст"));
        umap.get("ОП").link(umap.get("Півн. міст"), gmap.get("ОП").getProbTo("Півн. міст"));

        umap.get("Півд. міст").link(umap.get("Півн. міст"), gmap.get("Півд. міст").getProbTo("Півн. міст"));
        umap.get("Півд. міст").link(umap.get("КМД"), gmap.get("Півд. міст").getProbTo("КМД"));
        umap.get("Півд. міст").link(umap.get("ISA"), gmap.get("Півд. міст").getProbTo("ISA"));

        umap.get("ISA").link(umap.get("Півд. міст"), gmap.get("ISA").getProbTo("Півд. міст"));
        umap.get("ISA").link(umap.get("COM"), gmap.get("ISA").getProbTo("COM"));

        umap.get("COM").link(umap.get("ISA"), gmap.get("COM").getProbTo("ISA"));

        umap.get("КМД").link(umap.get("Півд. міст"), gmap.get("КМД").getProbTo("Півд. міст"));
        umap.get("КМД").link(umap.get("Дисковод"), gmap.get("КМД").getProbTo("Дисковод"));

        umap.get("Дисковод").link(umap.get("КМД"), gmap.get("Дисковод").getProbTo("КМД"));


    }

    void buildUI() {

        Color color = new Color(240, 230, 140);
        Color color2 = new Color(245, 235, 120);
        Color color3 = new Color(220, 100, 100);

        //=========p1======================
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 4));
        p1.setBackground(new Color(200, 200, 100));
        p1.add(gmap.get("ЦП"));
        p1.add(gmap.get("Півн. міст"));
        p1.add(gmap.get("Півд. міст"));
        p1.add(gmap.get("КМД"));

        p1.add(new Container());
        p1.add(gmap.get("ГП"));
        p1.add(gmap.get("ISA"));
        p1.add(gmap.get("Дисковод"));

        p1.add(new Container());
        p1.add(gmap.get("ОП"));
        p1.add(gmap.get("COM"));
        p1.add(new Container());

        setLayout(new GridLayout(2, 1));
        add(p1);
        //===========p1==============================

        //==============p2=====================
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        //-------------cp------------------------

        JPanel cp = new JPanel();
        cp.setLayout(new GridLayout(2, 1));
        cp.setBorder(BorderFactory.createTitledBorder("Моделювання"));
        cp.setBackground(new Color(200, 200, 100));

        Container con1 = new Container();
        con1.setLayout(new GridLayout(2, 2));
        Container con2 = new Container();
        con2.setLayout(new GridLayout(1, 2));


        //...........................................................
        con1.add(new JLabel("Кількість задач "));
        JSpinner spTasks = new JSpinner(new SpinnerNumberModel(2, 1, 30, 1));
        spTasks.getEditor().getComponent(0).setBackground(color);
        spTasks.getComponent(0).setBackground(color2);
        spTasks.getComponent(1).setBackground(color2);
        con1.add(spTasks);

        JTextArea statusArea1 = new JTextArea();
        statusArea1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        statusArea1.setEditable(false);
        statusArea1.setMargin(new Insets(5, 30, 0, 5));
        statusArea1.setBackground(cp.getBackground());
        statusArea1.setWrapStyleWord(true);
        statusArea1.setLineWrap(true);

        JButton bBuildTree = new JButton("Побудувати дерево");
        bBuildTree.setBackground(color3);
        bBuildTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                treeBuilder = new TreeBuilder();
                uList = new ArrayList<Unit>();
                Iterator it = umap.entrySet().iterator();
                while (it.hasNext())
                    uList.add((Unit) ((Map.Entry) it.next()).getValue());
                Collections.sort(uList, new Comparator<Unit>() {
                    @Override
                    public int compare(Unit o1, Unit o2) {
                        return o1.getId() - o2.getId();
                    }
                });
                treeBuilder.init(uList, (int) spTasks.getValue());
                treeBuilder.start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!treeBuilder.isFinished()) {
                            statusArea1.setText("Вузлів : " + treeBuilder.getNodesNum() +
                                    "\nРебер : " + treeBuilder.getEdgesNum());
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        statusArea1.setText("Вузлів : " + treeBuilder.getNodesNum() +
                                "\nРебер : " + treeBuilder.getEdgesNum());

                        ArrayList<double[]> rese = treeBuilder.getEdges();
                        for (int i = 0; i < rese.size(); i++) {
                            System.out.print("e" + i + " : ");
                            System.out.print("(" + rese.get(i)[0] + "," + rese.get(i)[1] + ")");
                            System.out.println();
                        }
                        ArrayList<int[][]> res = treeBuilder.getNodes();
                        for (int i = 0; i < res.size(); i++) {
                            System.out.print("n" + i + " : ");
                            for (int j = 0; j < res.get(i).length - 1; j++)
                                System.out.print("(" + res.get(i)[j][0] + "," + res.get(i)[j][1] + "," + res.get(i)[j][2] + ")");
                            System.out.println();
                        }

                    }
                }).start();


            }
        });

        con1.add(bBuildTree);
        con1.add(statusArea1);
        cp.add(con1);

        //........................................

//		String delta = String.valueOf(Character.toChars(916));
//		con2.add(new JLabel("Величина " + delta+ "t"));
//		JSpinner spDt = new JSpinner(new SpinnerNumberModel(0.1, 0.000001, 10, 0.01));
//		spDt.getEditor().getComponent(0).setBackground(color);
//		spDt.getComponent(0).setBackground(color2);
//		spDt.getComponent(1).setBackground(color2);
//		con2.add(spDt);
        double deltaT = 0.01;

        JTextArea statusArea2 = new JTextArea();
        statusArea2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        statusArea2.setEditable(false);
        statusArea2.setMargin(new Insets(5, 30, 0, 5));
        statusArea2.setBackground(cp.getBackground());
        statusArea2.setWrapStyleWord(true);
        statusArea2.setLineWrap(true);

        JButton bSolve = new JButton("Моделювати");
        bSolve.setBackground(color3);
        bSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calculator calc = new Calculator();
                calc.init(treeBuilder.getNodes(), treeBuilder.getEdges(), deltaT, uList);
                calc.start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (calc.isFinished())
                                break;
                            statusArea2.setText("Обчислення.");
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            }
                            if (calc.isFinished())
                                break;
                            statusArea2.setText("Обчислення..");
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            }

                            if (calc.isFinished())
                                break;
                            statusArea2.setText("Обчислення...");
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            }
                            if (calc.isFinished())
                                break;
                            statusArea2.setText("Обчислення....");
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            }
                        }
                        String filename = spTasks.getValue() + ".txt";
                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(filename);
                            for (int i = 0; i < uList.size(); i++) {
                                if (uList.get(i).getName().compareTo("ЦП") == 0) {
                                    for (int j = 0; j < ((CPU) uList.get(i)).getCoresNum(); j++)
                                        writer.println(((CPU) uList.get(i)).getCoresIntensity(j + 1));
                                } else
                                    writer.println(uList.get(i).getIntensity());
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            writer.close();
                        }
                        ((MyTableModel) table.getModel()).update(umap);
                        table.repaint();
                        statusArea2.setText("");
                    }
                }).start();


            }
        });

        con2.add(bSolve);
        con2.add(statusArea2);
        cp.add(con2);

        //.............................................
        p2.add(cp, BorderLayout.WEST);
        //-----------cp-----------------------

        table = new JTable(new MyTableModel(umap));
        String header[] = {"Пристрій", "Відсоток зайнятості"};
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
            column.setHeaderValue(header[i]);
        }
        table.getTableHeader().setBackground(new Color(200, 255, 100, 55));
        table.getTableHeader().setForeground(new Color(200, 50, 50, 255));

        table.setOpaque(false);
        table.setRowHeight(32);
        table.setFont(new Font("DeJavu Sans Light", Font.PLAIN, 18));
        table.setBackground(new Color(0, 0, 0, 10));
        table.setForeground(new Color(200, 200, 0, 255));
        table.setSelectionForeground(new Color(100, 100, 0, 0));
        table.setCellSelectionEnabled(false);
        DefaultTableCellRenderer renderer = new MyRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setViewportView(table);
        p2.add(scroll, BorderLayout.CENTER);

        add(p2);

        //=============p2=======================

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
//		LookAndFeelInfo[] lf = UIManager.getInstalledLookAndFeels();
//		for(int i = 0; i < lf.length; i++)
//			System.out.println((i+1)+" "+lf[i].getClassName());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        setPreferredSize(new Dimension((int) width, (int) height));
        pack();
        setVisible(true);


    }


    class MyTableModel extends AbstractTableModel {
        private int COL = 2, ROW = 11;
        private HashMap<String, Unit> umap;

        MyTableModel(HashMap<String, Unit> umap) {
            update(umap);
        }

        public void update(HashMap<String, Unit> umap) {
            this.umap = umap;
            if (umap != null)
                ROW = umap.size() + ((CPU) umap.get("ЦП")).getCoresNum() - 1;
        }

        public int getColumnCount() {
            return COL;
        }

        public int getRowCount() {
            return ROW;
        }

        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0:
                    switch (row) {
                        case 0:
                            return "ЦП(1 ядро)";
                        case 1:
                            return "ЦП(2 ядра)";
                        case 2:
                            return "ЦП(3 ядра)";
                        case 3:
                            return "Півн. міст";
                        case 4:
                            return "ГП";
                        case 5:
                            return "ОП";
                        case 6:
                            return "Півд. міст";
                        case 7:
                            return "ISA";
                        case 8:
                            return "COM";
                        case 9:
                            return "КМД";
                        case 10:
                            return "Дисковод";
                    }
                    break;
                case 1:
                    if (umap != null)
                        if (((String) getValueAt(row, 0)).startsWith("ЦП"))
                            switch ((String) getValueAt(row, 0)) {
                                case "ЦП(1 ядро)":
                                    return ((CPU) umap.get("ЦП")).getCoresIntensity(1) * 100;
                                case "ЦП(2 ядра)":
                                    return ((CPU) umap.get("ЦП")).getCoresIntensity(2) * 100;
                                case "ЦП(3 ядра)":
                                    return ((CPU) umap.get("ЦП")).getCoresIntensity(3) * 100;
                            }
                        else
                            return umap.get(getValueAt(row, 0)).getIntensity() * 100;
            }
            return "";
        }

        public boolean isCellEditable(int row, int col) {
            return col != 0;
        }

        public void setValueAt(Object value, int row, int col) {
            double r = 0;
            boolean flag = true;
            try {
                r = Double.parseDouble(value.toString());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Помилка при вводі");
                flag = false;
            }
            if (flag) {
                switch (col) {
                    case 1:
                        if (((String) getValueAt(row, 0)).startsWith("ЦП"))
                            switch ((String) getValueAt(row, 0)) {
                                case "ЦП(1 ядро)":
                                    ((CPU) umap.get("ЦП")).setCoresIntensity(1, r);
                                case "ЦП(2 ядра)":
                                    ((CPU) umap.get("ЦП")).setCoresIntensity(2, r);
                                case "ЦП(3 ядра)":
                                    ((CPU) umap.get("ЦП")).setCoresIntensity(3, r);
                            }
                        else
                            umap.get(getValueAt(row, 0)).setIntensity(r);
                }
                fireTableDataChanged();
            }
        }
    } //MyTableModel


    static public class MyRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
            if (column == 0) {
                c.setBackground(new Color(200, 255, 100, 55));
                c.setForeground(new Color(200, 50, 50, 255));
            } else {
                c.setBackground(new Color(0, 0, 0, 100));
                c.setForeground(new Color(255, 255, 255, 255));
            }
            return c;
        }

    }
}
