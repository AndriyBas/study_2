package task;

/**
 * Created by andriybas on 5/4/15.
 */
public class Task {

    public static final double eps = 1e-7;

    public final double bornTime;
    public final double serveTime;
    public final int priority;

    private double totalServeTime;
    private double finishTime;
    private double reactTime;

    public Task(double bornTime, double serveTime, int priority) {
        this.bornTime = bornTime;
        this.serveTime = serveTime;
        this.priority = priority;
        clear();
    }

    public void clear() {
        totalServeTime = 0.0;
        finishTime = -1.0;
        reactTime = -1.0;
    }

    public double getTotalServeTime() {
        return totalServeTime;
    }

    public void addTotalServeTime(double newTotalServeTime) {
        this.totalServeTime += newTotalServeTime;
    }

    public boolean isCompleted() {
        return totalServeTime + eps >= serveTime;
    }

    public double getRemainingTime() {
        return serveTime - totalServeTime;
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public double getReactTime() {
        return reactTime;
    }

    public boolean isVirgin() {
        return reactTime < 0.0;
    }

    public void setReactTime(double reactTime) {
        this.reactTime = reactTime;
    }


    public double getWaitTime() {
        return finishTime - bornTime - serveTime;
    }

    public double getInSystemTime() {
        return finishTime - bornTime;
    }

}
