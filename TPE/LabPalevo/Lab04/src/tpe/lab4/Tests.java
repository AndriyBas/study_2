package tpe.lab4;

public class Tests {
    private double[][] xn;
    private double[][] y;
    private double[] yMid;
    private double[] b;

    private double[] var;
    private double varSum;
    private double sb;

    public Tests(double[][] xn, double[][] y, double[] yMid, double[] b) {
        super();
        this.xn = xn;
        this.y = y;
        this.yMid = yMid;
        this.b = b;
    }

    public double cochransTest() {
        var = new double[yMid.length];
        varSum = 0.0;
        for (int i = 0; i < var.length; i++) {
            var[i] = Utils.getVariance(new double[]{y[0][i], y[1][i], y[2][i]}, yMid[i]);
            varSum += var[i];
        }
        return Utils.getMax(var) / varSum;
    }

    public void studentsTest() {
        sb = varSum / var.length;

        double sbs = Math.sqrt(sb / (y.length * y[0].length));

        double[] beta = new double[yMid.length];

        for (int i = 0; i < beta.length; i++)
            beta[0] += yMid[i];
        beta[0] /= yMid.length;

        for (int i = 1; i <= xn.length; i++) {
            for (int j = 0; j < xn[0].length; j++)
                beta[i] += yMid[j] * xn[i - 1][j];
            beta[i] /= xn[0].length;
        }

        for (int i = 0; i < beta.length; i++) {
            beta[4] += xn[0][i] * xn[1][i] * yMid[i];
            beta[5] += xn[0][i] * xn[2][i] * yMid[i];
            beta[6] += xn[1][i] * xn[2][i] * yMid[i];
            beta[7] += xn[0][i] * xn[1][i] * xn[2][i] * yMid[i];
        }

        System.out.println("Student's test:");
        double[] t = new double[beta.length];
        for (int i = 0; i < t.length; i++) {
            t[i] = Math.abs(beta[i]) / sbs;
            System.out.println("t[" + i + "] = " + t[i]);
        }

        for (int i = 0; i < t.length; i++)
            if (t[i] < 2.306)
                b[i] = 0.0;
    }

    public double fishersTest(double[] check) {
        int d = 0;
        for (int i = 0; i < b.length; i++)
            if (b[i] != 0.0)
                d++;
        double sad = 0.0;
        for (int i = 0; i < y.length; i++)
            sad += Math.pow(check[i] - yMid[i], 2);
        sad *= y.length / (yMid.length - d);
        return sad / sb;
    }
}
