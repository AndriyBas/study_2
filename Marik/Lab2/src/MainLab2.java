import generators.ExponentialGen;
import generators.Generator;
import generators.HyperExponentialGen;
import generators.Regular;
import qms.FIFO;
import relevance.ConstRelevance;
import relevance.Relevance;

/**
 * Created by andriybas on 10/2/14.
 */
public class MainLab2 {

    public static void main(String[] args) {

        Generator inGen = new ExponentialGen(1.5);

//        Generator serveGen = new HyperExponentialGen(new double[] {0.3, 0.7}, new double[] {7, 0.4});
//        Generator serveGen = new Regular(1.2);
        Generator serveGen = new ExponentialGen(1.0);

        Relevance relevance = new ConstRelevance();

        double[] k = new double[]{1, 1, 1, 0, 0};

        FIFO fifo = new FIFO(inGen, serveGen, relevance, k);

        fifo.run();

        System.out.println("");
    }
}
