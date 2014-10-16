import java.util.Random;

/**
 * Created by oleh on 18.09.14.
 */
public class PoissonGenerator {

    private double r;
    private double lambda;

    public PoissonGenerator(double l) {
        this.lambda = l;
    }

    public double generate() {
        double t = 0.0;
        Random random = new Random();
            r = random.nextDouble();
            t = (-1.0/lambda)*Math.log(r);
        return t;
    }
}
