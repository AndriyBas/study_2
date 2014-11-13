
package tpe.lab4;

public class Utils {
    public static double getMid(double... args) {
        double res = 0.0;
        for (int i = 0; i < args.length; i++)
            res += args[i];
        return res / args.length;
    }

    public static double getMin(double[] sample) {
        double result = Double.MAX_VALUE;
        for (int i = 0; i < sample.length; i++)
            if (result > sample[i])
                result = sample[i];
        return result;
    }

    public static double getMax(double[] sample) {
        double result = Double.MIN_VALUE;
        for (int i = 0; i < sample.length; i++)
            if (result < sample[i])
                result = sample[i];
        return result;
    }

    public static double getVariance(double[] sample, double mid) {
        double var = 0.0;
        for (int i = 0; i < sample.length; i++)
            var += Math.pow(sample[i] - mid, 2);
        return var / sample.length;
    }

    public static double[] getAi(double[][] x, double[] yMid) {
        double[] a = new double[x.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < x[0].length - 1; j++)
                a[i] += x[i][j] * yMid[j];
            a[i] /= x[0].length;
        }
        return a;
    }

    public static double[][] getA(double[][] x) {
        double[][] a = new double[x.length][x.length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                a[i][j] = getAij(x, i, j);
        return a;
    }

    private static double getAij(double[][] x, int i, int j) {
        double a = 0.0;
        for (int k = 0; k < x[0].length; k++)
            a += x[i][k] * x[j][k];
        return a / x[0].length;
    }
}
