package zak.adcs.logicscheme.editor;

import zak.adcs.logicscheme.elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class SchemePanel extends JPanel implements Scrollable {

    private JRootPane rootFrame;

    private SchemeModel model;

    public SchemePanel(SchemeModel model, JRootPane rootFrame) {
        super();
        this.model = model;
        this.rootFrame = rootFrame;
        setSize(this.model.getSize());
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public SchemeModel getModel() {
        return model;
    }

    public void setModel(SchemeModel model) {
        this.model = model;
    }

    public void setEnableToModify(boolean enable) {
        if (enable) {
            if (getMouseListeners().length == 0 && getMouseMotionListeners().length == 0) {
                MouseHandler mouseHandler = new MouseHandler();
                addMouseListener(mouseHandler);
                addMouseMotionListener(mouseHandler);
            }
        } else {
            if (!(getMouseListeners().length == 0 && getMouseMotionListeners().length == 0)) {
                removeMouseListener(getMouseListeners()[0]);
                removeMouseMotionListener(getMouseMotionListeners()[0]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        setBackground(model.getBackgroundColor());
        if (model.getElements() != null) {
            for (VisualElement ve : model.getElements()) {
                ve.draw(g2);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return model.getSize();
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    private class MouseHandler extends MouseAdapter {

        private int oldX;
        private int oldY;
        HashMap<ConnectionElement, Point> oldPoints;

        private Point lastCursorPoint;

        private ClosedElement movingElement;

        private int movingPointIndex;
        private ConnectionElement movingPointElement;
        private ArrayList<ConnectionElement> movingPointConnections;

        private int connectionElementPointCanConnectIndex;

        private ConnectionElement addingConnectionElement;
        private ConnectionElement connectionElementToConnect;
        private ConnectionElement connectionElementCanConnect;
        private ClosedElement elementToConnect;
        private ClosedElement elementCanConnect;

        private boolean enterFlag;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                SchemeModel.CurrentAction currentAction = model.getCurrentAction();
                if (currentAction == SchemeModel.CurrentAction.MOVING) {
                    if (e.getClickCount() > 1) {
                        VisualElement inElement = null;
                        for (VisualElement element : model.getElements()) {
                            if (element.contains(e.getPoint())) {
                                inElement = element;
                                break;
                            }
                        }
                        if (inElement != null) {
                            inElement.setSelected(true);
                            repaint();
                            if (inElement.getClass().getName() == "zak.adcs.logicscheme.elements.InElement") {
                                IOElementDialog dialog = new IOElementDialog(e.getLocationOnScreen(), "Вхід");
                                dialog.setName(((InElement) inElement).getName());
                                dialog.setVisible(true);
                                if (dialog.isOkClicked() && dialog.getName() != (((InElement) inElement).getName())) {
                                    if (model.containsName(dialog.getName(), inElement)) {
                                        JOptionPane.showMessageDialog(rootFrame,
                                                "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        ((InElement) inElement).setName(dialog.getName());
                                    }
                                }
                            } else if (inElement.getClass().getName() == "zak.adcs.logicscheme.elements.OutElement") {
                                IOElementDialog dialog = new IOElementDialog(e.getLocationOnScreen(), "Вихід");
                                dialog.setName(((OutElement) inElement).getName());
                                dialog.setVisible(true);
                                if (dialog.isOkClicked() && dialog.getName() != (((OutElement) inElement).getName())) {
                                    if (model.containsName(dialog.getName(), inElement)) {
                                        JOptionPane.showMessageDialog(rootFrame,
                                                "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        ((OutElement) inElement).setName(dialog.getName());
                                    }
                                }
                            } else if (inElement.getClass().getName() == "zak.adcs.logicscheme.elements.AndElement") {
                                LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "І");
                                AndElement andElement = (AndElement) inElement;
                                dialog.setName(andElement.getName());
                                dialog.setInertiaDelay(andElement.getInertiaDelay());
                                dialog.setDynamicDelay(andElement.getDynamicDelay());
                                dialog.setInCount(andElement.getInCount());
                                dialog.setVisible(true);
                                if (dialog.isOkClicked()) {
                                    if (model.containsName(dialog.getName(), inElement)) {
                                        JOptionPane.showMessageDialog(rootFrame,
                                                "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        andElement.setName(dialog.getName());
                                        andElement.setInertiaDelay(dialog.getInertiaDelay());
                                        andElement.setDynamicDelay(dialog.getDynamicDelay());
                                        andElement.setInCount(dialog.getInCount());
                                    }
                                }
                            } else if (inElement.getClass().getName() == "zak.adcs.logicscheme.elements.OrElement") {
                                LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "АБО");
                                OrElement orElement = (OrElement) inElement;
                                dialog.setName(orElement.getName());
                                dialog.setInertiaDelay(orElement.getInertiaDelay());
                                dialog.setDynamicDelay(orElement.getDynamicDelay());
                                dialog.setInCount(orElement.getInCount());
                                dialog.setVisible(true);
                                if (dialog.isOkClicked()) {
                                    if (model.containsName(dialog.getName(), inElement)) {
                                        JOptionPane.showMessageDialog(rootFrame,
                                                "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        orElement.setName(dialog.getName());
                                        orElement.setInertiaDelay(dialog.getInertiaDelay());
                                        orElement.setDynamicDelay(dialog.getDynamicDelay());
                                        orElement.setInCount(dialog.getInCount());
                                    }
                                }
                            } else if (inElement.getClass().getName() == "zak.adcs.logicscheme.elements.XorElement") {
                                LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "Викл. АБО");
                                XorElement xorElement = (XorElement) inElement;
                                dialog.setName(xorElement.getName());
                                dialog.setInertiaDelay(xorElement.getInertiaDelay());
                                dialog.setDynamicDelay(xorElement.getDynamicDelay());
                                dialog.setInCount(xorElement.getInCount());
                                dialog.setVisible(true);
                                if (dialog.isOkClicked()) {
                                    if (model.containsName(dialog.getName(), inElement)) {
                                        JOptionPane.showMessageDialog(rootFrame,
                                                "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        xorElement.setName(dialog.getName());
                                        xorElement.setInertiaDelay(dialog.getInertiaDelay());
                                        xorElement.setDynamicDelay(dialog.getDynamicDelay());
                                        xorElement.setInCount(dialog.getInCount());
                                    }
                                }
                            } else if (inElement.getClass().getName() == "zak.adcs.logicscheme.elements.NandElement") {
                                LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "І-НЕ");
                                NandElement nandElement = (NandElement) inElement;
                                dialog.setName(nandElement.getName());
                                dialog.setInertiaDelay(nandElement.getInertiaDelay());
                                dialog.setDynamicDelay(nandElement.getDynamicDelay());
                                dialog.setInCount(nandElement.getInCount());
                                dialog.setVisible(true);
                                if (dialog.isOkClicked()) {
                                    if (model.containsName(dialog.getName(), inElement)) {
                                        JOptionPane.showMessageDialog(rootFrame,
                                                "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        nandElement.setName(dialog.getName());
                                        nandElement.setInertiaDelay(dialog.getInertiaDelay());
                                        nandElement.setDynamicDelay(dialog.getDynamicDelay());
                                        nandElement.setInCount(dialog.getInCount());
                                    }
                                }
                            } else if (inElement.getClass().getName() == "zak.adcs.logicscheme.elements.NorElement") {
                                LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "АБо-НЕ");
                                NorElement norElement = (NorElement) inElement;
                                dialog.setName(norElement.getName());
                                dialog.setInertiaDelay(norElement.getInertiaDelay());
                                dialog.setDynamicDelay(norElement.getDynamicDelay());
                                dialog.setInCount(norElement.getInCount());
                                dialog.setVisible(true);
                                if (dialog.isOkClicked()) {
                                    if (model.containsName(dialog.getName(), inElement)) {
                                        JOptionPane.showMessageDialog(rootFrame,
                                                "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        norElement.setName(dialog.getName());
                                        norElement.setInertiaDelay(dialog.getInertiaDelay());
                                        norElement.setDynamicDelay(dialog.getDynamicDelay());
                                        norElement.setInCount(dialog.getInCount());
                                    }
                                }
                            } else if (inElement.getClass().getName() == "zak.adcs.logicscheme.elements.ConnectionElement") {
                                ConnectionElement connectionElement = (ConnectionElement) inElement;
                                connectionElement.addPoint(e.getPoint());
                            }
                            inElement.setSelected(false);
                            repaint();
                        }
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_IN) {
                    Rectangle bounds = new Rectangle(e.getX(), e.getY(),
                            SchemeModel.DEFAULT_IO_ELEMENT_WIDTH, SchemeModel.DEFAULT_IO_ELEMENT_HEIGHT);
                    boolean canAdd = true;
                    if (model.getElements() != null) {
                        for (VisualElement element : model.getElements()) {
                            if (element.isOverlaped(bounds)) {
                                canAdd = false;
                                break;
                            }
                        }
                    }
                    if (canAdd) {
                        IOElementDialog dialog = new IOElementDialog(e.getLocationOnScreen(), "Добавление входа");
                        dialog.setVisible(true);
                        if (dialog.isOkClicked()) {
                            if (model.containsName(dialog.getName(), null)) {
                                JOptionPane.showMessageDialog(rootFrame,
                                        "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                InElement inElement = new InElement((int) bounds.getX(), (int) bounds.getY(),
                                        (int) bounds.getWidth(), (int) bounds.getHeight(), model.getStandartColor(),
                                        model.getSelectedColor(), model.getTextColor(), model.getFont(), dialog.getName());
                                model.getElements().add(inElement);
                                repaint();
                                model.setModified(true);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootFrame, "Неприпустиме роозташування елемента.", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_OUT) {
                    Rectangle bounds = new Rectangle(e.getX(), e.getY(),
                            SchemeModel.DEFAULT_IO_ELEMENT_WIDTH, SchemeModel.DEFAULT_IO_ELEMENT_HEIGHT);
                    boolean canAdd = true;
                    if (model.getElements() != null) {
                        for (VisualElement element : model.getElements()) {
                            if (element.isOverlaped(bounds)) {
                                canAdd = false;
                                break;
                            }
                        }
                    }
                    if (canAdd) {
                        IOElementDialog dialog = new IOElementDialog(e.getLocationOnScreen(), "Додання виходу");
                        dialog.setVisible(true);
                        if (dialog.isOkClicked()) {
                            if (model.containsName(dialog.getName(), null)) {
                                JOptionPane.showMessageDialog(rootFrame,
                                        "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                OutElement outElement = new OutElement((int) bounds.getX(), (int) bounds.getY(),
                                        (int) bounds.getWidth(), (int) bounds.getHeight(), model.getStandartColor(),
                                        model.getSelectedColor(), model.getTextColor(), model.getFont(), dialog.getName());
                                model.getElements().add(outElement);
                                repaint();
                                model.setModified(true);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootFrame, "Неприпустиме розташування елемента.", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_AND) {
                    Rectangle bounds = new Rectangle(e.getX(), e.getY(), SchemeModel.DEFAULT_LOGIC_ELEMENT_WIDTH,
                            SchemeModel.DEFAULT_LOGIC_ELEMENT_HEIGHT);
                    boolean canAdd = true;
                    for (VisualElement element : model.getElements()) {
                        if (element.isOverlaped(bounds)) {
                            canAdd = false;
                            break;
                        }
                    }
                    if (canAdd) {
                        LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "Додавання елемента І");
                        dialog.setVisible(true);
                        if (dialog.isOkClicked()) {
                            if (model.containsName(dialog.getName(), null)) {
                                JOptionPane.showMessageDialog(rootFrame,
                                        "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                if (dialog.getInCount() > 2) {
                                    bounds.setSize(SchemeModel.DEFAULT_IO_ELEMENT_WIDTH,
                                            SchemeModel.DEFAULT_ELEMENT_IN_DISTANCE * (dialog.getInCount() + 1));
                                    for (VisualElement element : model.getElements()) {
                                        if (element.isOverlaped(bounds)) {
                                            canAdd = false;
                                            break;
                                        }
                                    }
                                }
                                if (canAdd) {
                                    AndElement andElement = new AndElement((int) bounds.getX(), (int) bounds.getY(),
                                            (int) bounds.getWidth(), (int) bounds.getHeight(), model.getStandartColor(),
                                            model.getSelectedColor(), model.getTextColor(), model.getFont(),
                                            dialog.getName(), dialog.getInertiaDelay(), dialog.getDynamicDelay(),
                                            dialog.getInCount());
                                    model.getElements().add(andElement);
                                    repaint();
                                    model.setModified(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootFrame,
                                            "Неприпустиме розташування елемента.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootFrame,  "Неприпустиме розташування елемента.", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_OR) {
                    Rectangle bounds = new Rectangle(e.getX(), e.getY(), SchemeModel.DEFAULT_LOGIC_ELEMENT_WIDTH,
                            SchemeModel.DEFAULT_LOGIC_ELEMENT_HEIGHT);
                    boolean canAdd = true;
                    for (VisualElement element : model.getElements()) {
                        if (element.isOverlaped(bounds)) {
                            canAdd = false;
                            break;
                        }
                    }
                    if (canAdd) {
                        LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "Додавання елемента АБО");
                        dialog.setVisible(true);
                        if (dialog.isOkClicked()) {
                            if (model.containsName(dialog.getName(), null)) {
                                JOptionPane.showMessageDialog(rootFrame,
                                        "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                if (dialog.getInCount() > 2) {
                                    bounds.setSize(SchemeModel.DEFAULT_IO_ELEMENT_WIDTH,
                                            SchemeModel.DEFAULT_ELEMENT_IN_DISTANCE * (dialog.getInCount() + 1));
                                    for (VisualElement element : model.getElements()) {
                                        if (element.isOverlaped(bounds)) {
                                            canAdd = false;
                                            break;
                                        }
                                    }
                                }
                                if (canAdd) {
                                    OrElement orElement = new OrElement((int) bounds.getX(), (int) bounds.getY(),
                                            (int) bounds.getWidth(), (int) bounds.getHeight(), model.getStandartColor(),
                                            model.getSelectedColor(), model.getTextColor(), model.getFont(),
                                            dialog.getName(), dialog.getInertiaDelay(), dialog.getDynamicDelay(),
                                            dialog.getInCount());
                                    model.getElements().add(orElement);
                                    repaint();
                                    model.setModified(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootFrame,
                                            "Неприпустиме розташування елемента.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootFrame,  "Неприпустиме розташування елемента.", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_NAND) {
                    Rectangle bounds = new Rectangle(e.getX(), e.getY(), SchemeModel.DEFAULT_LOGIC_ELEMENT_WIDTH,
                            SchemeModel.DEFAULT_LOGIC_ELEMENT_HEIGHT);
                    boolean canAdd = true;
                    for (VisualElement element : model.getElements()) {
                        if (element.isOverlaped(bounds)) {
                            canAdd = false;
                            break;
                        }
                    }
                    if (canAdd) {
                        LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "Додавання елемента І-НЕ");
                        dialog.setVisible(true);
                        if (dialog.isOkClicked()) {
                            if (model.containsName(dialog.getName(), null)) {
                                JOptionPane.showMessageDialog(rootFrame,
                                        "Елемент з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                if (dialog.getInCount() > 2) {
                                    bounds.setSize(SchemeModel.DEFAULT_IO_ELEMENT_WIDTH,
                                            SchemeModel.DEFAULT_ELEMENT_IN_DISTANCE * (dialog.getInCount() + 1));
                                    for (VisualElement element : model.getElements()) {
                                        if (element.isOverlaped(bounds)) {
                                            canAdd = false;
                                            break;
                                        }
                                    }
                                }
                                if (canAdd) {
                                    NandElement nandElement = new NandElement((int) bounds.getX(), (int) bounds.getY(),
                                            (int) bounds.getWidth(), (int) bounds.getHeight(), model.getStandartColor(),
                                            model.getSelectedColor(), model.getTextColor(), model.getFont(),
                                            dialog.getName(), dialog.getInertiaDelay(), dialog.getDynamicDelay(),
                                            dialog.getInCount());
                                    model.getElements().add(nandElement);
                                    repaint();
                                    model.setModified(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootFrame,
                                            "Неприпустиме розташування елемента.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootFrame,  "Неприпустиме розташування елемента.", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_NOR) {
                    Rectangle bounds = new Rectangle(e.getX(), e.getY(), SchemeModel.DEFAULT_LOGIC_ELEMENT_WIDTH,
                            SchemeModel.DEFAULT_LOGIC_ELEMENT_HEIGHT);
                    boolean canAdd = true;
                    for (VisualElement element : model.getElements()) {
                        if (element.isOverlaped(bounds)) {
                            canAdd = false;
                            break;
                        }
                    }
                    if (canAdd) {
                        LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "Додавання елементв АБО-НЕ");
                        dialog.setVisible(true);
                        if (dialog.isOkClicked()) {
                            if (model.containsName(dialog.getName(), null)) {
                                JOptionPane.showMessageDialog(rootFrame,
                                        "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                if (dialog.getInCount() > 2) {
                                    bounds.setSize(SchemeModel.DEFAULT_IO_ELEMENT_WIDTH,
                                            SchemeModel.DEFAULT_ELEMENT_IN_DISTANCE * (dialog.getInCount() + 1));
                                    for (VisualElement element : model.getElements()) {
                                        if (element.isOverlaped(bounds)) {
                                            canAdd = false;
                                            break;
                                        }
                                    }
                                }
                                if (canAdd) {
                                    NorElement norElement = new NorElement((int) bounds.getX(), (int) bounds.getY(),
                                            (int) bounds.getWidth(), (int) bounds.getHeight(), model.getStandartColor(),
                                            model.getSelectedColor(), model.getTextColor(), model.getFont(),
                                            dialog.getName(), dialog.getInertiaDelay(), dialog.getDynamicDelay(),
                                            dialog.getInCount());
                                    model.getElements().add(norElement);
                                    repaint();
                                    model.setModified(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootFrame,
                                            "Неприпустиме розташування елемента.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootFrame,  "Неприпустиме розташування елемента.", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_XOR) {
                    Rectangle bounds = new Rectangle(e.getX(), e.getY(), SchemeModel.DEFAULT_LOGIC_ELEMENT_WIDTH,
                            SchemeModel.DEFAULT_LOGIC_ELEMENT_HEIGHT);
                    boolean canAdd = true;
                    for (VisualElement element : model.getElements()) {
                        if (element.isOverlaped(bounds)) {
                            canAdd = false;
                            break;
                        }
                    }
                    if (canAdd) {
                        LIDElementDialog dialog = new LIDElementDialog(e.getLocationOnScreen(), "Додавання елемента Викл. АБО");
                        dialog.setVisible(true);
                        if (dialog.isOkClicked()) {
                            if (model.containsName(dialog.getName(), null)) {
                                JOptionPane.showMessageDialog(rootFrame,
                                        "Елемант з таким ім’ям вже існує.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                if (dialog.getInCount() > 2) {
                                    bounds.setSize(SchemeModel.DEFAULT_IO_ELEMENT_WIDTH,
                                            SchemeModel.DEFAULT_ELEMENT_IN_DISTANCE * (dialog.getInCount() + 1));
                                    for (VisualElement element : model.getElements()) {
                                        if (element.isOverlaped(bounds)) {
                                            canAdd = false;
                                            break;
                                        }
                                    }
                                }
                                if (canAdd) {
                                    XorElement xorElement = new XorElement((int) bounds.getX(), (int) bounds.getY(),
                                            (int) bounds.getWidth(), (int) bounds.getHeight(), model.getStandartColor(),
                                            model.getSelectedColor(), model.getTextColor(), model.getFont(),
                                            dialog.getName(), dialog.getInertiaDelay(), dialog.getDynamicDelay(),
                                            dialog.getInCount());
                                    model.getElements().add(xorElement);
                                    repaint();
                                    model.setModified(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootFrame,
                                            "Неприпустиме розташування елемента.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootFrame,  "Неприпустиме розташування елемента.", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentAction == SchemeModel.CurrentAction.DELETING) {
                    VisualElement inElement = null;
                    for (VisualElement element : model.getElements()) {
                        if (element.contains(e.getPoint())) {
                            inElement = element;
                            break;
                        }
                    }
                    if (inElement != null) {
                        boolean connectionPointFlag = false;
                        if (inElement instanceof ConnectionElement) {
                            ConnectionElement connectionElement = (ConnectionElement) inElement;
                            int pointIndex = connectionElement.getInPointIndex(e.getPoint());
                            if (pointIndex > -1) {
                                connectionPointFlag = true;
                                connectionElement.setSelectedPoint(pointIndex, true);
                                repaint();
                                int answer = JOptionPane.showConfirmDialog(rootFrame,
                                        "Ви впевнені, що хочете видалити обраний елемент?", "Підтвердження",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (answer == JOptionPane.YES_OPTION) {
                                    model.removePointConnections(connectionElement.getNumber(), pointIndex);
                                    connectionElement.removePoint(pointIndex);
                                    model.setModified(true);
                                } else {
                                    connectionElement.setSelectedPoint(pointIndex, false);
                                }
                            } else {
                                connectionPointFlag = true;
                                connectionElement.setSelected(true);
                                repaint();
                                int answer = JOptionPane.showConfirmDialog(rootFrame,
                                        "Ви впевнені, що хочете видалити обраний елемент?", "Підтвердження",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (answer == JOptionPane.YES_OPTION) {
                                    model.removeConnection(connectionElement.getNumber());
                                    model.setModified(true);
                                } else {
                                    connectionElement.setSelected(false);
                                }
                            }
                        }
                        if (!connectionPointFlag) {
                            inElement.setSelected(true);
                            repaint();
                            int answer = JOptionPane.showConfirmDialog(rootFrame,
                                    "Ви впевнені, що хочете видалити обраний елемент?", "Підтвердження",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (answer == JOptionPane.YES_OPTION) {
                                model.getElements().remove(inElement);
                                if (inElement instanceof ClosedElement) {
                                    ClosedElement closedElement = (ClosedElement) inElement;
                                    for (int i = model.getElements().size() - 1; i >= 0; i--) {
                                        if (model.getElements().get(i) instanceof ConnectionElement) {
                                            if (((ConnectionElement) model.getElements().get(i)).getElementTo().compareTo(closedElement.getName()) == 0 ||
                                                    ((ConnectionElement) model.getElements().get(i)).getElementFrom().compareTo(closedElement.getName()) == 0) {
                                                ConnectionElement connectionElement = (ConnectionElement) model.getElements().get(i);
                                                model.removeConnection(connectionElement.getNumber());
                                            }
                                        }
                                    }
                                }
                                model.setModified(true);
                            } else {
                                inElement.setSelected(false);
                            }
                        }
                        repaint();
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                SchemeModel.CurrentAction currentAction = model.getCurrentAction();
                if (currentAction == SchemeModel.CurrentAction.MOVING) {
                    VisualElement inElement = null;
                    for (VisualElement element : model.getElements()) {
                        if (element.getClass().getName() != "zak.adcs.logicscheme.elements.ConnectionElement" &&
                                element.contains(e.getPoint())) {
                            inElement = element;
                            break;
                        } else if (element instanceof ConnectionElement && element.contains(e.getPoint())) {
                            ConnectionElement connectionElement = (ConnectionElement) element;
                            if (connectionElement.isInExit(e.getPoint())) {
                                movingPointIndex = connectionElement.getInPointIndex(e.getPoint());
                                movingPointElement = connectionElement;
                                movingPointElement.setSelectedPoint(movingPointIndex, true);
                                repaint();
                                oldX = connectionElement.getPoint(movingPointIndex).x;
                                oldY = connectionElement.getPoint(movingPointIndex).y;
                                lastCursorPoint = e.getPoint();
                                ArrayList<Integer> movingPointConnectionsNumbers =
                                        connectionElement.getConnectionNumbers(movingPointIndex);
                                if (movingPointConnectionsNumbers != null && movingPointConnectionsNumbers.size() > 0) {
                                    movingPointConnections = new ArrayList<ConnectionElement>();
                                    for (int number : movingPointConnectionsNumbers) {
                                        for (VisualElement iElement : model.getElements()) {
                                            if (iElement instanceof ConnectionElement) {
                                                if (((ConnectionElement) iElement).getNumber() == number) {
                                                    movingPointConnections.add((ConnectionElement) iElement);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (inElement != null) {
                        movingElement = (ClosedElement) inElement;
                        movingElement.setSelected(true);
                        repaint();
                        oldX = movingElement.getX();
                        oldY = movingElement.getY();
                        oldPoints = new HashMap<ConnectionElement, Point>();
                        int tempCounter = 0;
                        for (VisualElement element : model.getElements()) {
                            if (element instanceof ConnectionElement) {
                                ConnectionElement connectionElement = (ConnectionElement) element;
                                if (connectionElement.getElementFrom().compareTo(movingElement.getName()) == 0) {
                                    tempCounter++;
                                }
                            }
                        }
                        for (VisualElement element : model.getElements()) {
                            if (element instanceof ConnectionElement) {
                                ConnectionElement connectionElement = (ConnectionElement) element;
                                if (connectionElement.getElementFrom().compareTo(movingElement.getName()) == 0 &&
                                        tempCounter < 2) {
                                    Point copyPoint = new Point(connectionElement.getFirstPoint());
                                    oldPoints.put(connectionElement, copyPoint);
                                } else if (connectionElement.getElementFrom().compareTo(movingElement.getName()) == 0 &&
                                        connectionElement.hasChildrenConnections()) {
                                    boolean flag = false;
                                    for (VisualElement cElement : model.getElements()) {
                                        if (cElement instanceof ConnectionElement) {
                                            ConnectionElement cConnectionElement = (ConnectionElement) cElement;
                                            flag = cConnectionElement.hasChild(connectionElement.getNumber());
                                        }
                                    }
                                    if (!flag) {
                                        Point copyPoint = new Point(connectionElement.getFirstPoint());
                                        oldPoints.put(connectionElement, copyPoint);
                                    }
                                } else if (connectionElement.getElementTo().compareTo(movingElement.getName()) == 0) {
                                    Point copyPoint = new Point(connectionElement.getLastPoint());
                                    oldPoints.put(connectionElement, copyPoint);
                                }
                            }
                        }
                        lastCursorPoint = e.getPoint();
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_CONN) {
                    Point enterPoint = null;
                    Point exitPoint = null;
                    VisualElement inElement = null;
                    for (VisualElement element : model.getElements()) {
                        try {
                            if (element instanceof Enterable) {
                                enterPoint = ((Enterable) element).getEnterPoint(e.getPoint());
                                if (enterPoint != null) {
                                    inElement = element;
                                    break;
                                }
                            }
                            if (element instanceof Exitable) {
                                exitPoint = ((Exitable) element).getExitPoint(e.getPoint());
                                if (exitPoint != null) {
                                    inElement = element;
                                    break;
                                }
                            }
                        } catch (ConnectionAlreadyExistException e1) {
                            JOptionPane.showMessageDialog(rootFrame, e1.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if (inElement instanceof ConnectionElement) {
                        connectionElementToConnect = (ConnectionElement) inElement;
                        connectionElementToConnect.setSelected(true);
                        addingConnectionElement = new ConnectionElement(connectionElementToConnect.getElementFrom(), "",
                                exitPoint, e.getPoint(), model.getStandartColor(), model.getSelectedColor());
                        addingConnectionElement.setSelected(true);
                        model.getElements().add(addingConnectionElement);
                        lastCursorPoint = e.getPoint();
                        enterFlag = false;
                        repaint();
                    } else {
                        if (enterPoint != null) {
                            addingConnectionElement = new ConnectionElement("", ((Enterable) inElement).getName(), e.getPoint(),
                                    enterPoint, model.getStandartColor(), model.getSelectedColor());
                            addingConnectionElement.setSelected(true);
                            model.getElements().add(addingConnectionElement);
                            lastCursorPoint = e.getPoint();
                            elementToConnect = (ClosedElement) inElement;
                            elementToConnect.setSelected(true);
                            elementCanConnect = null;
                            enterFlag = true;
                            repaint();
                        } else if (exitPoint != null) {
                            addingConnectionElement = new ConnectionElement(((Exitable) inElement).getName(), "", exitPoint,
                                    e.getPoint(), model.getStandartColor(), model.getSelectedColor());
                            addingConnectionElement.setSelected(true);
                            model.getElements().add(addingConnectionElement);
                            lastCursorPoint = e.getPoint();
                            elementToConnect = (ClosedElement) inElement;
                            elementToConnect.setSelected(true);
                            elementCanConnect = null;
                            enterFlag = false;
                            repaint();
                        }
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                SchemeModel.CurrentAction currentAction = model.getCurrentAction();
                if (currentAction == SchemeModel.CurrentAction.MOVING) {
                    if (movingElement != null) {
                        boolean canMove = true;
                        for (VisualElement element : model.getElements()) {
                            if (element != movingElement && element.isOverlaped(
                                    new Rectangle(movingElement.getX(), movingElement.getY(), movingElement.getWidth(),
                                            movingElement.getHeight()))) {
                                canMove = false;
                                break;
                            }
                        }
                        if (!canMove) {
                            movingElement.setX(oldX);
                            movingElement.setY(oldY);
                            for (ConnectionElement connectionElement : oldPoints.keySet()) {
                                if (connectionElement.getElementFrom().compareTo(movingElement.getName()) == 0) {
                                    connectionElement.setFirstPoint(oldPoints.get(connectionElement));
                                } else {
                                    connectionElement.setLastPoint(oldPoints.get(connectionElement));
                                }
                            }
                            JOptionPane.showMessageDialog(rootFrame,
                                    "Неприпустиме розташування елемента.", "Помилка!", JOptionPane.ERROR_MESSAGE);
                        } else {
                            model.setModified(true);
                        }
                        movingElement.setSelected(false);
                        repaint();
                        movingElement = null;
                        oldPoints = null;
                        lastCursorPoint = null;
                    } else if (movingPointElement != null) {
                        movingPointElement.setSelectedPoint(movingPointIndex, false);
                        repaint();
                        model.setModified(true);
                        movingPointElement = null;
                        movingPointIndex = -1;
                        movingPointConnections = null;
                    }
                } else if (currentAction == SchemeModel.CurrentAction.ADDING_CONN) {
                    if (addingConnectionElement != null) {
                        VisualElement inElement = null;
                        Point enterPoint = null;
                        Point exitPoint = null;
                        for (VisualElement element : model.getElements()) {
                            try {
                                if (enterFlag) {
                                    if (element instanceof Exitable && (!(element instanceof ConnectionElement && connectionElementToConnect != null))) {
                                        exitPoint = ((Exitable) element).getExitPoint(e.getPoint());
                                        if (exitPoint != null) {
                                            inElement = element;
                                            break;
                                        }
                                    }
                                } else {
                                    if (element instanceof Enterable) {
                                        enterPoint = ((Enterable) element).getEnterPoint(e.getPoint());
                                        if (enterPoint != null) {
                                            inElement = element;
                                            break;
                                        }
                                    }
                                }
                            } catch (ConnectionAlreadyExistException e1) {
                                elementToConnect.setSelected(false);
                                model.getElements().remove(addingConnectionElement);
                                addingConnectionElement = null;
                                lastCursorPoint = null;
                                elementToConnect = null;
                                JOptionPane.showMessageDialog(rootFrame, e1.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (connectionElementCanConnect != null) {
                            if (exitPoint != null) {
                                addingConnectionElement.setFirstPoint(exitPoint);
                                addingConnectionElement.setElementFrom(connectionElementCanConnect.getElementFrom());
                                ((Exitable) elementToConnect).addExitConnectionName(connectionElementCanConnect.getElementFrom(), e.getPoint());
                                connectionElementCanConnect.addConnection(addingConnectionElement, addingConnectionElement.getFirstPoint());
                                connectionElementCanConnect.addExitConnectionName(elementToConnect.getName(), e.getPoint());
                                model.setModified(true);
                            } else {
                                model.getElements().remove(addingConnectionElement);
                            }
                            connectionElementCanConnect.setSelectedPoint(connectionElementPointCanConnectIndex, false);
                            addingConnectionElement.setSelected(false);
                            repaint();
                            addingConnectionElement = null;
                            lastCursorPoint = null;
                            elementToConnect.setSelected(false);
                            elementToConnect = null;
                            connectionElementCanConnect = null;
                            connectionElementPointCanConnectIndex = -1;
                        } else if (connectionElementToConnect != null) {
                            if (enterPoint != null) {
                                addingConnectionElement.setLastPoint(enterPoint);
                                addingConnectionElement.setElementTo(((Enterable) inElement).getName());
                                connectionElementToConnect.addExitConnectionName(((Enterable) inElement).getName(), e.getPoint());
                                connectionElementToConnect.addConnection(addingConnectionElement, addingConnectionElement.getFirstPoint());
                                ((Enterable) inElement).addEnterConnectionName(connectionElementToConnect.getElementFrom(), e.getPoint());
                                model.setModified(true);
                            } else {
                                model.getElements().remove(addingConnectionElement);
                            }
                            connectionElementToConnect.setSelected(false);
                            addingConnectionElement.setSelected(false);
                            if (elementCanConnect != null) {
                                elementCanConnect.setSelected(false);
                            }
                            repaint();
                            addingConnectionElement = null;
                            elementCanConnect = null;
                            lastCursorPoint = null;
                            connectionElementToConnect = null;
                        } else {
                            if (enterPoint != null) {
                                addingConnectionElement.setLastPoint(enterPoint);
                                addingConnectionElement.setElementTo(((Enterable) inElement).getName());
                                ((Exitable) elementToConnect).addExitConnectionName(((Enterable) inElement).getName(), e.getPoint());
                                ((Enterable) inElement).addEnterConnectionName(((Exitable) elementToConnect).getName(), e.getPoint());
                                model.setModified(true);
                            } else if (exitPoint != null) {
                                addingConnectionElement.setFirstPoint(exitPoint);
                                addingConnectionElement.setElementFrom(((Exitable) inElement).getName());
                                ((Exitable) inElement).addExitConnectionName(((Enterable) elementToConnect).getName(), e.getPoint());
                                ((Enterable) elementToConnect).addEnterConnectionName(((Exitable) inElement).getName(), e.getPoint());
                                model.setModified(true);
                            } else {
                                model.getElements().remove(addingConnectionElement);
                            }
                            elementToConnect.setSelected(false);
                            addingConnectionElement.setSelected(false);
                            if (elementCanConnect != null) {
                                elementCanConnect.setSelected(false);
                            }
                            if (connectionElementCanConnect != null) {
                                connectionElementCanConnect.setSelectedPoint(connectionElementPointCanConnectIndex, false);
                                connectionElementCanConnect = null;
                                connectionElementPointCanConnectIndex = -1;
                            }
                            repaint();
                            addingConnectionElement = null;
                            elementCanConnect = null;
                            lastCursorPoint = null;
                            elementToConnect = null;
                        }
                    }
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            SchemeModel.CurrentAction currentAction = model.getCurrentAction();
            if (currentAction == SchemeModel.CurrentAction.MOVING) {
                if (movingElement != null) {
                    movingElement.move(e.getX() - (int) lastCursorPoint.getX(), e.getY() - (int) lastCursorPoint.getY());
                    int tempCounter = 0;
                    for (VisualElement element : model.getElements()) {
                        if (element instanceof ConnectionElement) {
                            ConnectionElement connectionElement = (ConnectionElement) element;
                            if (connectionElement.getElementFrom().compareTo(movingElement.getName()) == 0) {
                                tempCounter++;
                            }
                        }
                    }
                    for (VisualElement element : model.getElements()) {
                        if (element instanceof ConnectionElement) {
                            ConnectionElement connectionElement = (ConnectionElement) element;
                            if (connectionElement.getElementFrom().compareTo(movingElement.getName()) == 0 && tempCounter < 2) {
                                connectionElement.moveFirstPoint(e.getX() - (int) lastCursorPoint.getX(),
                                        e.getY() - (int) lastCursorPoint.getY());
                            } else if (connectionElement.getElementFrom().compareTo(movingElement.getName()) == 0 &&
                                    connectionElement.hasChildrenConnections()) {
                                connectionElement.moveFirstPoint(e.getX() - (int) lastCursorPoint.getX(),
                                        e.getY() - (int) lastCursorPoint.getY());
                            }
                            if (connectionElement.getElementTo().compareTo(movingElement.getName()) == 0) {
                                connectionElement.moveLastPoint(e.getX() - (int) lastCursorPoint.getX(),
                                        e.getY() - (int) lastCursorPoint.getY());
                            }
                        }
                    }
                    repaint();
                    lastCursorPoint = e.getPoint();
                } else if (movingPointElement != null) {
                    movingPointElement.movePoint(movingPointIndex, e.getX() - lastCursorPoint.x, e.getY() - lastCursorPoint.y);
                    if (movingPointConnections != null) {
                        for (ConnectionElement ce : movingPointConnections) {
                            ce.manageTemporaryPoints();
                        }
                    }
                    movingPointIndex = movingPointElement.getInPointIndex(e.getPoint());
                    repaint();
                    lastCursorPoint = e.getPoint();
                }
            } else if (currentAction == SchemeModel.CurrentAction.ADDING_CONN) {
                if (addingConnectionElement != null) {
                    if (enterFlag) {
                        addingConnectionElement.moveFirstPoint(e.getPoint().x - lastCursorPoint.x,
                                e.getPoint().y - lastCursorPoint.y);
                        if (elementCanConnect != null) {
                            if (!((Exitable) elementCanConnect).isInExit(e.getPoint())) {
                                elementCanConnect.setSelected(false);
                                elementCanConnect = null;
                            }
                        }
                        if (connectionElementCanConnect != null) {
                            if (!connectionElementCanConnect.isInExit(e.getPoint())) {
                                connectionElementCanConnect.setSelectedPoint(connectionElementPointCanConnectIndex, false);
                                connectionElementCanConnect = null;
                                connectionElementPointCanConnectIndex = -1;
                            }
                        }
                        for (VisualElement element : model.getElements()) {
                            if (element instanceof Exitable) {
                                if (((Exitable) element).isInExit(e.getPoint())) {
                                    if (element instanceof ConnectionElement) {
                                        connectionElementCanConnect = (ConnectionElement) element;
                                        connectionElementPointCanConnectIndex = connectionElementCanConnect.getInPointIndex(e.getPoint());
                                    } else {
                                        elementCanConnect = (ClosedElement) element;
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        addingConnectionElement.moveLastPoint(e.getPoint().x - lastCursorPoint.x,
                                e.getPoint().y - lastCursorPoint.y);
                        if (elementCanConnect != null) {
                            if (!((Enterable) elementCanConnect).isInEnter(e.getPoint())) {
                                elementCanConnect.setSelected(false);
                                elementCanConnect = null;
                            }
                        }
                        for (VisualElement element : model.getElements()) {
                            if (element instanceof Enterable) {
                                if (((Enterable) element).isInEnter(e.getPoint())) {
                                    elementCanConnect = (ClosedElement) element;
                                    break;
                                }
                            }
                        }
                    }
                    if (elementCanConnect != null) {
                        elementCanConnect.setSelected(true);
                    }
                    if (connectionElementCanConnect != null) {
                        connectionElementCanConnect.setSelectedPoint(connectionElementPointCanConnectIndex, true);
                    }
                    repaint();
                    lastCursorPoint = e.getPoint();
                }
            }
        }

    }

}
