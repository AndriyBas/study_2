package zak.adcs.logicscheme.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class IOElementDialog extends JDialog {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 65;

    private JLabel nameLabel;
    private JTextField nameField;
    private JButton okButton;
    private JButton cancelButton;

    private boolean okClicked;

    public IOElementDialog(Point location, String title) {
        super();
        setLocation(location);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle(title);
        setModal(true);
        okClicked = false;
        nameLabel = new JLabel("Ім’я елемента");
        nameField = new JTextField();
        okButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Ви не ввели ім’я елемента", "Помилка!",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    okClicked = true;
                    setVisible(false);
                }
            }
        });
        okButton.setText("Ok");
        cancelButton = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        cancelButton.setText("Відмінити");
        setLayout(new GridLayout(2, 2));
        add(nameLabel);
        add(nameField);
        add(okButton);
        add(cancelButton);
    }

    public String getName() {
        return nameField.getText();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setName(String name) {
        nameField.setText(name);
    }

}
