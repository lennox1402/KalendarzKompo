package organizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Repository {

    private DataContext data;

    /**
     * Constructor of an Repository which contains all data
     */
    public Repository() {

        this.data = new DataContext();
    }

    /**
     * Adding an event to data layer
     * @param event an event
     * @return true if action of adding an event was a success, otherwise false
     */
    public boolean addEvent(Event event) {

        if(!data.getEvents().contains(event)) {
            event.setId(setId());
            data.getEvents().add(event);
            return true;
        }
        return false;
    }

    /**
     * Deleting an event from data layer
     * @param event an event
     * @return true if action of deleting an event was a success, otherwise false
     */
    public boolean deleteEvent(Event event) {

        if(data.getEvents().contains(event)) {
            data.getEvents().remove(event);
            return true;
        }
        return false;
    }

    /**
     * Deleting events older than date given as a parameter
     * @param date date of an event
     * @return List of events
     */
    public List<Event> deleteOldEvents(Date date) {

		List<Event> tmp = new ArrayList<>();
		for(int i = 0; i < data.getEvents().size(); i++) {
			if(data.getEvents().get(i).getDate().before(date)) {
				tmp.add(data.getEvents().get(i));
			}
		}
		for(Event e : tmp) {
			deleteEvent(e);
		}
		return tmp;
    }

    /**
     * Setting id of an event
     * @return Integer value of an event id
     */
    public int setId() {

        int i;
        if(data.getEvents().size() == 0) {
            i = 0;
            return i;
        }
        else {
            i = data.getEvents().get(data.getEvents().size() - 1).getId() + 1;
            return i;
        }
    }

    /**
     * Getting an event after given id
     * @param index index of an event in events list
     * @return an event
     */
    public Event getEvent(int index) {

        return data.getEvents().get(index);
    }

    /**
     * Getting list of all events in data layer
     * @return List of all events
     */
    public List<Event> getAllEvents() {

        return data.getEvents();
    }

    /**
     * Setting list of all events in data layer
     * @param tmp list of events
     */
    public void setAllEvents(List<Event> tmp) {

        data.setEvents(tmp);
    }

    /**
     * Getting list of events for which dates are between given start date and end date
     * @param start start date for period od time
     * @param end end date for period of time
     * @return List of events
     */
    public List<Event> getEventsBetweenDates(Date start, Date end) {

        List<Event> tmp = new ArrayList<>();
        for(int i = 0; i < data.getEvents().size(); i++) {
            if(data.getEvents().get(i).getDate().after(start) && data.getEvents().get(i).getDate().before(end)) {
                tmp.add(data.getEvents().get(i));
            }
        }
        return tmp;
    }

    /**
     * Changing title of an event
     * @param event an event
     * @param newTitle title of an event
     */
    public void changeTitleOfEvent(Event event, String newTitle) {

        event.setTitle(newTitle);
    }

    /**
     * Changing place of an event
     * @param event an event
     * @param newPlace place of an event
     */
    public void changePlaceOfEvent(Event event, String newPlace) {

        event.setPlace(newPlace);
    }

    /**
     * Changing note of an event
     * @param event an event
     * @param newNote note of an event
     */
    public void changeNoteOfEvent(Event event, String newNote) {

        event.setNote(newNote);
    }
}
