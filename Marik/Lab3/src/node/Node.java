package node;

import simulator.Simulator;
import task.Task;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * Created by andriybas on 10/24/14.
 */
public class Node {

    long totalWorkTime = 0L;
    int taskCount = 0;

    Queue<Task> waitQueue = new ArrayDeque<>();
    int resources = 1;

    List<Node> outConnections;
    List<Double> probabilities;


    public void executeIfPending() {
        while (!waitQueue.isEmpty() && hasResources()) ;
    }

    public boolean hasResources() {
        return resources > 0;
    }

    public void freeResource() {
        resources++;
    }

    public void acquireResource() {
        assert resources > 0;
        resources--;
    }


}
