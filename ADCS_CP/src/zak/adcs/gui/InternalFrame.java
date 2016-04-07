package zak.adcs.gui;

import zak.adcs.ImageIconLoader;
import zak.adcs.logicscheme.editor.SchemeEditor;
import zak.adcs.logicscheme.editor.SchemeModel;
import zak.adcs.logicscheme.modelling.ModellingPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Zakhozhyy Ihor
 *         Date: 01.03.11
 *         Time: 5:08
 */
public class InternalFrame extends JInternalFrame {

    public static final int MIN_WIDTH = 880;
    public static final int MIN_HEIGHT = 660;

    private static final String NEW_SCHEME_NAME = "Новая схема ";

    private static int NEW_SCHEME_COUNTER = 1;

    private JTabbedPane tabbedPane;
    private SchemeEditor schemeEditor;

    private int connectivityTabIndex;
    private int modellingTabIndex;

    private File file;

    public InternalFrame(Rectangle bounds, Dimension schemeSize, Color schemeBackColor, Color schemeStandColor,
                         Color schemeSelColor, Color textColor, Font font) {
        super(NEW_SCHEME_NAME + String.valueOf(NEW_SCHEME_COUNTER++), true, false, true, true);
        setBounds(bounds);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setFrameIcon(ImageIconLoader.getInstance().getImageIcon("/img/document.png"));
        file = null;
        connectivityTabIndex = -1;
        modellingTabIndex = -1;
        tabbedPane = new JTabbedPane();
        schemeEditor = new SchemeEditor(schemeSize, schemeBackColor, schemeStandColor, schemeSelColor, textColor, font,
                rootPane);
        tabbedPane.addTab("Редактор схемы", schemeEditor);
        add(tabbedPane);
        reshape(getX(), getY(), getWidth(), getHeight());
        setVisible(true);
    }

    public InternalFrame(Rectangle bounds, File file, SchemeModel schemeModel) {
        super(file.getAbsolutePath(), true, false, true, true);
        setBounds(bounds);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setFrameIcon(ImageIconLoader.getInstance().getImageIcon("/img/document.png"));
        this.file = file;
        connectivityTabIndex = -1;
        modellingTabIndex = -1;
        tabbedPane = new JTabbedPane();
        schemeEditor = new SchemeEditor(schemeModel, rootPane);
        tabbedPane.addTab("Редактор схемы", schemeEditor);
        add(tabbedPane);
        reshape(getX(), getY(), getWidth(), getHeight());
        setVisible(true);
    }

    public void addConnectivityTab() {
        if (connectivityTabIndex == -1) {
            JTable table = new JTable(schemeEditor.getSchemeTableModel());
            table.setTableHeader(null);
            table.setDragEnabled(false);
            tabbedPane.addTab("Матрица связности", new JScrollPane(table));
            connectivityTabIndex = tabbedPane.getTabCount() - 1;
        }
        schemeEditor.setPanelEnableToModify(false);
        tabbedPane.setSelectedIndex(connectivityTabIndex);
    }

    public void addModellingTab() {
        if (modellingTabIndex == -1) {
            tabbedPane.addTab("Моделирование", new ModellingPanel(schemeEditor.getSchemeTableModel(),
                    schemeEditor.getBackgroundColor(), schemeEditor.getStandartColor(), schemeEditor.getTextColor(),
                    schemeEditor.getTextFont()));
            modellingTabIndex = tabbedPane.getTabCount() - 1;
        }
        schemeEditor.setPanelEnableToModify(false);
        tabbedPane.setSelectedIndex(modellingTabIndex);
    }

    public void editScheme() {
        for (int i = tabbedPane.getTabCount() - 1; i > 0; i--) {
            tabbedPane.removeTabAt(i);
        }
        connectivityTabIndex = -1;
        modellingTabIndex = -1;
        schemeEditor.setPanelEnableToModify(true);
    }

    public boolean isModified() {
        return schemeEditor.isModified();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        setTitle(file.getAbsolutePath());
    }

    public String getXMLScheme() {
        return schemeEditor.getXMLScheme();
    }

}
