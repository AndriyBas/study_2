package zak.adcs.logicscheme.editor;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import zak.adcs.logicscheme.elements.*;
import zak.adcs.logicscheme.modelling.LogicFunctionsWorker;
import zak.adcs.logicscheme.modelling.SchemeTableModel;

import java.awt.*;
import java.util.ArrayList;

@XStreamAlias("schememodel")
public class SchemeModel {

    public enum CurrentAction {MOVING, ADDING_IN, ADDING_OUT, ADDING_AND, ADDING_OR, ADDING_NAND, ADDING_NOR, ADDING_XOR, ADDING_CONN, DELETING}

    public static final int DEFAULT_LOGIC_ELEMENT_WIDTH = 50;
    public static final int DEFAULT_LOGIC_ELEMENT_HEIGHT = 60;
    public static final int DEFAULT_IO_ELEMENT_WIDTH = 60;
    public static final int DEFAULT_IO_ELEMENT_HEIGHT = 25;
    public static final int DEFAULT_ELEMENT_IN_DISTANCE = 20;

    private Dimension size;

    @XStreamAlias("backgroundcolor")
    private Color backgroundColor;
    @XStreamAlias("standartcolor")
    private Color standartColor;
    @XStreamAlias("selectedcolor")
    private Color selectedColor;
    @XStreamAlias("textcolor")
    private Color textColor;

    private Font font;

    private ArrayList<VisualElement> elements;

    @XStreamOmitField
    private boolean isModified;
    @XStreamOmitField
    private CurrentAction currentAction;

    public SchemeModel() {
    }

    public SchemeModel(Dimension size, Color backgroundColor, Color standartColor, Color selectedColor, Color textColor,
                       Font font) {
        this.size = size;
        this.backgroundColor = backgroundColor;
        this.standartColor = standartColor;
        this.selectedColor = selectedColor;
        this.textColor = textColor;
        this.font = font;
        elements = new ArrayList<VisualElement>();
        isModified = true;
        currentAction = SchemeModel.CurrentAction.MOVING;
    }

    private static XStream getXStream() {
        XStream xStream = new XStream();
        Annotations.configureAliases(xStream, SchemeModel.class);
        Annotations.configureAliases(xStream, AndElement.class);
        Annotations.configureAliases(xStream, OrElement.class);
        Annotations.configureAliases(xStream, NandElement.class);
        Annotations.configureAliases(xStream, NorElement.class);
        Annotations.configureAliases(xStream, XorElement.class);
        Annotations.configureAliases(xStream, ConnectionElement.class);
        return xStream;
    }

    public static SchemeModel fromXML(String xml) {
        XStream xStream = getXStream();
        return (SchemeModel) xStream.fromXML(xml);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getStandartColor() {
        return standartColor;
    }

    public void setStandartColor(Color standartColor) {
        this.standartColor = standartColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Dimension getSize() {
        return size;
    }

    public ArrayList<VisualElement> getElements() {
        return elements;
    }

    public void addElement(VisualElement element) {
        elements.add(element);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public String toXML() {
        XStream xStream = getXStream();
        return xStream.toXML(this);
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public CurrentAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(CurrentAction currentAction) {
        this.currentAction = currentAction;
    }

    public void removeConnection(int number) {
        ConnectionElement connectionElement = null;
        for (VisualElement visualElement : elements) {
            if (visualElement instanceof ConnectionElement && ((ConnectionElement) visualElement).getNumber() == number) {
                connectionElement = (ConnectionElement) visualElement;
                break;
            }
        }
        if (connectionElement != null) {
            for (VisualElement visualElement : elements) {
                if (visualElement instanceof Enterable) {
                    ((Enterable) visualElement).removeEnterConnectionName(connectionElement.getElementFrom());
                }
                if (visualElement instanceof Exitable) {
                    ((Exitable) visualElement).removeExitConnectionName(connectionElement.getElementTo());
                }
            }
            for (ArrayList<Integer> pointConnections : connectionElement.getConnectionElements()) {
                if (pointConnections != null) {
                    for (int subNumber : pointConnections) {
                        removeConnection(subNumber);
                    }
                }
            }
            elements.remove(connectionElement);
        }
    }

    public void removePointConnections(int number, int pointIndex) {
        ConnectionElement connectionElement = null;
        for (VisualElement visualElement : elements) {
            if (visualElement instanceof ConnectionElement && ((ConnectionElement) visualElement).getNumber() == number) {
                connectionElement = (ConnectionElement) visualElement;
                break;
            }
        }
        if (connectionElement != null) {
            ArrayList<Integer> connectionsNumbers = connectionElement.getConnectionElements().get(pointIndex);
            if (connectionsNumbers != null) {
                for (int pointConnection : connectionsNumbers) {
                    removeConnection(pointConnection);
                }
            }
        }
    }

    public boolean containsName(String name, VisualElement visualElement) {
        for (VisualElement element : elements) {
            if (element.getClass().getName() == "zak.adcs.logicscheme.elements.InElement") {
                if ((((InElement) element).getName().compareTo(name) == 0) && (visualElement != element)) {
                    return true;
                }
            } else if (element.getClass().getName() == "zak.adcs.logicscheme.elements.OutElement") {
                if ((((OutElement) element).getName().compareTo(name) == 0) && (visualElement != element)) {
                    return true;
                }
            } else if (element.getClass().getName() != "zak.adcs.logicscheme.elements.ConnectionElement") {
                if ((((LogicElement) element).getName().compareTo(name) == 0) && (visualElement != element)) {
                    return true;
                }
            }
        }
        return false;
    }

    public SchemeTableModel getSchemeTableModel() {
        int inCount = 0;
        int elementCount = 0;
        int outCount = 0;
        for (VisualElement element : elements) {
            if (element instanceof InElement) {
                inCount++;
            } else if (element instanceof OutElement) {
                outCount++;
            } else if (!(element instanceof ConnectionElement)) {
                elementCount++;
            }
        }
        String[] inNames = new String[inCount];
        String[] elementNames = new String[elementCount];
        LogicFunctionsWorker.LogicFunction[] logicFunctions = new LogicFunctionsWorker.LogicFunction[elementCount];
        int[] inertiaDelay = new int[elementCount];
        int[] dynamicDelay = new int[elementCount];
        String[] outNames = new String[outCount];
        int matrixSize = inCount + elementCount + outCount;
        int[][] connectivityMatrix = new int[matrixSize][];
        for (int i = 0; i < connectivityMatrix.length; i++) {
            connectivityMatrix[i] = new int[matrixSize];
            for (int j = 0; j < connectivityMatrix[i].length; j++) {
                connectivityMatrix[i][j] = 0;
            }
        }
        int inIndex = 0;
        int elementIndex = 0;
        int outIndex = 0;
        for (int i = 0; i < elements.size(); i++) {
            VisualElement element = elements.get(i);
            if (element instanceof InElement) {
                inNames[inIndex++] = ((InElement) element).getName();
            } else if (element instanceof OutElement) {
                outNames[outIndex++] = ((OutElement) element).getName();
            } else if (!(element instanceof ConnectionElement)) {
                elementNames[elementIndex] = ((LogicElement) element).getName();
                inertiaDelay[elementIndex] = ((LogicElement) element).getInertiaDelay();
                dynamicDelay[elementIndex] = ((LogicElement) element).getDynamicDelay();
                if (element instanceof AndElement && !(element instanceof NandElement)) {
                    logicFunctions[elementIndex++] = LogicFunctionsWorker.LogicFunction.AND;
                } else if (element instanceof OrElement && !(element instanceof NorElement)) {
                    logicFunctions[elementIndex++] = LogicFunctionsWorker.LogicFunction.OR;
                } else if (element instanceof NandElement) {
                    logicFunctions[elementIndex++] = LogicFunctionsWorker.LogicFunction.NAND;
                } else if (element instanceof NorElement) {
                    logicFunctions[elementIndex++] = LogicFunctionsWorker.LogicFunction.NOR;
                } else if (element instanceof XorElement) {
                    logicFunctions[elementIndex++] = LogicFunctionsWorker.LogicFunction.XOR;
                }
            }
        }
        for (VisualElement element : elements) {
            if (element instanceof ConnectionElement) {
                ConnectionElement connectionElement = (ConnectionElement) element;
                boolean foundFrom = false;
                int indexFrom = -1;
                for (int i = 0; i < inNames.length; i++) {
                    indexFrom++;
                    if (inNames[i].compareTo(connectionElement.getElementFrom()) == 0) {
                        foundFrom = true;
                        break;
                    }
                }
                if (!foundFrom) {
                    for (int i = 0; i < elementNames.length; i++) {
                        indexFrom++;
                        if (elementNames[i].compareTo(connectionElement.getElementFrom()) == 0) {
                            foundFrom = true;
                            break;
                        }
                    }
                    if (!foundFrom) {
                        for (int i = 0; i < outNames.length; i++) {
                            indexFrom++;
                            if (outNames[i].compareTo(connectionElement.getElementFrom()) == 0) {
                                foundFrom = true;
                                break;
                            }
                        }
                    }
                }
                boolean foundTo = false;
                int indexTo = -1;
                for (int i = 0; i < inNames.length; i++) {
                    indexTo++;
                    if (inNames[i].compareTo(connectionElement.getElementTo()) == 0) {
                        foundTo = true;
                        break;
                    }
                }
                if (!foundTo) {
                    for (int i = 0; i < elementNames.length; i++) {
                        indexTo++;
                        if (elementNames[i].compareTo(connectionElement.getElementTo()) == 0) {
                            foundTo = true;
                            break;
                        }
                    }
                    if (!foundTo) {
                        for (int i = 0; i < outNames.length; i++) {
                            indexTo++;
                            if (outNames[i].compareTo(connectionElement.getElementTo()) == 0) {
                                foundTo = true;
                                break;
                            }
                        }
                    }
                }
                connectivityMatrix[indexFrom][indexTo]++;
            }
        }
        return new SchemeTableModel(inNames, elementNames, logicFunctions, inertiaDelay, dynamicDelay, outNames,
                connectivityMatrix);
    }

}
