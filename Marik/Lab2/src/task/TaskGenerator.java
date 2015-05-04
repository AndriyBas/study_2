package task;

import event.Event;
import generators.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andriybas on 5/4/15.
 */
public class TaskGenerator {

    public static List<Task> generateTasks(Generator inputGen, Generator serveGen, int n, int maxPriority) {
        List<Task> tasks = new ArrayList<>();

        double time = 0;
        final Random rand = new Random();

        for (int i = 0; i < n; i++) {
            time += inputGen.generate();
            Task task = new Task(time, serveGen.generate(), 1 + rand.nextInt(maxPriority));
            tasks.add(task);
        }

        return tasks;
    }

}
