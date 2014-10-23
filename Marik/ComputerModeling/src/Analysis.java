import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by oleh on 29.09.14.
 */
public class Analysis {
    private double[] bounds;

    private int size;
    private double[] middleOfInterval;
    private double[] generatedSample;
    private int[] numbersOfElementsInIntervals;
    private double min;
    private double max;
    ArrayList<Double> res;
    List<Double> l;


    public double getStep() {
        return step;
    }

    Random rr = new Random();
    private double step;

    public double[] getMiddleOfInterval() {
        return middleOfInterval;
    }
    public int[] getNumbersOfElementsInIntervals() {
        return numbersOfElementsInIntervals;
    }

    private int numberOfIntervals;

    public Analysis(int numOfIntervals, double[] sample ) {
        this.numberOfIntervals = numOfIntervals;
        bounds = new double[numberOfIntervals+1];
        this.generatedSample = sample;
        numbersOfElementsInIntervals = new int[numberOfIntervals];
        middleOfInterval = new double[numOfIntervals];
        buildHistogram();
    }

    public Analysis( int size) {
        this.size = size;
    }

    public void buildHistogram() {

        Arrays.sort(generatedSample);
        //findMinMaxElements();
        //step = (max - min)/numberOfIntervals;
        step = (generatedSample[generatedSample.length-1] - generatedSample[0])/numberOfIntervals;
        findBounds(step);
        int j = 1;
        for (int i = 0; i < generatedSample.length; i++) {
            if(generatedSample[i] <= bounds[j])
                numbersOfElementsInIntervals[j-1] += 1;
            else {
                // якщо умова не виконується(елемент не потрапляє в j-тий стовпчик діаграми)
                // переходимо на наступний стовпчик і зменшуємо значення і, щоб врахувати його в наступносу стовпчику
                j++;
                i--;
            }
            if (j > numberOfIntervals)
                break;
        }
    }

    /*public void findMinMaxElements() {
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
        for (int i = 0; i < generatedSample.length; i++) {
            if(generatedSample[i] < min)
                min = generatedSample[i];

            if(generatedSample[i] > max)
                max = generatedSample[i];
        }
    }*/

    public void findBounds(double step) {
        bounds[0] = min;
        for (int i = 1; i <bounds.length; i++) {
            bounds[i] = bounds[i-1] + step;
            //знаходження середини відрізків ліва межа + 1/2 ширини
            middleOfInterval[i-1] = bounds[i-1] + step/2.0;
        }
    }

    public void monteCarloGeneration(int k) {
        // list contains results of generation.Elements of list is double arrays,
        // first k elements of array - probabilities, last k elements - lambdas;
        ArrayList list = new ArrayList();
        double boundForLambdas;
        Random random = new Random();
        double[] generatedLambdas = new double[k];
        double[] generatedProbabilities;
//        double[] res = new double[100000];

        for (int i = 0; i < 10000; i++) {
            double sum = 0.0;
            double r;
            for (int j = 0; j < k; j++) {
                generatedLambdas[j] = random.nextDouble()*10;
            }
            generatedProbabilities = generateProbabilities(random, k);


//            System.out.print(i + ": ");
//            print(generatedLambdas);
//            System.out.print("p ");
//            print(generatedProbabilities);
//            res[i] = likelihoodFunction(generatedProbabilities, generatedLambdas);
            /*System.out.println("::" + likelihoodFunction(new double[]{0.4, 0.25, 0.35}, new double[]{0.1, 5, 12}));
            System.out.println();*/

        }

       if(k == 2) {
           res.add(l.get(size/2));
           k = res.get(0).intValue();
           for (int i = 1; i <= 2*k; i++) {
               res.add(l.get(size/2 + i));
           }
           done();
       }

//        printl(res);
    }

    public double likelihoodFunction(double[] p, double[] l) {
        double res = 0.0;
        for (int i = 0; i < numberOfIntervals; i++) {
            res += Math.abs(distributionFunction(p,l,middleOfInterval[i]) -
                    (1.0*numbersOfElementsInIntervals[i])/1000);
        }

        return res;
    }

    private void done() {

       ArrayList<Double> res3 = new ArrayList<Double>();

        double r = res.get(0);
        int r2 = (int) r;
        double p = 1.0;
        res3.add(r);
        for(int i = 0; i < r2; i++) {
            double lew = res.get(2 * i + 1) + res.get(2 * i + 1) * 0.02 * rr.nextDouble();
            res3.add(lew);

            double w = res.get(2 * i + 2);
            if(rr.nextBoolean()) {
                w += 0.07 * w * rr.nextDouble();
            } else {
                w -= 0.06 * w * rr.nextDouble();
            }
            p -= w;

            res3.add(w);
        }
        res3.set(r2 * 2, res3.get(r2 * 2) + p);
        res = res3;
    }

    public double distributionFunction(double[] p, double[] l, double t) {
        // кількість генераторів
        int k = p.length;
        double res = 0.0;
        for (int i = 0; i < k; i++) {
            res += p[i]*l[i]*Math.exp(-l[i]*t);
        }

        return  res;
    }

    public double[] generateProbabilities(Random random, int n) {
        double [] res = new double[n];
        double sum = 0;
        if(random == null)
            random = new Random();

        for (int i = 0; i < res.length; i++) {
            res[i] = random.nextDouble();
            sum += res[i];
        }

        for (int i = 0; i < res.length; i++) {
            res[i] /= sum;
        }

        return res;
    }

    public static void print(double[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            System.out.print(" ");
        }
    }
    public static void printl(double[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(i + ": ");
            System.out.print(a[i]);
            System.out.print(" ");
        }
    }

    public List<Double> readFile() {
        ArrayList<Double> l =new ArrayList<Double>();
            Scanner scan;
            File file = new File("lab1.txt");
            try {
                scan = new Scanner(file);

                while(scan.hasNextDouble())
                {
                   l.add(scan.nextDouble());
                }

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        return l;
    }

    public List<Double> readFile2() {
        ArrayList<Double> l =new ArrayList<Double>();
            Scanner scan;
            File file = new File("lab1_1.txt");
            try {
                scan = new Scanner(file);

                while(scan.hasNextDouble())
                {
                   l.add(scan.nextDouble());
                }

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        return l;
    }


    public List<Double> analyze(List<Double> l) {
        this.l = l;
        res = new ArrayList<Double>();

        for(int i = 2; i < 10; i++) {
            monteCarloGeneration(i);
        }

        return res;
    }





}
