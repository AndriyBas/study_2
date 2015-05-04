package generators;

/**
 * Created by andriybas on 10/2/14.
 */
public interface Generator {

    double generate();

    // to work with Java 7 remove this method
    default double[] generateSeq(int n) {
        double[] x = new double[n];

        for (int i = 0; i < n; i++) {
            x[i] = generate();
        }

        return x;
    }

}
