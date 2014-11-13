package event;

import node.Node;
import task.Task;

/**
 * Created by andriybas on 10/24/14.
 */
public class AcceptEvent extends Event {

    protected AcceptEvent(Node node, long time, Task task) {
        super(node, time, task);
    }

    @Override
    public boolean isAccept() {
        return true;
    }
}
