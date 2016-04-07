package zak.adcs;

import com.thoughtworks.xstream.XStream;
import zak.adcs.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Program {

    private static final File CONFIGURATION_FILE = new File("conf.xml");

    private static Configuration configuration;

    private static void readConfigurationFile() {
        try {
            BufferedReader input = new BufferedReader(new FileReader(CONFIGURATION_FILE));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
            }
            input.close();
            XStream xStream = new XStream();
            xStream.alias("configuration", Configuration.class);
            configuration = (Configuration) xStream.fromXML(builder.toString());
        } catch (IOException e) {
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            configuration = new Configuration(new Rectangle((int) ((screenSize.getWidth() - MainFrame.MIN_WIDTH) / 2),
                    (int) ((screenSize.getHeight() - MainFrame.MIN_HEIGHT) / 2), MainFrame.MIN_WIDTH, MainFrame.MIN_HEIGHT));
        }
    }

    public static void writeConfigurationFile(Rectangle mainFrameBounds) {
        try {
            configuration = new Configuration(mainFrameBounds);
            XStream xStream = new XStream();
            xStream.alias("configuration", Configuration.class);
            String xml = xStream.toXML(configuration);
            PrintWriter output = new PrintWriter(CONFIGURATION_FILE);
            output.write(xml);
            output.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Can not save settings", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        readConfigurationFile();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame mainFrame = new MainFrame(configuration.getMainFrameBounds());
                mainFrame.setVisible(true);
            }
        });
    }

}
