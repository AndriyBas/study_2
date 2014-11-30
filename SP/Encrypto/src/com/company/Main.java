package com.company;

import com.company.transfer.Receiver;
import com.company.transfer.Sender;
import com.company.ui.MainFrame;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        // write your code here

//        new Thread(new Receiver()).start();
//        new Sender().send(new File("wow.txt"));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new MainFrame();
    }
}
