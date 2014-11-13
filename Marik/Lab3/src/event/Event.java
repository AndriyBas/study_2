package event;

import node.Node;
import task.Task;

/**
 * Created by andriybas on 10/24/14.
 */
abstract public class Event {

    Node node;
    long time;
    Task task;

    protected Event(Node node, long time, Task task) {
        this.node = node;
        this.time = time;
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public long getTime() {
        return time;
    }

    public Node getNode() {
        return node;
    }

    public boolean isFinish() {
        return false;
    }

    public boolean isAccept() {
        return false;
    }

}
