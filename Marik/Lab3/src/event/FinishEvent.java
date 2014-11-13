package event;

import node.Node;
import task.Task;

/**
 * Created by andriybas on 10/24/14.
 */
public class FinishEvent extends Event{

    protected FinishEvent(Node node, long time, Task task) {
        super(node, time, task);
    }

    @Override
    public boolean isFinish() {
        return true;
    }
}
