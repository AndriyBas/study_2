package relevance;

/**
 * Created by andriybas on 10/2/14.
 */
public class ConstRelevance implements Relevance {
    @Override
    public double getRelevance(double t) {
        return 1.0;
    }
}
