package relevance;

/**
 * Created by andriybas on 10/2/14.
 */
public class DefaultRelevance implements Relevance {

    final double t1;
    final double t2;

    public DefaultRelevance(double t1, double t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public double getRelevance(double t) {

        if (t <= t1) {
            return 1.0;
        }

        if (t < t2) {
            return (t2 - t) / (t2 - t1);
        }

        return 0.0;
    }
}
