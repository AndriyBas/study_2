package generators;

/**
 * Created by andriybas on 10/2/14.
 */
public class Regular implements Generator {

    final double lambda;

    public Regular(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double generate() {
        return 1.0 / lambda;
    }
}
