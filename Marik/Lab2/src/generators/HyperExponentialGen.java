package generators;

import java.util.Random;

/**
 * Created by andriybas on 10/2/14.
 */
public class HyperExponentialGen implements Generator {

    private final Random rand;

    public final double[] p;
    public final double[] lambdas;
    public final int k;

    private final double[] sumP;


    public HyperExponentialGen(double[] p, double[] lambdas) {

        if (p == null || lambdas == null || p.length == 0 || p.length != lambdas.length) {
            throw new IllegalArgumentException("WTF");
        }

        this.p = p;
        this.lambdas = lambdas;
        this.k = p.length;

        sumP = new double[k];
        sumP[0] = p[0];

        for (int i = 1; i < k; i++) {
            sumP[i] = sumP[i - 1] + p[i];
        }
        // just in case, for precision
        sumP[k - 1] += 1;

        rand = new Random();
    }


    @Override
    public double generate() {
        // take random number from [0, 1]
        double r = rand.nextDouble();

        // choose generator
        int genNum = 0;
        while (r > sumP[genNum]) genNum++;

        return -Math.log(rand.nextDouble()) / lambdas[genNum];
    }
}
