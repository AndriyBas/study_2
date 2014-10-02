package qms;

import event.Event;
import generators.Generator;
import relevance.Relevance;

import java.lang.reflect.Array;
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

    int totalTasks = 5000;
    int completedTasks = 0;
    int generatedTasks = 0;

    double totalReactTime = 0.0;
    double totalInSystemTime = 0.0;

    ArrayList<Double> tInSystem;
    ArrayList<Double> tReactTime;
    ArrayList<Double> tServeTime;

    double deviationInSystem;
    double averageInSystemTime;
    double averageReactTime;

    public AbstractQMS(Generator inputGen, Generator serveGen, Relevance relevance, double[] k) {
        this.inputGen = inputGen;
        this.serveGen = serveGen;
        this.relevance = relevance;
        this.k = k;

        tInSystem = new ArrayList<>(totalTasks);
        tReactTime = new ArrayList<>(totalTasks);
    }

    public abstract void run();

    public Event generateEvent(double fromTime) {
        generatedTasks++;
        return new Event(fromTime + inputGen.generate(), serveGen.generate());
    }

    public boolean hasMoreTasks() {
        return generatedTasks < totalTasks;
    }

}
