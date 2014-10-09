package qms;

import event.Event;
import generators.Generator;
import relevance.Relevance;

import java.util.ArrayList;

/**
 * Queue management system
 * (системи масового обслуговування)
 * <p>
 * Created by andriybas on 10/2/14.
 */
public abstract class AbstractQMS {

    final Generator inputGen;
    final Generator serveGen;
    final Relevance relevance;

    final double[] k;

    final int N = 5;
    int completedTasks = 0;
    int generatedTasks = 0;


    final ArrayList<Event> inEvents;

    double totalReactTime;
    double totalInSystemTime;
    double totalServeTime;
    double totalWaitTime;

    double deviationInSystem;

    double averageServeTime;
    double averageInSystemTime;
    double averageReactTime;
    double averageWaitTime;

    public AbstractQMS(Generator inputGen, Generator serveGen, Relevance relevance, double[] k) {
        this.inputGen = inputGen;
        this.serveGen = serveGen;
        this.relevance = relevance;
        this.k = k;
        this.inEvents = new ArrayList<>(N);
    }

    public abstract void run();

    public Event generateEvent(double fromTime) {
        generatedTasks++;
        Event e = new Event(fromTime + inputGen.generate(), serveGen.generate());
        inEvents.add(e);
        return e;
    }

    public boolean hasMoreTasks() {
        return generatedTasks < N;
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

        averageInSystemTime = totalInSystemTime / N;
        averageReactTime = totalReactTime / N;
        averageWaitTime = totalWaitTime / N;
        averageServeTime = totalServeTime / N;

        deviationInSystem = 0;

        for (Event e : inEvents) {
            deviationInSystem += (averageInSystemTime - e.getInSystemTime()) * (averageInSystemTime - e.getInSystemTime());
        }

        deviationInSystem /= N;
    }

}
