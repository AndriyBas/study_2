import generators.ExponentialGen;
import generators.Generator;
import task.RR2;
import task.Task;
import task.TaskGenerator;

import java.util.List;

/**
 * Created by andriybas on 5/4/15.
 */
public class MainRR2 {

    public static void main(String[] args) {

        Generator inGen = new ExponentialGen(0.7);

        Generator serveGen = new ExponentialGen(1.2);

        int n = 5;
        int maxPriority = 32;
        double theta = 0.4;

        List<Task> tasks = TaskGenerator.generateTasks(inGen, serveGen, n, maxPriority);

        RR2 rr = new RR2(tasks, theta);
        rr.run();

        System.out.println("");
    }
}
