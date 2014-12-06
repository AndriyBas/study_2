package com.company.ui;

import com.company.encode.AES;
import com.company.encode.DES;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
    private JTextField enDesPasswordText;
    private JLabel decDESfileName;
    private JButton decDESbuttonPick;
    private JTextField decDESPasswordText;
    private JTextArea decTextArea;
    private JButton decDESDecryptBtn;
    private JLabel enAESfileName;
    private JButton enAESbuttonPick;
    private JTextField enAesPasswordText;
    private JButton enAESSendBtn;
    private JLabel decAESfileName;
    private JButton decAESbuttonPick;
    private JTextField decAESPasswordText;
    private JButton decAESDecryptBtn;

    File enDESChosenFile;
    File decDESChosenFile;

    File enAESChosenFile;
    File decAESChosenFile;

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

        initDES();
        initAES();

    }

    private void initDES() {
        enDESbuttonPick.addActionListener(e -> {

            final JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.setCurrentDirectory(new File("."));

            int returnVal = fc.showOpenDialog(MainFrame.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                enDESChosenFile = fc.getSelectedFile();
                enDESfileName.setText(enDESChosenFile.getName());
            }
        });

        enDESSendBtn.addActionListener(e -> {
            try {

                if (enDESChosenFile != null) {
                    DES des = new DES();
                    des.encrypt(enDESChosenFile);

//                    DES desDec = new DES(des.getKeyHex());
//                    desDec.decrypt(new File("temp_en_des_out.txt"));

                    enDesPasswordText.setText(des.getKeyHex());

                } else {
                    //custom title, error icon
                    JOptionPane.showMessageDialog(this,
                            "Chose file",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        decDESbuttonPick.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.setCurrentDirectory(new File("."));

            int returnVal = fc.showOpenDialog(MainFrame.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                decDESChosenFile = fc.getSelectedFile();
                decDESfileName.setText(decDESChosenFile.getName());
            }
        });

        decDESDecryptBtn.addActionListener(e -> {
                    try {

                        if (decDESChosenFile != null) {
//                            DES des = new DES();
//                            des.encrypt(enDESChosenFile);
                            String password = decDESPasswordText.getText().trim();

                            DES desDec = new DES(password);
                            desDec.decrypt(new File("temp_en_des_out.txt"));

                            decTextArea.removeAll();

                            String everything = readFromFile("temp_de_des_out.txt");


                            decTextArea.append(everything);


                        } else {
                            //custom title, error icon
                            JOptionPane.showMessageDialog(this,
                                    "Chose file",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
        );
    }

    private void initAES() {
        enAESbuttonPick.addActionListener(e -> {

            final JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.setCurrentDirectory(new File("."));

            int returnVal = fc.showOpenDialog(MainFrame.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                enAESChosenFile = fc.getSelectedFile();
                enAESfileName.setText(enAESChosenFile.getName());
            }
        });

        enAESSendBtn.addActionListener(e -> {
            try {

                if (enAESChosenFile != null) {
                    AES aes = new AES();
                    aes.encrypt(enAESChosenFile);

                    enAesPasswordText.setText(aes.getKeyHex());

                } else {
                    //custom title, error icon
                    JOptionPane.showMessageDialog(this,
                            "Chose file",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e1) {
                e1.printStackTrace();
                decTextArea.append("invalid key");
            }
        });


        decAESbuttonPick.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.setCurrentDirectory(new File("."));

            int returnVal = fc.showOpenDialog(MainFrame.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                decAESChosenFile = fc.getSelectedFile();
                decAESfileName.setText(decAESChosenFile.getName());
            }
        });

        decAESDecryptBtn.addActionListener(e -> {
                    try {

                        if (decAESChosenFile != null) {
                            String password = decAESPasswordText.getText().trim();

                            AES aesDec = new AES(password);
                            aesDec.decrypt(new File("temp_en_aes_out.txt"));

                            decTextArea.removeAll();

                            String everything = readFromFile("temp_de_aes_out.txt");


                            decTextArea.append(everything);


                        } else {
                            //custom title, error icon
                            JOptionPane.showMessageDialog(this,
                                    "Chose file",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        decTextArea.append("invalid key");
                    }
                }
        );
    }

    private String readFromFile(String fileName) {

        String everything = null;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
        }
        return everything;
    }

}
