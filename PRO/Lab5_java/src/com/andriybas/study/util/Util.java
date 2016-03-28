package com.andriybas.study.util;

public class Util {

    public static int findMin(int[] V, int start, int end) {
        int localMin = Integer.MAX_VALUE;
        for (int i = start; i < end; i++) {
            if (localMin > V[i])
                localMin = V[i];
        }
        return localMin;
    }

    public static int findMax(int[] V, int start, int end) {
        int localMax = Integer.MIN_VALUE;
        for (int i = start; i < end; i++) {
            if (localMax < V[i])
                localMax = V[i];
        }
        return localMax;
    }

    public static void calculate(int n, int start, int end,
                                 int[][] MA, int maxZ, int[][] MO,
                                 int minZ, int[][] ME, int[][] MT) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < n; j++) {
                int val = 0;
                for (int k = 0; k < n; k++) {
                    val += ME[i][k] * MT[k][j];
                }
                MA[i][j] = MO[i][j] * maxZ + val * minZ;
            }
        }
    }
}
