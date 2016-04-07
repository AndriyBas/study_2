package zak.adcs.logicscheme.modelling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ModellingPanel extends JPanel {

    private JLabel inputSetLabel;
    private JComboBox inputSetBox;
    private JLabel startSetLabel;
    private JComboBox startSetBox;
    private JButton stepButton;
    private JButton modellingButton;
    private JButton allModellingButton;
    private JButton clearButton;
    private JSplitPane splitPane;
    private JTable modellingTable;
    private TimeDiagramPanel diagramPanel;

    private ModellingModel modellingModel;

    public ModellingPanel(SchemeTableModel schemeTableModel, Color backgroundColor, Color standartColor, Color textColor,
                          Font font) {
        super();
        modellingModel = new ModellingModel(schemeTableModel.getInNames(), schemeTableModel.getElementNames(),
                schemeTableModel.getInertiaDelay(), schemeTableModel.getDynamicDelay(),
                schemeTableModel.getLogicFunctions(), schemeTableModel.getConnectivityMatrixExceptOut(),
                backgroundColor, standartColor, textColor, font);
        StringBuilder builder = new StringBuilder();
        builder.append("Вхідний набір( ");
        for (String s : schemeTableModel.getInNames()) {
            builder.append(s);
            builder.append(" ");
        }
        builder.append(")");
        inputSetLabel = new JLabel(builder.toString());
        inputSetBox = new JComboBox();
        inputSetBox.setEditable(false);
        for (int i = 0; i < Math.pow(2, schemeTableModel.getInCount()); i++) {
            String temp = Integer.toBinaryString(i);
            while (temp.length() < schemeTableModel.getInCount()) {
                temp = "0" + temp;
            }
            inputSetBox.addItem(temp);
        }
        builder = new StringBuilder();
        builder.append("Установчий набір ( ");
        for (String s : schemeTableModel.getInNames()) {
            builder.append(s);
            builder.append(" ");
        }
        builder.append(")");
        startSetLabel = new JLabel(builder.toString());
        startSetBox = new JComboBox();
        startSetBox.setEditable(false);
        for (int i = 0; i < Math.pow(2, schemeTableModel.getInCount()); i++) {
            String temp = Integer.toBinaryString(i);
            while (temp.length() < schemeTableModel.getInCount()) {
                temp = "0" + temp;
            }
            startSetBox.addItem(temp);
        }
        stepButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (modellingModel.isBusy()) {
                    modellingModel.step();
                } else {
                    if (modellingModel.isClear()) {
                        modellingModel.step((String) inputSetBox.getSelectedItem(), (String) startSetBox.getSelectedItem());
                    } else {
                        modellingModel.step((String) inputSetBox.getSelectedItem(), true);
                    }
                }
                modellingModel.fireTableDataChanged();
                revalidate();
                repaint();
            }
        });
        stepButton.setText("Крок");
        modellingButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                modellingModel.modelling((String) inputSetBox.getSelectedItem(), (String) startSetBox.getSelectedItem());
                modellingModel.fireTableDataChanged();
                revalidate();
                repaint();
            }
        });

        modellingButton.setText("Моделювати");
        allModellingButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < inputSetBox.getItemCount(); i++) {
                    modellingModel.modelling((String) inputSetBox.getItemAt(i), (String) startSetBox.getSelectedItem());
                }
                modellingModel.fireTableDataChanged();
                revalidate();
                repaint();
            }
        });

        allModellingButton.setText("Моделювати всі набори");
        clearButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                modellingModel.clear();
                modellingModel.fireTableDataChanged();
                revalidate();
                repaint();
            }
        });
        clearButton.setText("Очистити");
        modellingTable = new JTable(modellingModel);
        modellingTable.setDragEnabled(false);
        diagramPanel = new TimeDiagramPanel(modellingModel);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(new JScrollPane(modellingTable));
        splitPane.setBottomComponent(new JScrollPane(diagramPanel));
        splitPane.setDividerLocation(splitPane.getPreferredSize().height / 2);
        int strutSize = 5;
        Box hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalStrut(strutSize));
        hBox.add(inputSetLabel);
        hBox.add(Box.createHorizontalStrut(strutSize));
        hBox.add(inputSetBox);
        hBox.add(Box.createHorizontalStrut(strutSize));
        hBox.add(startSetLabel);
        hBox.add(Box.createHorizontalStrut(strutSize));
        hBox.add(startSetBox);
        hBox.add(Box.createHorizontalStrut(strutSize));
        hBox.add(stepButton);
        hBox.add(Box.createHorizontalStrut(strutSize));
        hBox.add(modellingButton);
        hBox.add(Box.createHorizontalStrut(strutSize));
        hBox.add(allModellingButton);
        hBox.add(Box.createHorizontalStrut(strutSize));
        hBox.add(clearButton);
        hBox.add(Box.createHorizontalStrut(strutSize));
        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(2 * strutSize));
        vBox.add(hBox);
        vBox.add(Box.createVerticalStrut(2 * strutSize));
        setLayout(new BorderLayout());
        add(new JScrollPane(vBox), BorderLayout.NORTH);
        add(splitPane);
    }
}
