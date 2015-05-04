package qms;

import event.Event;
import generators.Generator;
import relevance.Relevance;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Created by andriybas on 10/2/14.
 */
public class FIFO extends AbstractQMS {

    Deque<Event> eventQueue;

    public FIFO(List<Event> inEvents, Relevance relevance, double[] k) {
        super(inEvents, relevance);
        eventQueue = new ArrayDeque<>(inEvents);
    }

    @Override
    public void run() {

        double time = 0;

        while (!eventQueue.isEmpty()) {

            Event e = eventQueue.getFirst();

            if (time < e.bornTime) {
                time = e.bornTime;
            }

            if (relevance.getRelevance(time - e.bornTime) >= 0.0) {

                double newTime = time + e.serveTime;

//                while (hasMoreTasks() && newTime > eventQueue.getLast().getLastServeTime()) {
//                    eventQueue.addLast(generateEvent(eventQueue.getLast().bornTime));
//                }

                processEvent(e, newTime);
                time = newTime;
            }

            eventQueue.removeFirst();
        }

        calculateValues();
    }

    void processEvent(Event e, double time) {
        completedTasks++;

        e.setCompletedTime(time);
        e.setLastServeTime(time);
        e.setReactTime(time - e.serveTime - e.bornTime);
        e.setWaitTime(time - e.serveTime - e.bornTime);
        e.completeBy(e.serveTime);
        e.setInSystemTime(time - e.bornTime);
    }

}
