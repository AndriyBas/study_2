package qms;

import event.Event;
import relevance.Relevance;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by andriybas on 10/2/14.
 */
public class RR extends AbstractQMS {

    ArrayList<Event> eventQueue;

    public double theta = 0.2;

    public RR(List<Event> inEvents, Relevance relevance, double[] k) {
        super(inEvents, relevance, k);
        eventQueue = new ArrayList<>(inEvents);
        clearData();

//        calculateTheta();
    }

    public void calculateTheta() {
        theta = 0.001;
        double dt = 0.01;
        double maxTheta = 10.0;

        double bestTheta = theta;
        double maxFuncVal = Double.MIN_VALUE;

        while (theta < maxTheta) {

            run();

            double funcVal = calculateFunction();
            if (funcVal > maxFuncVal) {
                maxFuncVal = funcVal;
                bestTheta = theta;
            }

            theta += dt;

            clearData();
        }

        theta = bestTheta;
    }

    private void clearData() {
        for (Event e : eventQueue) {
            e.clear();
        }
    }

    @Override
    public void run() {

        double time = 0;

        while (!eventQueue.isEmpty()) {

            Event e = eventQueue.get(0);

            if (time < e.bornTime) {
                time = e.bornTime;
            }

            if (relevance.getRelevance(time - e.bornTime) >= 0.0) {

//                double newTime = time + e.serveTime;

                double needTime = e.serveTime - e.getPartServedTime();
                if (needTime <= theta) {
                    finishEventBy(e, needTime, time);
                    time += needTime;
                } else {
                    processEventBy(e, theta, time);
                    time += theta;

                    // insert at proper position
                    int i = 1;
                    while (i < eventQueue.size() && eventQueue.get(i).getLastServeTime() < time)
                        i++;
                    eventQueue.add(i, e);
                }

            }
            eventQueue.remove(0);
        }

        calculateValues();
    }

    private void finishEventBy(Event e, double byTime, double fromTime) {
        completedTasks++;

        e.setCompletedTime(fromTime + byTime);

        if (e.getReactTime() < 0.0) {
            e.setReactTime(fromTime - e.bornTime);
        }

        e.setWaitTime(e.getWaitTime() + fromTime - e.getLastServeTime());
        e.setInSystemTime(fromTime + byTime - e.bornTime);

        e.setLastServeTime(fromTime + byTime);


    }

    public void processEventBy(Event e, double byTime, double fromTime) {

        if (e.getReactTime() < 0.0) {
            e.setReactTime(fromTime - e.bornTime);
        }

        e.setWaitTime(e.getWaitTime() + fromTime - e.getLastServeTime());

        e.completeBy(byTime);

        e.setLastServeTime(fromTime + byTime);
    }


}
