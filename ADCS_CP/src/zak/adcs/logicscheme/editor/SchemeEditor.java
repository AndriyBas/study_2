package zak.adcs.logicscheme.editor;

import zak.adcs.ImageIconLoader;
import zak.adcs.logicscheme.modelling.SchemeTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SchemeEditor extends JPanel {

    public static final String ADD_OUTS = "Додавання виходів";
    public static final String ADD_IN = "Додавання входів";
    public static final String ADD_ELEMENTS = "Додавання елементів";

    private SchemePanel panel;
    private JToolBar toolBar;
    private JLabel statusLabel;
    private Font textFont;

    private SchemeEditor() {
        super();
        toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        ImageIconLoader iconLoader = ImageIconLoader.getInstance();
        JButton tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.MOVING);
                statusLabel.setText("Переміщення елементів");
            }
        });
        tempButton.setToolTipText("Переміщення елементів");
        tempButton.setIcon(iconLoader.getImageIcon("/img/cursor.png"));
        toolBar.addSeparator();
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.ADDING_IN);
                statusLabel.setText(ADD_IN);
            }
        });
        tempButton.setToolTipText(ADD_IN);
        tempButton.setIcon(iconLoader.getImageIcon("/img/in_element.png"));
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.ADDING_OUT);
                statusLabel.setText(ADD_OUTS);
            }
        });
        tempButton.setToolTipText(ADD_OUTS);
        tempButton.setIcon(iconLoader.getImageIcon("/img/out_element.png"));
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.ADDING_AND);
                statusLabel.setText(ADD_ELEMENTS + " І");
            }
        });
        tempButton.setToolTipText(ADD_ELEMENTS + " І");
        tempButton.setIcon(iconLoader.getImageIcon("/img/and_element.png"));
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.ADDING_OR);
                statusLabel.setText(ADD_ELEMENTS + " АБО");
            }
        });
        tempButton.setToolTipText(ADD_ELEMENTS + " АБО");
        tempButton.setIcon(iconLoader.getImageIcon("/img/or_element.png"));
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.ADDING_NAND);
                statusLabel.setText(ADD_ELEMENTS + " АБО-НЕ");
            }
        });
        tempButton.setToolTipText(ADD_ELEMENTS + " АБО-НЕ");
        tempButton.setIcon(iconLoader.getImageIcon("/img/nand_element.png"));
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.ADDING_NOR);
                statusLabel.setText(ADD_ELEMENTS + " АБО-НЕ");
            }
        });
        tempButton.setToolTipText(ADD_ELEMENTS + " АБО-НЕ");
        tempButton.setIcon(iconLoader.getImageIcon("/img/nor_element.png"));
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.ADDING_XOR);
                statusLabel.setText(ADD_ELEMENTS + " Виключне АБО");
            }
        });
        tempButton.setToolTipText(ADD_ELEMENTS + " Виключне АБО");
        tempButton.setIcon(iconLoader.getImageIcon("/img/xor_element.png"));
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.ADDING_CONN);
                statusLabel.setText("Додавання з’єднань");
            }
        });
        tempButton.setToolTipText("Додавання з’єднань");
        tempButton.setIcon(iconLoader.getImageIcon("/img/conn_element.png"));
        toolBar.addSeparator();
        tempButton = toolBar.add(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                panel.getModel().setCurrentAction(SchemeModel.CurrentAction.DELETING);
                statusLabel.setText("Видалення елементів і з’єднань");
            }
        });
        tempButton.setToolTipText("Видалення елементів і з’єднань");
        tempButton.setIcon(iconLoader.getImageIcon("/img/delete_element.png"));
        statusLabel = new JLabel("Переміщення елементів");
    }

    public SchemeEditor(Dimension panelSize, Color panelColor, Color standartColor, Color selectedColor, Color textColor,
                        Font font, JRootPane rootFrame) {
        this();
        panel = new SchemePanel(new SchemeModel(panelSize, panelColor, standartColor, selectedColor, textColor, font),
                rootFrame);
        setLayout(new BorderLayout());
        add(statusLabel, BorderLayout.SOUTH);
        add(toolBar, BorderLayout.WEST);
        add(new JScrollPane(panel));
    }

    public SchemeEditor(SchemeModel schemeModel, JRootPane rootFrame) {
        this();
        panel = new SchemePanel(schemeModel, rootFrame);
        setLayout(new BorderLayout());
        add(statusLabel, BorderLayout.SOUTH);
        add(toolBar, BorderLayout.WEST);
        add(new JScrollPane(panel));
    }

    public SchemeTableModel getSchemeTableModel() {
        return panel.getModel().getSchemeTableModel();
    }

    public Color getStandartColor() {
        return panel.getModel().getStandartColor();
    }

    public Color getBackgroundColor() {
        return panel.getModel().getBackgroundColor();
    }

    public boolean isModified() {
        return panel.getModel().isModified();
    }

    public String getXMLScheme() {
        return panel.getModel().toXML();
    }

    public void setPanelEnableToModify(boolean enable) {
        panel.setEnableToModify(enable);
    }

    public Font getTextFont() {
        return panel.getModel().getFont();
    }

    public Color getTextColor() {
        return panel.getModel().getTextColor();
    }

}
