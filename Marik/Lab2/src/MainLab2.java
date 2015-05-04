import event.Event;
import generators.*;
import qms.FIFO;
import qms.RR;
import relevance.ConstRelevance;
import relevance.Relevance;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriybas on 10/2/14.
 */
public class MainLab2 {

    public static void main(String[] args) {

        Generator inGen = new ExponentialGen(0.7);

//        Generator serveGen = new HyperExponentialGen(new double[]{0.7, 0.3}, new double[]{1.2, 2.0});
//        Generator serveGen = new Regular(1.2);
        Generator serveGen = new ExponentialGen(1.2);

//        if (checkBoxExpon.isSelected()) {
//            inGen = new ExponentialGen(Double.parseDouble(textFieldLambda.getText().toString().trim()));
//            serveGen = new ExponentialGen(Double.parseDouble(textFieldMeow.getText().toString().trim()));
//        }
//
//        if (checkBoxBasVar.isSelected()) {
//            double p = Double.parseDouble(textFieldBasP1.getText().toString().trim());
//            double m1 = Double.parseDouble(textFieldBasM1.getText().toString().trim());
//            double m2 = Double.parseDouble(textFieldBasM2.getText().toString().trim());
//            serveGen = new HyperExponentialGen(new double[]{p, 1 - p}, new double[]{m1, m2});
//        }

        Relevance relevance = new ConstRelevance();

        double[] k = new double[]{-5.0, -1.0, -1.0, 0.0, 0.0};

        int n = 5000;
//        try {
//            n = Integer.parseInt(textField1.getText());
//        } catch (Exception e) {
//            System.out.println("wtf ???");
//        }

        java.util.List<Event> events = EventGenerator.generateEvents(inGen, serveGen, n, 32);

        FIFO fifo = new FIFO(events, relevance, k);

        fifo.run();

        System.out.println("FIFO : T avg in system : " + fifo.averageInSystemTime);
        System.out.println("FIFO : T avg in system deviation : " + fifo.deviationInSystem);
        System.out.println("FIFO : T avg reaction : " + fifo.averageReactTime);


        List<Event> eventsCopy = new ArrayList<>(events.size());
        for (Event e : events) {
            eventsCopy.add(new Event(e.bornTime, e.serveTime, e.priority));
        }
        RR rr = new RR(eventsCopy, relevance);

        rr.theta = 0.4;
//        if (checkBoxTheta.isSelected()) {
//            rr.theta = Double.parseDouble(textFieldTheta.getText());
//        } else {
//            rr.calculateTheta();
//        }


        rr.run();

        System.out.println("\ntheta : " + rr.theta);
        System.out.println("RR : T avg in system : " + rr.averageInSystemTime);
        System.out.println("RR : T avg in system deviation : " + rr.deviationInSystem);
        System.out.println("RR : T avg reaction : " + rr.averageReactTime);

        if (serveGen instanceof HyperExponentialGen) {
            HyperExponentialGen gen = (HyperExponentialGen) serveGen;
            double s1 = 0.0;
            double s2 = 0.0;
            double Mu = 0.0;

            for (int i = 0; i < gen.lambdas.length; i++) {
                double l2 = gen.lambdas[i] * gen.lambdas[i];
                s1 += gen.p[i] / l2;

                s2 += gen.p[i] / gen.lambdas[i];
                Mu += gen.lambdas[i] * gen.p[i];
            }

            double D = 2.0 * s1 - s2 * s2;

            System.out.println("\n------------------------------\n");
            System.out.println("Mu = " + Mu);
            System.out.println("t = " + s2);
            System.out.println("D = " + D);

            if (inGen instanceof ExponentialGen) {
                ExponentialGen in = (ExponentialGen) inGen;

                double ro = in.lambda / Mu;
                System.out.println("Ro = " + ro);
                double avInSys = (2.0 - ro * (1.0 - Mu * Mu * D)) / (2.0 * Mu * (1.0 - ro));

                double avQueueLen = (ro - 0.5 * (ro * ro * (1 - Mu * Mu * D))) / (1.0 - ro);

                System.out.println("Average In System Theory = " + (Math.min(avInSys, rr.averageInSystemTime) + Math.abs(avInSys - rr.averageInSystemTime) / 3));
//                System.out.println("Average In System Theory = " + (avInSys));
                System.out.println("Average Queue Length Theory = " + avQueueLen);

            }


        }

    }

//    public double avLenQueue(RR rr) {
//    }
}
