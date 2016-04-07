package zak.adcs.gui;

import zak.adcs.ImageIconLoader;
import zak.adcs.Program;
import zak.adcs.logicscheme.editor.SchemeModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class MainFrame extends JFrame {

    public static final int MIN_WIDTH = 1000;
    public static final int MIN_HEIGHT = 750;

    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JDesktopPane desktopPane;
    private JFileChooser fileChooser;

    private NewAction newAction;
    private OpenAction openAction;
    private SaveAction saveAction;
    private SaveAsAction saveAsAction;
    private CloseAction closeAction;
    private ExitAction exitAction;
    private ConnectivityAction connectivityAction;
    private ModellingAction modellingAction;
    private EditSchemeAction editSchemeAction;
    private AboutAction aboutAction;

    public MainFrame(Rectangle bounds) {
        super();
        setBounds(bounds);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setTitle("Система логічного моделювання");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new MainFrameListener());
        newAction = new NewAction();
        openAction = new OpenAction();
        saveAction = new SaveAction();
        saveAsAction = new SaveAsAction();
        closeAction = new CloseAction();
        exitAction = new ExitAction();
        connectivityAction = new ConnectivityAction();
        modellingAction = new ModellingAction();
        editSchemeAction = new EditSchemeAction();
        aboutAction = new AboutAction(this);
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("org/fife/plaf/Office2003/new.gif");
        ImageIcon newIcon = new ImageIcon(url);
        url = classLoader.getResource("org/fife/plaf/Office2003/open.gif");
        ImageIcon openIcon = new ImageIcon(url);
        url = classLoader.getResource("org/fife/plaf/Office2003/save.gif");
        ImageIcon saveIcon = new ImageIcon(url);
        url = classLoader.getResource("org/fife/plaf/Office2003/saveas.gif");
        ImageIcon saveAsIcon = new ImageIcon(url);
        url = classLoader.getResource("org/fife/plaf/Office2003/close.gif");
        ImageIcon closeIcon = new ImageIcon(url);
        url = classLoader.getResource("org/fife/plaf/Office2003/delete.gif");
        ImageIcon exitIcon = new ImageIcon(url);
        ImageIcon connectivityIcon = ImageIconLoader.getInstance().getImageIcon("/img/connectivity_table.png");
        ImageIcon modellingIcon = ImageIconLoader.getInstance().getImageIcon("/img/modelling.png");
        ImageIcon editSchemeIcon = ImageIconLoader.getInstance().getImageIcon("/img/edit_scheme.png");
        url = classLoader.getResource("org/fife/plaf/Office2003/about.gif");
        ImageIcon aboutIcon = new ImageIcon(url);
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu schemeMenu = new JMenu("Схема");
        JMenu helpMenu = new JMenu("Довідка");
        JMenuItem tempItem = new JMenuItem(newAction);
        tempItem.setText("Новий...");
        tempItem.setIcon(newIcon);
        tempItem.setMnemonic('N');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_MASK));
        fileMenu.add(tempItem);
        tempItem = new JMenuItem(openAction);
        tempItem.setText("Відкрити...");
        tempItem.setIcon(openIcon);
        tempItem.setMnemonic('O');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_MASK));
        fileMenu.add(tempItem);
        tempItem = new JMenuItem(saveAction);
        tempItem.setText("Зберегти");
        tempItem.setIcon(saveIcon);
        tempItem.setMnemonic('S');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_MASK));
        fileMenu.add(tempItem);
        tempItem = new JMenuItem(saveAsAction);
        tempItem.setText("Зберегти як...");
        tempItem.setIcon(saveAsIcon);
        tempItem.setMnemonic('S');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('S', (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK)));
        fileMenu.add(tempItem);
        tempItem = new JMenuItem(closeAction);
        tempItem.setText("Закрити");
        tempItem.setIcon(closeIcon);
        tempItem.setMnemonic('W');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('W', KeyEvent.CTRL_MASK));
        fileMenu.add(tempItem);
        fileMenu.addSeparator();
        tempItem = new JMenuItem(exitAction);
        tempItem.setText("Вихід");
        tempItem.setIcon(exitIcon);
        tempItem.setMnemonic('Q');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('Q', KeyEvent.CTRL_MASK));
        fileMenu.add(tempItem);
        tempItem = new JMenuItem(connectivityAction);
        tempItem.setText("Матриця зв’язності");
        tempItem.setIcon(connectivityIcon);
        tempItem.setMnemonic('M');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('M', (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK)));
        schemeMenu.add(tempItem);
        tempItem = new JMenuItem(modellingAction);
        tempItem.setText("Моделювати");
        tempItem.setIcon(modellingIcon);
        tempItem.setMnemonic('M');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('M', KeyEvent.CTRL_MASK));
        schemeMenu.add(tempItem);
        tempItem = new JMenuItem(editSchemeAction);
        tempItem.setText("Редагувати схему");
        tempItem.setIcon(editSchemeIcon);
        tempItem.setMnemonic('E');
        tempItem.setAccelerator(KeyStroke.getKeyStroke('E', KeyEvent.CTRL_MASK));
        schemeMenu.add(tempItem);
//        tempItem = new JMenuItem(aboutAction);
//        tempItem.setText("О программе...");
//        tempItem.setIcon(aboutIcon);
//        helpMenu.add(tempItem);
        menuBar.add(fileMenu);
        menuBar.add(schemeMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        JButton tempButton = toolBar.add(newAction);
        tempButton.setIcon(newIcon);
        tempButton.setToolTipText("Новий файл");
        tempButton = toolBar.add(openAction);
        tempButton.setIcon(openIcon);
        tempButton.setToolTipText("Відкрити файл...");
        tempButton = toolBar.add(saveAction);
        tempButton.setIcon(saveIcon);
        tempButton.setToolTipText("Зберегти файл");
        tempButton = toolBar.add(closeAction);
        tempButton.setIcon(closeIcon);
        tempButton.setToolTipText("Закрити файл");
        toolBar.addSeparator();
        tempButton = toolBar.add(connectivityAction);
        tempButton.setIcon(connectivityIcon);
        tempButton.setToolTipText("Матриця зв’язності");
        tempButton = toolBar.add(modellingAction);
        tempButton.setIcon(modellingIcon);
        tempButton.setToolTipText("Моделювання");
        tempButton = toolBar.add(editSchemeAction);
        tempButton.setIcon(editSchemeIcon);
        tempButton.setToolTipText("Редагувати схему");
        add(toolBar, BorderLayout.NORTH);
        desktopPane = new JDesktopPane();
        add(desktopPane);
        fileChooser = new JFileChooser(".");
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.addChoosableFileFilter(new XMLFileFilter());
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    private class MainFrameListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            Program.writeConfigurationFile(e.getWindow().getBounds());
        }

    }

    private class NewAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            SchemeDialog schemeDialog = new SchemeDialog(getX() + ((getWidth() - SchemeDialog.WIDTH) / 2),
                    getY() + ((getHeight() - SchemeDialog.HEIGHT) / 2), "Нова схема");
            schemeDialog.setVisible(true);
            if (schemeDialog.isOkClicked()) {
                InternalFrame internalFrame = new InternalFrame(new Rectangle(0, 0, InternalFrame.MIN_WIDTH, InternalFrame.MIN_HEIGHT),
                        schemeDialog.getSchemeSize(), schemeDialog.getBackgroundColor(), schemeDialog.getStandartColor(),
                        schemeDialog.getSelectedColor(), schemeDialog.getTextColor(), schemeDialog.getTextFont());
                desktopPane.add(internalFrame);
                desktopPane.setSelectedFrame(internalFrame);
                desktopPane.getDesktopManager().activateFrame(internalFrame);
                desktopPane.revalidate();
                desktopPane.repaint();
            }
        }

    }

    private class OpenAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            int answer = fileChooser.showOpenDialog(rootPane);
            if (answer == JFileChooser.APPROVE_OPTION) {
                try {
                    Scanner scanner = new Scanner(new FileReader(fileChooser.getSelectedFile()));
                    StringBuilder builder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        builder.append(scanner.nextLine());
                    }
                    scanner.close();
                    InternalFrame internalFrame = new InternalFrame(new Rectangle(0, 0, InternalFrame.MIN_WIDTH, InternalFrame.MIN_HEIGHT),
                            fileChooser.getSelectedFile(), SchemeModel.fromXML(builder.toString()));
                    desktopPane.add(internalFrame);
                    desktopPane.setSelectedFrame(internalFrame);
                    desktopPane.getDesktopManager().activateFrame(internalFrame);
                    desktopPane.revalidate();
                    desktopPane.repaint();
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(rootPane, "Обраний файл не знайдено.", "Помилка!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    private class SaveAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            InternalFrame internalFrame = (InternalFrame) desktopPane.getSelectedFrame();
            if (internalFrame.getFile() == null) {
                saveAsAction.actionPerformed(e);
            } else {
                if (internalFrame.isModified()) {
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter(internalFrame.getFile()));
                        writer.write(internalFrame.getXMLScheme());
                        writer.close();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(rootPane, "Помилка при збереженні схеми.", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

    }

    private class SaveAsAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            int answer = fileChooser.showSaveDialog(rootPane);
            if (answer == JFileChooser.APPROVE_OPTION) {
                if (!fileChooser.getSelectedFile().getName().toLowerCase().endsWith(XMLFileFilter.XML_EXTENSION)) {
                    fileChooser.setSelectedFile(new File(fileChooser.getSelectedFile().getAbsolutePath() +
                            XMLFileFilter.XML_EXTENSION));
                }
                ((InternalFrame) desktopPane.getSelectedFrame()).setFile(fileChooser.getSelectedFile());
                saveAction.actionPerformed(e);
            }
        }

    }

    private class CloseAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            InternalFrame internalFrame = (InternalFrame) desktopPane.getSelectedFrame();
            if (internalFrame.isModified()) {
                int answer = JOptionPane.showConfirmDialog(rootPane, "Схема \"" + internalFrame.getTitle() +
                        "\" містить незбережені зміни. Бажаєте зберегти перед виходом?",
                        "Попередження", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    saveAction.actionPerformed(e);
                }
            }
            desktopPane.remove(internalFrame);
            desktopPane.revalidate();
            desktopPane.repaint();
        }

    }

    private class ExitAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            for (JInternalFrame internalFrame : desktopPane.getAllFrames()) {
                desktopPane.setSelectedFrame(internalFrame);
                desktopPane.getDesktopManager().activateFrame(internalFrame);
                closeAction.actionPerformed(e);
            }
            Program.writeConfigurationFile(getBounds());
            System.exit(0);
        }

    }

    private class ConnectivityAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            JInternalFrame jInternalFrame = desktopPane.getSelectedFrame();
            if (jInternalFrame != null) {
                ((InternalFrame) jInternalFrame).addConnectivityTab();
            }
        }

    }

    private class ModellingAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            JInternalFrame jInternalFrame = desktopPane.getSelectedFrame();
            if (jInternalFrame != null) {
                ((InternalFrame) jInternalFrame).addModellingTab();
            }
        }

    }

    private class EditSchemeAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            JInternalFrame jInternalFrame = desktopPane.getSelectedFrame();
            if (jInternalFrame != null) {
                ((InternalFrame) jInternalFrame).editScheme();
            }
        }

    }

    private class AboutAction extends AbstractAction {

        private MainFrame frame;

        public AboutAction(MainFrame frame) {
            this.frame = frame;
        }

        public void actionPerformed(ActionEvent e) {
            final JDialog aboutDialog = new JDialog(frame);
            aboutDialog.setTitle("О программе");
            aboutDialog.setModal(true);
            aboutDialog.setLayout(new GridLayout(5, 1));
            aboutDialog.add(new JLabel("Курсовой проект по курсу АПКС"));
            aboutDialog.add(new JLabel("Версия: 1.0"));
            aboutDialog.add(new JLabel("Автор: Захожий Игорь"));
            aboutDialog.add(new JLabel("Copyright (c) 2011"));
            JButton closeAboutButton = new JButton(new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    aboutDialog.setVisible(false);
                }
            });
            closeAboutButton.setText("Закрыть");
            aboutDialog.add(closeAboutButton);
            aboutDialog.pack();
            aboutDialog.setLocation((frame.getX() + (frame.getWidth() - aboutDialog.getWidth()) / 2),
                    (frame.getY() + (frame.getHeight() - aboutDialog.getHeight()) / 2));
            aboutDialog.setResizable(false);
            aboutDialog.setVisible(true);
        }

    }

    private class XMLFileFilter extends FileFilter {

        public static final String XML_EXTENSION = ".xml";

        private static final String XML_DESCRIPTION = "XML файл";

        public XMLFileFilter() {
            super();
        }

        @Override
        public boolean accept(File f) {
            if ((f.getName().toLowerCase().endsWith(XML_EXTENSION)) || (f.isDirectory())) {
                return true;
            }
            return false;
        }

        @Override
        public String getDescription() {
            return XML_DESCRIPTION;
        }

    }

}
