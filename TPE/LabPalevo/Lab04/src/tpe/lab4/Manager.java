package tpe.lab4;

import java.util.Locale;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Manager {
    private int k = 3;

    private double[][] x, y;
    private double[][] xMeasures;
    private double[][] xn = new double[3][];

    private double[] yMid;

    private double[] mx;
    private double my;

    private double[] ai;
    private double[][] a;
    private double[] c;

    private double[] b;

    private double[] check;

    private InputMatrix im;
    private OutputMatrix om;

    public Manager(int minX1, int maxX1, int minX2, int maxX2, int minX3, int maxX3, int minY, int maxY) {
        im = new InputMatrix(minX1, maxX1, minX2, maxX2, minX3, maxX3);
        om = new OutputMatrix(minY, maxY, k);
        b = new double[3];
    }

    public void main() {
        x = im.getX();
        y = om.getY();
        xMeasures = im.getMeasures();

        System.out.println("Xi measures:");
        System.out.println(TablesPrint.xMeasures(xMeasures));
        printDelimiter();

        xn = im.getXn();
        System.out.println(TablesPrint.xn(xn));
        printDelimiter();
        System.out.println(TablesPrint.table(x, y));
        printDelimiter();

        mx = new double[x.length];
        for (int i = 0; i < mx.length; i++) {
            mx[i] = Utils.getMid(x[i]);
        }

        yMid = new double[x[0].length];
        for (int i = 0; i < yMid.length; i++) {
            yMid[i] = Utils.getMid(y[0][i], y[1][i], y[2][i]);
        }

        System.out.println("Y mid:");
        System.out.println(TablesPrint.getStringFromArray(yMid));
        printDelimiter();

        my = Utils.getMid(yMid);

        ai = Utils.getAi(x, yMid);
        a = Utils.getA(x);

        RealMatrix system = MatrixUtils.createRealMatrix(getSystem(mx, a));
        RealMatrix sInv = new LUDecomposition(system).getSolver().getInverse();

        c = new double[x[0].length];
        c[0] = my;
        for (int i = 1; i < c.length; i++)
            c[i] = ai[i - 1];
        RealMatrix m = MatrixUtils.createColumnRealMatrix(c);

        b = sInv.multiply(m).getColumn(0);
        System.out.println("Regression coefficients:");
        System.out.println(TablesPrint.getStringFromArray(b));
        printDelimiter();

        System.out.println("System:");
        printSystem();
        printDelimiter();

        Tests t = new Tests(xn, y, yMid, b);
        double gp = t.cochransTest();
        System.out.println("Cochran's test:\t" + gp + " : " + (gp < 0.4377));
        printDelimiter();

        t.studentsTest();
        printDelimiter();
        System.out.println("System after Student's test:");
        printSystem();
        printDelimiter();

        double fr = t.fishersTest(check);
        System.out.println("Fisher's test:\t" + fr + " : " + (fr < 4.5));

    }

    private double[][] getSystem(double[] mx, double[][] a) {
        double[][] s = new double[x[0].length][x[0].length];

        s[0][0] = 1.0;
        for (int i = 1; i < s.length; i++) {
            s[0][i] = mx[i - 1];
            s[i][0] = mx[i - 1];

            for (int j = 1; j < s.length; j++)
                s[i][j] = a[i - 1][j - 1];
        }
        return s;
    }

    private double[] getCheck() {
        double[] res = new double[x[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i] += b[0];
            for (int j = 0; j < x.length; j++)
                res[i] += x[j][i] * b[j + 1];
        }
        return res;
    }

    private String[] getStringSystem() {
        String[] res = new String[x[0].length];
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < res.length; i++) {
            str.append(formatDouble(b[0]));
            for (int j = 0; j < x.length; j++) {
                str.append(" + ");
                str.append(formatDouble(x[j][i]));
                str.append(" * ");
                str.append(formatDouble(b[j + 1]));
            }
            str.append(" = ");
            res[i] = str.toString();
            str.delete(0, str.length());
        }
        return res;
    }

    private void printSystem() {
        String[] system = getStringSystem();
        check = getCheck();

        for (int i = 0; i < check.length; i++)
            System.out.println(system[i] + formatDouble(check[i]));
    }

    public static void printDelimiter() {
        System.out.println("----------------------------------------------------------------------------\n");
    }

    public static String formatDouble(double arg) {
        return String.format(Locale.US, "% .1f", arg);
    }
}
