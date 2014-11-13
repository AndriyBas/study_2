package task;

/**
 * Created by andriybas on 10/24/14.
 */
public class Task {

    String name;

    int bornTime;

    int comeTime;

    public Task serveAndPass(int time) {
        this.comeTime = time;
        return this;
    }
}
