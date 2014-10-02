package qms;

import event.Event;
import generators.Generator;
import relevance.Relevance;

import java.util.LinkedList;

/**
 * Created by andriybas on 10/2/14.
 */
public class RR extends AbstractQMS {

    LinkedList<Event> eventQueue;

    double theta = 0.1;

    public RR(Generator inputGen, Generator serveGen, Relevance relevance, double[] k) {
        super(inputGen, serveGen, relevance, k);

        eventQueue = new LinkedList<>();
    }

    @Override
    public void run() {

        double time = 0;

        while (hasMoreTasks() || !eventQueue.isEmpty()) {

            if (eventQueue.isEmpty() && hasMoreTasks()) {
                eventQueue.add(generateEvent(time));
            }

            if (eventQueue.isEmpty()) {
                break;
            }

            Event e = eventQueue.getFirst();
            if (time < e.bornTime) {
                time = e.bornTime;
            }

            if (e.serveTime - e.getPartServedTime() <= theta) {
                double newTime = time + e.serveTime;

                while (hasMoreTasks() && newTime > eventQueue.getLast().getLastServeTime()) {
                    eventQueue.addLast(generateEvent(eventQueue.getLast().bornTime));
                }


                processEvent(e, newTime);
                eventQueue.removeFirst();

                time = newTime;
            } else {
                double newTime = time + theta;

                while (hasMoreTasks() && newTime > eventQueue.getLast().getLastServeTime()) {
                    eventQueue.addLast(generateEvent(eventQueue.getLast().bornTime));
                }


            }
        }


        averageInSystemTime = totalInSystemTime / totalTasks;
        averageReactTime = totalReactTime / totalTasks;
        deviationInSystem = 0;
        for (int i = 0; i < tInSystem.size(); i++) {
            deviationInSystem += (averageInSystemTime - tInSystem.get(i)) * (averageInSystemTime - tInSystem.get(i));
        }
        deviationInSystem /= totalTasks;
    }


    void processEvent(Event e, double time) {
        completedTasks++;
        totalInSystemTime += (time - e.bornTime);
        totalReactTime += (time - e.serveTime - e.bornTime);

        tInSystem.add(time - e.bornTime);
        tReactTime.add(time - e.serveTime - e.bornTime);
        tServeTime.add(e.serveTime);
    }


}

}
