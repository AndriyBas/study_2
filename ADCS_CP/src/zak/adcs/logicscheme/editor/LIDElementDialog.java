package zak.adcs.logicscheme.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LIDElementDialog extends JDialog {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 130;

    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel inertiaLabel;
    private JSpinner inertiaSpinner;
    private JLabel dynamicLabel;
    private JSpinner dynamicSpinner;
    private JLabel inCountLabel;
    private JSpinner inCountSpinner;
    private JButton okButton;
    private JButton cancelButton;

    private boolean okClicked;

    public LIDElementDialog(Point location, String title) {
        super();
        setLocation(location);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle(title);
        setModal(true);
        okClicked = false;
        nameLabel = new JLabel("Ім’я елемента");
        nameField = new JTextField();
        inertiaLabel = new JLabel("Ітераційна затримка");
        inertiaSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        dynamicLabel = new JLabel("Динамічна затримка");
        dynamicSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        inCountLabel = new JLabel("Кількість входів");
        inCountSpinner = new JSpinner(new SpinnerNumberModel(2, 2, Integer.MAX_VALUE, 1));
        okButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                okClicked = true;
                setVisible(false);
            }
        });
        okButton.setText("OK");
        cancelButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        cancelButton.setText("Відмінити");
        setLayout(new GridLayout(5, 2));
        add(nameLabel);
        add(nameField);
        add(inertiaLabel);
        add(inertiaSpinner);
        add(dynamicLabel);
        add(dynamicSpinner);
        add(inCountLabel);
        add(inCountSpinner);
        add(okButton);
        add(cancelButton);
    }

    public String getName() {
        return nameField.getText();
    }

    public int getInertiaDelay() {
        return (Integer) inertiaSpinner.getValue();
    }

    public int getDynamicDelay() {
        return (Integer) dynamicSpinner.getValue();
    }

    public int getInCount() {
        return (Integer) inCountSpinner.getValue();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setInertiaDelay(int inertiaDelay) {
        inertiaSpinner.setValue(inertiaDelay);
    }

    public void setDynamicDelay(int dynamicDelay) {
        dynamicSpinner.setValue(dynamicDelay);
    }

    public void setInCount(int inCount) {
        inCountSpinner.setValue(inCount);
    }

    public void setName(String name) {
        nameField.setText(name);
    }

}
