package generators;

import event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriybas on 10/15/14.
 */
public class EventGenerator {

    public static List<Event> generateEvents(Generator inputGen, Generator serveGen, int n) {
        List<Event> events = new ArrayList<>();

        double time = 0;

        for (int i = 0; i < n; i++) {
            time += inputGen.generate();
            Event e = new Event(time + time, serveGen.generate());
            events.add(e);
        }

        return events;
    }
}
