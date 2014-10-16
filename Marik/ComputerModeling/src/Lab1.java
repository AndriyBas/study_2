import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleh on 28.09.14.
 */
public class Lab1 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        HyperexponentialGenerator generator = new HyperexponentialGenerator(3,5000);
        List<Double> res = generator.generate();
       /* double[] res = new double[1000];

        PoissonGenerator g = new PoissonGenerator(100);
        for (int i = 0; i < 1000; i++) {
            res[i] = g.generate();
        }*/
        System.out.println(res.get(5000/2) + " " + res.get(5000/2 + 1));
        PrintWriter writer = new PrintWriter("lab1.txt");
        for (int i = 0; i < res.size(); i++) {
            writer.println(res.get(i));
        }
        writer.close();

        List<Double> l = Analysis.readFile();

        /*Analysis analysis = new Analysis(12,res);
        //analysis.buildHistogram();
        int[] ints = analysis.getNumbersOfElementsInIntervals();
        analysis.monteCarloGeneration(3);

        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i] + " ");
        }

        double[] m = analysis.getMiddleOfInterval();
        for (int i = 0; i < m.length; i++) {
           // analysis.distributionFunction(m[i]);
        }
        double step = analysis.getStep();
        System.out.println( "step" + step);
        System.out.println("mid");
        Analysis.print(m);*/
    }
}
