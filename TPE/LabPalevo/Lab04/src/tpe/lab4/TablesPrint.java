package tpe.lab4;

import java.util.Locale;

public class TablesPrint {
    public static String xMeasures(double[][] measures) {
        StringBuilder str = new StringBuilder();
        str.append("\tMin\tMax\n");

        for (int i = 0; i < measures.length; i++) {
            str.append("X");
            str.append(i + 1);
            str.append(formatDouble(measures[i][0]));
            str.append(formatDouble(measures[i][1]));
            str.append("\n");
        }

        str.delete(str.length() - 1, str.length());
        return str.toString();
    }

    public static String xn(double[][] xn) {
        StringBuilder str = new StringBuilder("Xn:\n       ");

        for (int i = 0; i < xn.length; i++) {
            str.append("X");
            str.append(i + 1);
            str.append("       ");
        }
        str.append("\n");

        for (int i = 0; i < xn[0].length; i++) {
            for (int j = 0; j < xn.length; j++) {
                str.append(formatDouble(xn[j][i]));
            }
            str.append("\n");
        }

        str.delete(str.length() - 1, str.length());
        return str.toString();
    }

    public static String table(double[][] x, double[][] y) {
        StringBuilder str = new StringBuilder("Experiment Matrix:\n       ");

        for (int i = 0; i < x.length; i++) {
            str.append("X");
            str.append(i + 1);
            str.append("       ");
        }
        str.delete(str.length() - 7, str.length());
        str.append("|       ");

        for (int i = 0; i < y.length; i++) {
            str.append("Y");
            str.append(i + 1);
            str.append("       ");
        }
        str.append("\n");

        for (int i = 0; i < x[0].length; i++) {
            for (int j = 0; j < x.length; j++) {
                str.append(formatDouble(x[j][i]));
            }
            str.append("|");

            for (int j = 0; j < y.length; j++) {
                str.append(formatDouble(y[j][i]));
            }
            str.append("\n");
        }
        str.delete(str.length() - 1, str.length());
        return str.toString();
    }

    public static String getStringFromArray(double[] arg) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < arg.length; i++)
            str.append(formatDouble(arg[i]));
        return str.toString();
    }

    public static String formatDouble(double arg) {
        return String.format(Locale.US, "% 9.1f", arg);
    }
}