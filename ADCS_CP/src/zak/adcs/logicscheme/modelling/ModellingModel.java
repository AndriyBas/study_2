package zak.adcs.logicscheme.modelling;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ModellingModel extends AbstractTableModel {

    public static final int INTERVAL_WIDTH = 20;
    public static final int DIAGRAM_BORDER = 20;
    public static final int DIAGRAM_Y_INTERVAL = 15;

    public static final String TIME_STRING = "Час t";

    private static final String[] LAST_COLUMN_NAMES = {"ТТС", "t", "ТБС"};

    private Color backgroundColor;
    private Color standartColor;
    private Color textColor;

    private Font font;

    private String[] inNames;
    private String[] elementNames;
    private int[] inertiaDelay;
    private int[] dynamicDelay;
    private LogicFunctionsWorker.LogicFunction[] logicFunctions;
    private int[][] connectivityMatrix;

    private int t;
    private HashMap<Integer, LinkedList<Integer>> futureEventsTable;
    private int lastEventT;
    private LinkedList<String> previousFutureEvents;

    private Dimension diagramSize;
    private int graphicHeight;
    private ArrayList<HashMap<Integer, Character>> diagrams;

    private LinkedList<String[]> rows;
    private int columnCount;

    private boolean isBusy;
    private boolean isClear;

    public ModellingModel(String[] inNames, String[] elementNames, int[] inertiaDelay, int[] dynamicDelay,
                          LogicFunctionsWorker.LogicFunction[] logicFunctions, int[][] connectivityMatrix,
                          Color backgroundColor, Color standartColor, Color textColor, Font font) {
        this.inNames = inNames;
        this.elementNames = elementNames;
        this.inertiaDelay = inertiaDelay;
        this.dynamicDelay = dynamicDelay;
        this.logicFunctions = logicFunctions;
        this.connectivityMatrix = connectivityMatrix;
        this.backgroundColor = backgroundColor;
        this.standartColor = standartColor;
        this.textColor = textColor;
        this.font = font;
        columnCount = inNames.length + elementNames.length + 3;
        rows = new LinkedList<String[]>();
        isBusy = false;
        isClear = true;
        diagramSize = new Dimension(100, 100);
        t = 0;
        lastEventT = 0;
        futureEventsTable = new HashMap<Integer, LinkedList<Integer>>();
        previousFutureEvents = new LinkedList<String>();
        diagrams = new ArrayList<HashMap<Integer, Character>>();
        for (int i = 0; i < inNames.length + elementNames.length; i++) {
            diagrams.add(new HashMap<Integer, Character>());
        }
    }

    private boolean hasFutureEvents() {
        if (futureEventsTable.isEmpty()) {
            return false;
        }
        return true;
    }

    private void addDiagramValues(int t) {
        int index = 0;
        for (int i = 0; i < inNames.length; i++) {
            int j = rows.size() - 1;
            boolean found = false;
            while (!found && j >= 0) {
                if (rows.get(j)[index].length() > 0 && rows.get(j)[index].compareTo(" ") != 0) {
                    found = true;
                }
                j--;
            }
            diagrams.get(index).put(t, rows.get(++j)[index].charAt(0));
            index++;
        }
        for (int i = 0; i < elementNames.length; i++) {
            int j = rows.size() - 1;
            boolean found = false;
            while (!found && j >= 0) {
                if (rows.get(j)[index].length() > 0) {
                    found = true;
                }
                j--;
            }
            diagrams.get(index).put(t, rows.get(++j)[index].charAt(0));
            index++;
        }
    }

    private String getInputSet(ArrayList<Integer> numbers) {
        StringBuffer buffer = new StringBuffer();
        for (int index : numbers) {
            int j = rows.size() - 1;
            boolean found = false;
            while (!found && j >= 0) {
                if (rows.get(j)[index].length() > 0 && rows.get(j)[index].compareTo(" ") != 0) {
                    found = true;
                }
                j--;
            }
            buffer.append(rows.get(++j)[index]);
        }
        return buffer.toString();
    }

    private String[] getStartCondition(String startSet) {
        ArrayList<String[]> conditions = new ArrayList<>();
        String[] condition = new String[elementNames.length];
        for (int i = 0; i < condition.length; i++) {
            condition[i] = "0";
        }
        conditions.add(condition);
        boolean endFlag;
        do {
            condition = new String[elementNames.length];
            for (int i = 0; i < elementNames.length; i++) {
                ArrayList<Integer> inputElements = new ArrayList<Integer>();
                for (int j = 0; j < connectivityMatrix.length; j++) {
                    if (connectivityMatrix[j][i + inNames.length] > 0) {
                        for (int k = connectivityMatrix[j][i + inNames.length]; k > 0; k--) {
                            inputElements.add(j);
                        }
                    }
                }
                StringBuffer inputBuffer = new StringBuffer();
                for (int index : inputElements) {
                    if (index < inNames.length) {
                        inputBuffer.append(startSet.substring(index, index + 1));
                    } else {
                        inputBuffer.append(conditions.get(conditions.size() - 1)[index - inNames.length]);
                    }
                }
                StringBuffer buffer = new StringBuffer();
                buffer.append(LogicFunctionsWorker.getValue(logicFunctions[i], inputBuffer.toString()));
                condition[i] = buffer.toString();
            }
            String[] previousCondition = conditions.get(conditions.size() - 1);
            endFlag = true;
            for (int i = 0; i < elementNames.length; i++) {
                if (condition[i].compareTo(previousCondition[i]) != 0) {
                    endFlag = false;
                    break;
                }
            }
            if (!endFlag) {
                conditions.add(condition);
            }
        } while (!endFlag);
        return conditions.get(conditions.size() - 1);
    }

    public boolean isBusy() {
        return isBusy;
    }

    public boolean isClear() {
        return isClear;
    }

    public void step(String inputSet, String startSet) {
        String[] row = new String[columnCount];
        int index = 0;
        for (int i = 0; i < inNames.length; i++) {
            row[index++] = "";
        }
        for (int i = 0; i < elementNames.length; i++) {
            row[index++] = "0";
        }
        for (int i = 0; i < LAST_COLUMN_NAMES.length; i++) {
            row[index++] = "";
        }
        rows.add(row);
        row = new String[columnCount];
        index = 0;
        for (int i = 0; i < startSet.length(); i++) {
            row[index++] = startSet.substring(i, i + 1);
        }
        String[] startCondition = getStartCondition(startSet);
        for (int i = 0; i < startCondition.length; i++) {
            row[index++] = startCondition[i];
        }
        row[index++] = "";
        row[index++] = "";
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (int i = inNames.length; i < inNames.length + elementNames.length; i++) {
            for (int j = 0; j < inNames.length; j++) {
                if (connectivityMatrix[j][i] > 0) {
                    if (!isFirst) {
                        stringBuilder.append("-");
                    }
                    stringBuilder.append(elementNames[i - inNames.length]);
                    if (futureEventsTable.get(0) == null) {
                        futureEventsTable.put(0, new LinkedList<Integer>());
                    }
                    futureEventsTable.get(0).add(i);
                    isFirst = false;
                    break;
                }
            }
        }
        row[index] = stringBuilder.toString();
        previousFutureEvents.add(stringBuilder.toString());
        rows.add(row);
        isClear = false;
        step(inputSet, false);
    }

    public void step() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < inNames.length; i++) {
            buffer.append(" ");
        }
        step(buffer.toString(), false);
    }

    public void step(String inputSet, boolean newSet) {
        if (newSet) {
            for (int i = inNames.length; i < inNames.length + elementNames.length; i++) {
                for (int j = 0; j < inNames.length; j++) {
                    if (connectivityMatrix[j][i] > 0) {
                        if (futureEventsTable.get(t) == null) {
                            futureEventsTable.put(t, new LinkedList<Integer>());
                        }
                        futureEventsTable.get(t).add(i);
                        break;
                    }
                }
            }
        }
        String[] row = new String[columnCount];
        for (int i = 0; i < inNames.length; i++) {
            row[i] = inputSet.substring(i, i + 1);
        }
        for (int i = inNames.length; i < inNames.length + elementNames.length; i++) {
            row[i] = "";
        }
        LinkedList<Integer> currentEvents = futureEventsTable.get(t);
        futureEventsTable.remove(t);
        if (currentEvents != null) {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder __sb = new StringBuilder();
            for (int i = 0; i < currentEvents.size(); i++) {
                stringBuilder.append(elementNames[currentEvents.get(i) - inNames.length]);

                __sb.append(elementNames[currentEvents.get(i) - inNames.length]);
                // TODO: added delays here
                // <<<<<<<<
                __sb.append("(d:" + dynamicDelay[currentEvents.get(i) - inNames.length]);
                __sb.append(",i:" + inertiaDelay[currentEvents.get(i) - inNames.length] + ")");
                // >>>>>>>>
                if (i < currentEvents.size() - 1) {
                    __sb.append(" - ");
                    stringBuilder.append("-");
                }
            }
//            row[columnCount - 3] = stringBuilder.toString();
            row[columnCount - 3] = __sb.toString();

            String tempString = stringBuilder.toString();
            row[columnCount - 2] = String.valueOf(t);
            rows.add(row);
            stringBuilder = new StringBuilder();
            boolean isFirst = true;
            for (int ii = 0; ii < currentEvents.size(); ii++) {
                int index = currentEvents.get(ii);
                //TODO: I changed delay calculation
//                int delay = dynamicDelay[index - inNames.length];
                int delay = inertiaDelay[index - inNames.length] + dynamicDelay[index - inNames.length];
                ArrayList<Integer> inputElements = new ArrayList<Integer>();
                for (int i = 0; i < connectivityMatrix.length; i++) {
                    if (connectivityMatrix[i][index] > 0) {
                        for (int j = connectivityMatrix[i][index]; j > 0; j--) {
                            inputElements.add(i);
                        }
                    }
                }
                StringBuffer buffer = new StringBuffer();
                buffer.append(LogicFunctionsWorker.getValue(logicFunctions[index - inNames.length], getInputSet(inputElements)));
                row[index] = buffer.toString();
                for (int i = inNames.length; i < connectivityMatrix[index].length; i++) {
                    if (connectivityMatrix[index][i] > 0) {
                        if (!isFirst) {
                            stringBuilder.append("-");
                        }
                        stringBuilder.append(elementNames[i - inNames.length]);
                        isFirst = false;
                        boolean addFlag = true;
                        for (int eventT = t; eventT <= lastEventT; eventT++) {
                            if (futureEventsTable.get(eventT) != null) {
                                if (futureEventsTable.get(eventT).contains(i)) {
                                    if (eventT < t + delay) {
                                        futureEventsTable.get(eventT).remove(new Integer(i));
                                    } else {
                                        addFlag = false;
                                    }
                                }
                            }
                        }
                        if (addFlag) {
                            if (delay == 0) {
                                currentEvents.add(i);
                            } else {
                                if (futureEventsTable.get(t + delay) == null) {
                                    futureEventsTable.put(t + delay, new LinkedList<Integer>());
                                }
                                if (!futureEventsTable.get(t + delay).contains(i)) {
                                    futureEventsTable.get(t + delay).add(i);
                                    if (t + delay > lastEventT) {
                                        lastEventT = t + delay;
                                    }
                                }
                            }
                        }
                    }
                }
            }


            for (Integer i : futureEventsTable.keySet()) {
                LinkedList<Integer> list = futureEventsTable.get(i);
                System.out.println(i + " : " + list.toString());
                System.out.println();
            }

            row[columnCount - 1] = stringBuilder.toString();
            isBusy = hasFutureEvents();
            previousFutureEvents.add(tempString);
            for (String e : previousFutureEvents) {
                if (e.compareTo(stringBuilder.toString()) == 0 && stringBuilder.toString().length() > 0) {
                    isBusy = false;
                    break;
                }
            }
            if (!isBusy) {
                previousFutureEvents = new LinkedList<String>();
                lastEventT = 0;
            } else {
                previousFutureEvents.add(stringBuilder.toString());
            }
        } else {
            row[inNames.length + elementNames.length + 1] = String.valueOf(t);
            rows.add(row);
        }
        addDiagramValues(t);
        t++;
    }

    public void modelling(String inputSet, String startSet) {
        if (!isBusy) {
            if (isClear) {
                step(inputSet, startSet);
            } else {
                step(inputSet, true);
            }
        }
        while (isBusy) {
            step();
        }
    }

    public void clear() {
        t = 0;
        lastEventT = 0;
        futureEventsTable = new HashMap<Integer, LinkedList<Integer>>();
        previousFutureEvents = new LinkedList<String>();
        isBusy = false;
        isClear = true;
        rows = new LinkedList<String[]>();
    }

    public int getT() {
        return t;
    }

    public void setDiagramSize(Dimension diagramSize) {
        this.diagramSize = diagramSize;
        graphicHeight = (int) (diagramSize.getHeight() / (inNames.length + elementNames.length));
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < inNames.length; i++) {
            names.add(inNames[i]);
        }
        for (int i = 0; i < elementNames.length; i++) {
            names.add(elementNames[i]);
        }
        return names;
    }

    public ArrayList<HashMap<Integer, Character>> getDiagrams() {
        return diagrams;
    }

    public Dimension getDiagramSize() {
        return diagramSize;
    }

    public int getGraphicHeight() {
        return graphicHeight;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getStandartColor() {
        return standartColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Font getFont() {
        return font;
    }

    @Override
    public String getColumnName(int column) {
        int temp = column;
        if (temp < inNames.length) {
            return inNames[temp];
        } else {
            temp -= inNames.length;
            if (temp < elementNames.length) {
                return elementNames[temp];
            } else {
                temp -= elementNames.length;
                return LAST_COLUMN_NAMES[temp];
            }
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows.get(rowIndex)[columnIndex];
    }
}
