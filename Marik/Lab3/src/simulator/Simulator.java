package simulator;

import event.Event;
import node.Node;
import task.Task;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by andriybas on 10/24/14.
 */
public class Simulator implements Runnable {

    LinkedList<Event> globalQueue;

    @Override
    public void run() {

    }

    public void postEvent(Event e) {

    }

    public void processEvent(Event e) {

        long time = e.getTime();
        Node node = e.getNode();
        Task t = e.getTask();

        if(e.isFinish()) {
            node.freeResource();

        } else { // isAccept == true

        }
    }
}
