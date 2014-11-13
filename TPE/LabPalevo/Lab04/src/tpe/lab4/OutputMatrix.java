
package tpe.lab4;

import java.util.Random;

public class OutputMatrix {
    private double minY, maxY;
    private int N = 8;
    private int m;

    private double[][] matrix;
    private Random r = new Random();

    public OutputMatrix(double minY, double maxY, int m) {
        this.minY = minY;
        this.maxY = maxY;
        this.m = m;
        matrix = new double[m][N];
    }

    public double[][] getY() {
        for (int i = 0; i < m; i++) {
            matrix[i] = new double[N];
            for (int j = 0; j < N; j++) {
                matrix[i][j] = r.nextDouble() * (maxY - minY) + minY;
            }
        }
        return matrix;
    }
}
