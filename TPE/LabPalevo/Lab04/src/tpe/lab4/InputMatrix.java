package tpe.lab4;

public class InputMatrix {
    private double[][] measures;

    private double[][] x;
    private int k = 3;
    private int p = 7;
    private int N = 8;

    private double[][] xn = new double[][]{
            {-1, -1, 1, 1, -1, -1, 1, 1},
            {-1, 1, -1, 1, -1, 1, -1, 1},
            {-1, 1, 1, -1, 1, -1, -1, 1}
    };

    public InputMatrix(int minX1, int maxX1, int minX2, int maxX2, int minX3, int maxX3) {
        measures = new double[][]{
                {minX1, maxX1},
                {minX2, maxX2},
                {minX3, maxX3}
        };
    }

    public double[][] getX() {
        x = new double[p][N];

        for (int i = 0; i < k; i++)
            for (int j = 0; j < x[0].length; j++)
                x[i][j] = (xn[i][j] == -1 ? measures[i][0] : measures[i][1]);

        for (int i = 0; i < x[0].length; i++) {
            x[3][i] = x[0][i] * x[1][i];
            x[4][i] = x[0][i] * x[2][i];
            x[5][i] = x[1][i] * x[2][i];
            x[6][i] = x[0][i] * x[1][i] * x[2][i];
        }
        return x;
    }

    public double[][] getMeasures() {
        return measures;
    }

    public double[][] getXn() {
        return xn;
    }
}
