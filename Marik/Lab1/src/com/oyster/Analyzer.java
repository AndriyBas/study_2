package com.oyster;

import java.util.Arrays;
import java.util.Formatter;

/**
 * Created by andriybas on 10/1/14.
 */
public class Analyzer {

    final int K = 2;
    final int M = 100;

    final int readM = M / 4;

    double gradientSpeed = 1.1;

    final double[] t;
    final double minT;
    final double maxT;
    final double step;

    final double[] tMidSegVal;

    final double[] y;
    final double[] tempY;

    double localMin = -1;
    double[] localP;
    double[] localLambda;

    double err;

    public Analyzer(double[] t) {
        if (t == null || t.length == 0) {
            throw new IllegalArgumentException("wtf");
        }

        Arrays.sort(t);

        this.t = t;

        y = new double[M];
        tempY = new double[M];

        minT = t[0];
        maxT = t[t.length - 1];

        step = 1.0 * (maxT - minT) / M;

        tMidSegVal = new double[M];
        for (int i = 0; i < M; i++) {
            tMidSegVal[i] = minT + step * i + step / 2.0;
        }

        int j = 0;
        for (int i = 0; i < M; i++) {
            int lastJ = j;
            while (j < t.length && t[j] < (i + 1.0) * step)
                j++;

            // this is the value of density function in the middle of each segment
            y[i] = 1.0 * (j - lastJ) / t.length / step;
        }

    }

    public void iterate(double[] guessP, double[] guessL) {
        if (guessL == null || guessL == null || guessL.length != K || guessP.length != K) {
            throw new IllegalArgumentException("wtf");
        }

        double[] tempP = guessP;
        double[] tempL = guessL;

        double[] newP = new double[K];
        double[] newL = new double[K];

        boolean broke = false;

        for (int z = 0; z < 1000 && !broke; z++) {

            err = 0.5 / M;

            for (int i = 0; i < M; i++) {
                tempY[i] = 0;
                for (int j = 0; j < K; j++) {
                    tempY[i] += tempP[j] * tempL[j] * Math.exp(-tempL[j] * tMidSegVal[i]);
                }
            }

            for (int i = 0; i < M; i++) {
                err += (tempY[i] - y[i]) * (tempY[i] - y[i]);
            }

            System.out.println(", " + err);

            // calculate new p-s
            newP[K - 1] = 1.0;

            for (int j = 0; j < K - 1; j++) {
                double val = 1.0 / M;
                for (int i = 0; i < M; i++) {
                    val += (tempY[i] - y[i]) * tempL[j] * Math.exp(-tempL[j] * tMidSegVal[i]);
                }

                newP[j] = tempP[j] - gradientSpeed * val;
                newP[K - 1] -= newP[j];

                if (newP[j] > 1.0 || newP[j] < 0.0) {
                    broke = true;
                    break;
                }
            }


            // calculate new lambdas
            for (int j = 0; j < K; j++) {

                double val = 1.0 / M;
                for (int i = 0; i < M; i++) {
                    val += (tempY[i] - y[i]) * tempP[j] * Math.exp(-tempL[j] * tMidSegVal[i]) * (1.0 - tempL[j] * tMidSegVal[i]);
                }

                newL[j] = tempL[j] - gradientSpeed * val;
            }

            System.arraycopy(newP, 0, tempP, 0, K);
            System.arraycopy(newL, 0, tempL, 0, K);

            int fake = 1;
        }

        if (!broke) {
            localP = tempP;
            localLambda = tempL;
        }

    }


}
