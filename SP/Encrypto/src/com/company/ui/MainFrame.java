package com.company.ui;

import com.company.encode.DES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by andriybas on 11/30/14.
 */
public class MainFrame extends JFrame {
    private JPanel panelRoot;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JTabbedPane tabbedPaneRoot;
    private JButton enDESbuttonPick;
    private JButton enDESSendBtn;
    private JTextField a127001TextField;
    private JLabel enDESfileName;

    File chosenFile;

    public MainFrame() {
        super("Encrypto");

        add(panelRoot);


        int width = 600;
        int height = 400;

        setMinimumSize(new Dimension(width, height));


        init();

        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void init() {

        enDESbuttonPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.setCurrentDirectory(new File("."));

                int returnVal = fc.showOpenDialog(MainFrame.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    chosenFile = fc.getSelectedFile();
                    updateLabel(enDESfileName);
                }
            }
        });

        enDESSendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DES des = new DES();
                    des.encrypt(new File("wow.txt"));

                    des.decrypt(new File("temp_en_des_out.txt"));

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    private void updateLabel(JLabel label) {
        if (chosenFile != null) {
            label.setText(chosenFile.getName());
        }
    }


}
