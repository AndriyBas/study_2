package qms;

import event.Event;
import generators.Generator;
import relevance.Relevance;

import java.util.ArrayList;
import java.util.List;

/**
 * Queue management system
 * (системи масового обслуговування)
 * <p>
 * Created by andriybas on 10/2/14.
 */
public abstract class AbstractQMS {

    public final Relevance relevance;

    public final double[] k;

    public final int N;
    public int completedTasks = 0;

    public final List<Event> inEvents;

    public double totalReactTime;
    public double totalInSystemTime;
    public double totalServeTime;
    public double totalWaitTime;

    public double deviationInSystem;

    public double averageServeTime;
    public double averageInSystemTime;
    public double averageReactTime;
    public double averageWaitTime;

    public AbstractQMS(List<Event> inEvents, Relevance relevance, double[] k) {
        this.relevance = relevance;
        this.k = k;
        this.inEvents = inEvents;
        this.N = inEvents.size();
    }

    public abstract void run();

//    public Event generateEvent(double fromTime) {
//        generatedTasks++;
//        Event e = new Event(fromTime + inputGen.generate(), serveGen.generate());
//        inEvents.add(e);
//        return e;
//    }

//    public boolean hasMoreTasks() {
//        return generatedTasks < N;
//    }

    public double calculateFunction() {
        double res = 0.0;
        res += k[0] * averageInSystemTime;
        res += k[1] * deviationInSystem;
        res += k[2] * averageReactTime;
        res += k[3] * completedTasks / N;
        // TODO : K[4] - last coefficient is not calculated

        return res;
    }

    public void calculateValues() {

        totalInSystemTime = totalReactTime = totalServeTime = totalWaitTime = 0.0;
        for (Event e : inEvents) {
            if (e.isCompleted()) {
                totalInSystemTime += e.getInSystemTime();
                totalReactTime += e.getReactTime();
                totalWaitTime += e.getWaitTime();
                totalServeTime += e.serveTime;
            }
        }

        averageInSystemTime = totalInSystemTime / completedTasks;
        averageReactTime = totalReactTime / completedTasks;
        averageWaitTime = totalWaitTime / completedTasks;
        averageServeTime = totalServeTime / completedTasks;

        deviationInSystem = 0;

        for (Event e : inEvents) {
            deviationInSystem += (averageInSystemTime - e.getInSystemTime()) * (averageInSystemTime - e.getInSystemTime());
        }

        deviationInSystem /= (completedTasks - 1.0);
    }

    public List<Event> getInEvents() {
        return inEvents;
    }

}
