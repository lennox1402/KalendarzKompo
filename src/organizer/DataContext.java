package organizer;

import java.util.ArrayList;
import java.util.List;

public class DataContext {

    private List<Event> events;

    /**
     * Constructor creating data layer
     */
    public DataContext() {

        this.events = new ArrayList<>();
    }

    /**
     * Getter accessing private list of all events
     * @return List of all events
     */
    public List<Event> getEvents() {

        return events;
    }

    /**
     * Setter for private list of all events
     * @param tmp list containing Event type objects
     */
    public void setEvents(List<Event> tmp) {

        events = tmp;
    }
}
