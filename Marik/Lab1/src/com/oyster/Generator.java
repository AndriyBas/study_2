package com.oyster;

import java.util.Random;

/**
 * Created by andriybas on 10/1/14.
 */
public class Generator {

    final Random rand;

    final double[] P;
    final double[] L;
    final double[] sumP;

    public Generator(double[] p, double[] lambda) {
        if (p == null || lambda == null || p.length != lambda.length) {
            throw new IllegalArgumentException("wtf");
        }

        this.P = p;
        this.L = lambda;

        rand = new Random();

        sumP = new double[p.length];
        sumP[0] = p[0];
        for (int i = 1; i < p.length; i++) {
            sumP[i] = sumP[i - 1] + p[i];
        }

        // so that it always stops
        sumP[p.length - 1] += 1.0;
    }


    public double[] generate(int n) {
        double[] x = new double[n];

        for (int i = 0; i < n; i++) {
            double q = rand.nextDouble();
            int j = 0;
            while (q > sumP[j]) j++;

            x[i] = -Math.log(rand.nextDouble()) / L[j];
        }

        return x;
    }
}
