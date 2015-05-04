package task;

/**
 * Created by andriybas on 5/4/15.
 */
public class TimeEvent {

    public final double time;
    public final Task task;
    public final EventType type;


    public TimeEvent(double time, Task task, EventType type) {
        this.time = time;
        this.task = task;
        this.type = type;
    }
}
