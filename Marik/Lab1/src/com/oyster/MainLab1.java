package com.oyster;

public class MainLab1 {

    public static void main(String[] args) {

        double[] p = new double[]{0.2, 0.8};

        double[] l = new double[]{0.4, 2};
        int N = 1000000;

        Generator g = new Generator(p, l);


        double[] t = g.generate(N);


        Analyzer an = new Analyzer(t);

        print(an.y);
        System.out.println("step = " + an.step);

        an.iterate(new double[]{0.2, 0.8}, new double[]{0.4, 2});

//        print(t);

        System.out.println("");

    }


    static void print(double[] x) {
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i]);
            System.out.print('\n');
        }
    }


}
