package generators;

import event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andriybas on 10/15/14.
 */
public class EventGenerator {

    public static List<Event> generateEvents(Generator inputGen, Generator serveGen, int n, int maxPriority) {
        List<Event> events = new ArrayList<>();

        double time = 0;
        final Random rand = new Random();

        for (int i = 0; i < n; i++) {
            time += inputGen.generate();
            Event e = new Event(time, serveGen.generate(), 1 + rand.nextInt(maxPriority));
            events.add(e);
        }

        return events;
    }
}
