import ui.Graph;

import javax.swing.*;

/**
 * Created by andriybas on 10/3/14.
 */
public class MainGraph {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame graph = new Graph();
        graph.setVisible(true);
    }
}
