import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by oleh on 25.09.14.
 */
public class HyperexponentialGenerator {

    private int k;
    private int numberOfElements;
    private double[] probabilities;
    private double[] p;
    private double[] lambdas;
    private List<Double> result = new ArrayList<Double>();;
    private PoissonGenerator[] generators;

    public HyperexponentialGenerator(int k, int n) {
        this.k = k;
        this.numberOfElements = n;
        probabilities = new double[k+1];
        p = new double[k];
        lambdas = new double[k];
        generators = new PoissonGenerator[k];

        inputParameters();

        for (int i = 0; i < k; i++) {
            generators[i] = new PoissonGenerator(lambdas[i]);
        }

    }

    public void inputParameters() {
        double sum = 0.0;
        Scanner scan = new Scanner(System.in);
//        System.out.println("ВВедіть величину вибірки");
//        numberOfElements = scan.nextInt();
//        System.out.println("ВВедіть кількість генераторів");
//        k = scan.nextInt();
        System.out.println("ВВедіть інтенсивності потоків та ймовірності їх застосування");
        probabilities [0] = 0.0;
        double temp = 0.0;
        for (int i = 0; i < k; i++) {
            System.out.print(i + " генератор:\nінтенсивніть = ");
            lambdas[i] = scan.nextDouble();
            System.out.println("ймовірність роботи = ");
            // в масив записуються інтервали, що відповідають ймовірностям роботи герераторів [0,1]
            temp = scan.nextDouble();
            p[i] = temp;
            sum += temp;
            probabilities[i+1] = sum;
        }
        scan.close();
    }

    public List<Double> generate() {
        double r = 0.0;
        for (int i = 0; i < numberOfElements; i++) {
            if(i == numberOfElements/2 )
                add();
            Random random = new Random();
            r = random.nextDouble();
//            System.out.println(i + " random value " + r);
            for (int j = 0; j < k; j++) {
                if (r > probabilities[j]&& r < probabilities[j+1]) {
                    result.add(generators[j].generate());
                    System.out.println("generator " + j + " value: " + result.get(i));
                }
            }
        }

        return result;
    }

    private void add() {
        result.add(new Double(k));
        for (int i = 0; i < k; i++) {
            result.add(lambdas[i]);
            result.add(p[i]);
        }
    }

}
