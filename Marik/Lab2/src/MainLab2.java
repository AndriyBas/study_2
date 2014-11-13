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

        Generator inGen = new ExponentialGen(1.0);

//        Generator serveGen = new HyperExponentialGen(new double[] {0.3, 0.7}, new double[] {7, 0.4});
//        Generator serveGen = new Regular(1.2);
        Generator serveGen = new ExponentialGen(1.5);

        Relevance relevance = new ConstRelevance();

        double[] k = new double[]{1, 1, 1, 0, 0};

        List<Event> events = EventGenerator.generateEvents(inGen, serveGen, 5000);

        List<Event> eventsCopy = new ArrayList<>(events.size());
        for (Event e : events) {
            eventsCopy.add(new Event(e.bornTime, e.serveTime));
        }

        FIFO fifo = new FIFO(events, relevance, k);

        fifo.run();

        RR rr = new RR(eventsCopy, relevance, k);

        rr.run();

        ArrayDeque<Double> d = new ArrayDeque<>();
        System.out.println("");
    }

//    public double avLenQueue(RR rr) {
//    }
}
