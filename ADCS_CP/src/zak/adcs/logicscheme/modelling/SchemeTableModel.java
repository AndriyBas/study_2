package zak.adcs.logicscheme.modelling;

import javax.swing.table.AbstractTableModel;

public class SchemeTableModel extends AbstractTableModel {

    private String[] inNames;
    private String[] elementNames;
    private LogicFunctionsWorker.LogicFunction[] logicFunctions;
    private int[] inertiaDelay;
    private int[] dynamicDelay;
    private String[] outNames;
    private int[][] connectivityMatrix;

    public SchemeTableModel(String[] inNames, String[] elementNames, LogicFunctionsWorker.LogicFunction[] logicFunctions,
                            int[] inertiaDelay, int[] dynamicDelay, String[] outNames, int[][] connectivityMatrix) {
        this.inNames = inNames;
        this.elementNames = elementNames;
        this.logicFunctions = logicFunctions;
        this.inertiaDelay = inertiaDelay;
        this.dynamicDelay = dynamicDelay;
        this.outNames = outNames;
        this.connectivityMatrix = connectivityMatrix;
    }

    public int getInCount() {
        return inNames.length;
    }

    public String[] getInNames() {
        return inNames;
    }

    public String[] getElementNames() {
        return elementNames;
    }

    public LogicFunctionsWorker.LogicFunction[] getLogicFunctions() {
        return logicFunctions;
    }

    public int[] getInertiaDelay() {
        return inertiaDelay;
    }

    public int[] getDynamicDelay() {
        return dynamicDelay;
    }

    public int[][] getConnectivityMatrixExceptOut() {
        int[][] result = new int[inNames.length + elementNames.length][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new int[result.length];
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = connectivityMatrix[i][j];
            }
        }
        return result;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public int getRowCount() {
        return connectivityMatrix.length + 1;
    }

    public int getColumnCount() {
        return (inNames.length + elementNames.length + outNames.length + 1);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == 0 && columnIndex == 0) {
            return "";
        }
        if (rowIndex == 0) {
            int temp = columnIndex - 1;
            if (temp >= inNames.length) {
                temp -= inNames.length;
                if (temp < elementNames.length) {
                    return elementNames[temp];
                } else {
                    temp -= elementNames.length;
                    return outNames[temp];
                }
            } else {
                return inNames[temp];
            }
        }
        if (columnIndex == 0) {
            int temp = rowIndex - 1;
            if (temp >= inNames.length) {
                temp -= inNames.length;
                if (temp < elementNames.length) {
                    return elementNames[temp];
                } else {
                    temp -= elementNames.length;
                    return outNames[temp];
                }
            } else {
                return inNames[temp];
            }
        }
        return connectivityMatrix[rowIndex - 1][columnIndex - 1];
    }

    public int getElementCount() {
        return elementNames.length;
    }

}
