package task;

import java.util.List;
import java.util.Stack;

/**
 * Created by andriybas on 5/4/15.
 */
public class RR2 implements Runnable {

    public final List<Task> tasks;
    public final double theta;

    public RR2(List<Task> tasks, double theta) {
        this.tasks = tasks;
        this.theta = theta;
    }

    private Stack<TimeEvent> events;
    private Stack<Task> taskQueue;
    private Task currentTask;
    private double currentTaskEnterTime;
    private double systemTime;

    @Override
    public void run() {

        events = new Stack<>();
        taskQueue = new Stack<>();

        for (int i = tasks.size() - 1; i >= 0; i--) {
            events.push(new TimeEvent(tasks.get(i).bornTime, tasks.get(i), EventType.ENTER));
        }

        systemTime = 0.0;
        currentTaskEnterTime = -1.0;
        while (!events.isEmpty()) {

            TimeEvent e = events.pop();
            systemTime = e.time;
            Task task = e.task;

            if(e.type == EventType.ENTER) {
                addToQueue(task);
                if(taskQueue.peek() == task && highestPriority(task)) {
                    removeCurrentTaskEvent();
                    processCurrentTask(systemTime);
                }
            } else {
                processCurrentTask(systemTime);
            }
        }
    }

    private void removeCurrentTaskEvent() {
        if(currentTask == null)
            return;

        for(int i = events.size() - 1; i >= 0; i--) {
            if(events.get(i).task == currentTask) {
                events.remove(i);
                return;
            }
        }
    }

    private boolean highestPriority(Task task) {
        return currentTask == null || currentTask.priority < task.priority;
    }

    private void processCurrentTask(double time) {
        if(currentTask != null) {

            // time task was on the processor
            double processTime = time - currentTaskEnterTime;
            if (currentTask.isVirgin()) {
                currentTask.setReactTime(currentTaskEnterTime);
            }

            // add time task was on the processor
            currentTask.addTotalServeTime(processTime);

            if (currentTask.isCompleted()) { // if completed - ignore it
                currentTask.setFinishTime(time);
            } else { // else add back to queue
                addToQueue(currentTask);
            }

        }

        if(!taskQueue.isEmpty()) {
            currentTask = taskQueue.pop();
            currentTaskEnterTime = time;

            double needTime = Math.min(theta, currentTask.getRemainingTime());
            TimeEvent timeEvent = new TimeEvent(time + needTime, currentTask, EventType.FINISH);
            addEvent(timeEvent);
        } else {
            currentTask = null;
            currentTaskEnterTime = -1.0;
        }

    }

    private void addEvent(TimeEvent event) {
        int i = 0;
        for(; i < events.size() && events.get(i).time > event.time; i++);
        events.add(i, event);
    }

    private void addToQueue(Task task) {
        int i = 0;
        for(;i < taskQueue.size() && task.priority > taskQueue.get(i).priority; i++);
        taskQueue.add(i, task);
    }

    public double getSystemTime() {
        return systemTime;
    }



}
