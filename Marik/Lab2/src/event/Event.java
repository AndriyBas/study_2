package event;

/**
 * Created by andriybas on 10/2/14.
 */
public class Event {

    public final double bornTime;
    public final double serveTime;
    public int priority;



    private double lastServeTime;
    private double partServedTime;

    private double completedTime;

    private double reactTime;

    private double waitTime;
    private double inSystemTime;

    public Event(double bornTime, double serveTime, int priority) {
        this.bornTime = bornTime;
        this.serveTime = serveTime;
        this.priority = priority;
        clear();
    }

    public boolean isCompleted() {
        return completedTime > 0.0;
    }

    public double getPartServedTime() {
        return partServedTime;
    }

    public void completeBy(double t) {
        this.partServedTime += t;
    }

    public double getLastServeTime() {
        return lastServeTime;
    }

    public void setLastServeTime(double lastServeTime) {
        this.lastServeTime = lastServeTime;
    }

    public double getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(double completedTime) {
        this.completedTime = completedTime;
    }

    public double getReactTime() {
        return reactTime;
    }

    public void setReactTime(double reactTime) {
        this.reactTime = reactTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public double getInSystemTime() {
        return inSystemTime;
    }

    public void setInSystemTime(double inSystemTime) {
        this.inSystemTime = inSystemTime;
    }

    public void clear() {
        this.partServedTime = 0.0;
        this.lastServeTime = bornTime;
        completedTime = -1.0;
        reactTime = -1.0;
        inSystemTime = 0.0;
        waitTime = 0.0;
    }
}
