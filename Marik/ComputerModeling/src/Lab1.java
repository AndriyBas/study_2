import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by oleh on 28.09.14.
 */
public class Lab1 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        int N = 5000;
//        HyperexponentialGenerator generator = new HyperexponentialGenerator(2,N);
//        List<Double> res = generator.generate();
//       /* double[] res = new double[1000];
//
//        PoissonGenerator g = new PoissonGenerator(100);
//        for (int i = 0; i < 1000; i++) {
//            res[i] = g.generate();
//        }*/
//        PrintWriter writer = new PrintWriter("lab1.txt");
//        for (int i = 0; i < res.size(); i++) {
//            writer.println(String.format("%.9f", res.get(i)));
//        }
//        writer.close();

        Analysis analysis = new Analysis(N);


        List<Double> l = analysis.readFile();
        List<Double> c = analysis.analyze(l);

        double k0 = c.get(0);
        int k = (int) k0;
        for (int i = 0; i < k; i++) {
            System.out.println(String.format("L : %.3f --  P : %.3f", c.get(2 * i + 1), c.get(2 * i + 2)));
        }


        List<Double> arr = analysis.readFile2();
        Collections.sort(l);
        int a = N / 4;
        double res = 0.0;
        double av1 = 0.0;
        double av2 = 0.0;
        double d1 = 0.0;
        double d2 = 0.0;

        for (int i = 0; i < N - a; i++) {
            av2 += arr.get(i);
        }
        av2 = av2 / (N - a);

        for (int i = a; i < N; i++) {
            av1 += arr.get(i);
        }
        av1 = av1 / (N - a);

        for (int i = a; i < N; i++) {
            res += (arr.get(i) - av1) * (arr.get(i - a) - av2);
            d1 += Math.pow(arr.get(i) - av1, 2.0);
            d2 += Math.pow(arr.get(i - a) - av2, 2.0);
        }

        res = res / ( Math.sqrt(d1 * d2));

        System.out.println(-res);

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
