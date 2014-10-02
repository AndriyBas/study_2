package event;

/**
 * Created by andriybas on 10/2/14.
 */
public class Event {

    public final double bornTime;
    public final double serveTime;

    private double lastServeTime;
    private double partServedTime;

    public Event(double bornTime, double serveTime) {
        this.bornTime = bornTime;
        this.serveTime = serveTime;
        this.partServedTime = 0.0;
        this.lastServeTime = bornTime;
    }

    public boolean isCompleted() {
        return Double.compare(partServedTime, serveTime) >= 0;
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

}
