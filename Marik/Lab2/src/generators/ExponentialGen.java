package generators;

import java.util.Random;

/**
 * Created by andriybas on 10/2/14.
 */
public class ExponentialGen implements Generator {

    final double lambda;

    private final Random rand;

    public ExponentialGen(double lambda) {
        this.lambda = lambda;
        rand = new Random();
    }

    @Override
    public double generate() {
        return -Math.log(rand.nextDouble()) / lambda;
    }
}
