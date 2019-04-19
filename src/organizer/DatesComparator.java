package organizer;

import java.util.Comparator;

public class DatesComparator implements Comparator<Event> {

    /**
     * Compares dates of two objects
     * @param event1 one of events
     * @param event2 another event
     * @return positive value if compared value for an object compared to is greater, 0 if values equals, otherwise negative value
     */
    @Override
    public int compare(Event event1, Event event2) {

        return event1.getDate().compareTo(event2.getDate());
    }
}
