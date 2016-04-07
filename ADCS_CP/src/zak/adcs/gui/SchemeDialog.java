package zak.adcs.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchemeDialog extends JDialog {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 300;

    private static final int MIN_SIZE_VALUE = 400;
    private static final int MAX_SIZE_VALUE = 4000;
    private static final int DEFAULT_SCHEME_WIDTH = 1600;
    private static final int DEFAULT_SCHEME_HEIGHT = 1200;

    private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final Color DEFAULT_STANDART_COLOR = Color.CYAN;
    private static final Color DEFAULT_SELECTED_COLOR = Color.PINK;
    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

    private static final Font DEFAULT_FONT = new Font("Sans Serif", Font.PLAIN, 12);

    private static final String COLOR_PREVIEW_TEXT = "        ";

    private JLabel sizeLabel;
    private JLabel xLabel;
    private JSpinner xSpinner;
    private JSpinner ySpinner;
    private JLabel backgroundLabel;
    private JLabel bColorLabel;
    private JButton bColorButton;
    private JLabel standartLabel;
    private JLabel stColorLabel;
    private JButton stColorButton;
    private JLabel selectedLabel;
    private JLabel slColorLabel;
    private JButton slColorButton;
    private JLabel textLabel;
    private JLabel tColorLabel;
    private JButton tColorButton;
    private JLabel fontLabel;
    private JComboBox fontBox;
    private JButton okButton;
    private JButton cancelButton;

    private boolean okClicked;

    public SchemeDialog(int x, int y, String title) {
        super();
        setLocation(x, y);
        setTitle(title);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setModal(true);
        okClicked = false;
        sizeLabel = new JLabel("Розмір схеми");
        xLabel = new JLabel(" x ");
        xSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_SCHEME_WIDTH, MIN_SIZE_VALUE, MAX_SIZE_VALUE, 1));
        ySpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_SCHEME_HEIGHT, MIN_SIZE_VALUE, MAX_SIZE_VALUE, 1));
        backgroundLabel = new JLabel("Колір фону");
        bColorLabel = new JLabel(COLOR_PREVIEW_TEXT);
        bColorLabel.setOpaque(true);
        bColorLabel.setBackground(DEFAULT_BACKGROUND_COLOR);
        standartLabel = new JLabel("Колір елементів");
        stColorLabel = new JLabel(COLOR_PREVIEW_TEXT);
        stColorLabel.setOpaque(true);
        stColorLabel.setBackground(DEFAULT_STANDART_COLOR);
        selectedLabel = new JLabel("Колір виділення");
        slColorLabel = new JLabel(COLOR_PREVIEW_TEXT);
        slColorLabel.setOpaque(true);
        slColorLabel.setBackground(DEFAULT_SELECTED_COLOR);
        textLabel = new JLabel("Колів тексту");
        tColorLabel = new JLabel(COLOR_PREVIEW_TEXT);
        tColorLabel.setOpaque(true);
        tColorLabel.setBackground(DEFAULT_TEXT_COLOR);
        bColorButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Color result = JColorChooser.showDialog(rootPane, backgroundLabel.getText(), bColorLabel.getBackground());
                if (result != null) {
                    bColorLabel.setBackground(result);
                }
            }
        });
        bColorButton.setText("Змінити...");
        stColorButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Color result = JColorChooser.showDialog(rootPane, standartLabel.getText(), stColorLabel.getBackground());
                if (result != null) {
                    stColorLabel.setBackground(result);
                }
            }
        });
        stColorButton.setText("Змінити...");
        slColorButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Color result = JColorChooser.showDialog(rootPane, selectedLabel.getText(), slColorLabel.getBackground());
                if (result != null) {
                    slColorLabel.setBackground(result);
                }
            }
        });
        slColorButton.setText("Змінити...");
        tColorButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Color result = JColorChooser.showDialog(rootPane, textLabel.getText(), tColorLabel.getBackground());
                if (result != null) {
                    tColorLabel.setBackground(result);
                }
            }
        });
        tColorButton.setText("Змінити...");
        fontLabel = new JLabel("Шрифт");
        fontBox = new JComboBox();
        fontBox.setEditable(false);
        fontBox.addItem("Sans Serif");
        fontBox.addItem("Serif");
        fontBox.addItem("Monospaced");
        fontBox.addItem("Dialog");
        fontBox.addItem("Dialog Input");
        fontBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fontBox.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, 12));
                repaint();
            }
        });
        fontBox.setSelectedIndex(0);
        fontBox.setFont(DEFAULT_FONT);
        okButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                okClicked = true;
                setVisible(false);
            }
        });
        okButton.setText("Ok");
        cancelButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                okClicked = false;
                setVisible(false);
            }
        });
        cancelButton.setText("Відмінити");
        Box sizeBox = Box.createHorizontalBox();
        sizeBox.add(Box.createHorizontalStrut(10));
        sizeBox.add(sizeLabel);
        sizeBox.add(Box.createHorizontalStrut(10));
        sizeBox.add(xSpinner);
        sizeBox.add(Box.createHorizontalStrut(5));
        sizeBox.add(xLabel);
        sizeBox.add(Box.createHorizontalStrut(5));
        sizeBox.add(ySpinner);
        sizeBox.add(Box.createHorizontalStrut(10));
        Box bColorBox = Box.createHorizontalBox();
        bColorBox.add(Box.createHorizontalStrut(10));
        bColorBox.add(backgroundLabel);
        bColorBox.add(Box.createHorizontalStrut(10));
        bColorBox.add(bColorLabel);
        bColorBox.add(Box.createHorizontalStrut(10));
        bColorBox.add(bColorButton);
        bColorBox.add(Box.createHorizontalStrut(10));
        Box stColorBox = Box.createHorizontalBox();
        stColorBox.add(Box.createHorizontalStrut(10));
        stColorBox.add(standartLabel);
        stColorBox.add(Box.createHorizontalStrut(10));
        stColorBox.add(stColorLabel);
        stColorBox.add(Box.createHorizontalStrut(10));
        stColorBox.add(stColorButton);
        stColorBox.add(Box.createHorizontalStrut(10));
        Box slColorBox = Box.createHorizontalBox();
        slColorBox.add(Box.createHorizontalStrut(10));
        slColorBox.add(selectedLabel);
        slColorBox.add(Box.createHorizontalStrut(10));
        slColorBox.add(slColorLabel);
        slColorBox.add(Box.createHorizontalStrut(10));
        slColorBox.add(slColorButton);
        slColorBox.add(Box.createHorizontalStrut(10));
        Box tColorBox = Box.createHorizontalBox();
        tColorBox.add(Box.createHorizontalStrut(10));
        tColorBox.add(textLabel);
        tColorBox.add(Box.createHorizontalStrut(10));
        tColorBox.add(tColorLabel);
        tColorBox.add(Box.createHorizontalStrut(10));
        tColorBox.add(tColorButton);
        tColorBox.add(Box.createHorizontalStrut(10));
        Box fBox = Box.createHorizontalBox();
        fBox.add(Box.createHorizontalStrut(10));
        fBox.add(fontLabel);
        fBox.add(Box.createHorizontalStrut(10));
        fBox.add(fontBox);
        fBox.add(Box.createHorizontalStrut(10));
        Box buttonsBox = Box.createHorizontalBox();
        buttonsBox.add(Box.createHorizontalStrut(10));
        buttonsBox.add(okButton);
        buttonsBox.add(Box.createHorizontalStrut(10));
        buttonsBox.add(cancelButton);
        buttonsBox.add(Box.createHorizontalStrut(10));
        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(sizeBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(bColorBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(stColorBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(slColorBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(tColorBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(fBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(buttonsBox);
        vBox.add(Box.createVerticalStrut(10));
        add(vBox);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Dimension getSchemeSize() {
        return new Dimension((Integer) xSpinner.getValue(), (Integer) ySpinner.getValue());
    }

    public Color getBackgroundColor() {
        return bColorLabel.getBackground();
    }

    public Color getStandartColor() {
        return stColorLabel.getBackground();
    }

    public Color getSelectedColor() {
        return slColorLabel.getBackground();
    }

    public Color getTextColor() {
        return tColorLabel.getBackground();
    }

    public Font getTextFont() {
        return fontBox.getFont();
    }

    //TODO Maybe to code methods for editing scheme options

}
