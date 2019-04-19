package organizer;

import java.util.Date;

public class Event {

    private int id;
    private String title;
    private String place;
    private String note;
    private Date date;

    /**
     * Constructor creating an event
     * @param title title of an event
     * @param place place of an event
     * @param date date of an event
     * @param note note for an event
     */
    public Event(String title, String place, Date date, String note) {

        this.title = title;
        this.place = place;
        this.note = note;
        this.date = date;
    }

    /**
     * Copy constructor of an event
     * @param otherEvent an event
     */
    public Event(Event otherEvent)
    {
        this.id = otherEvent.id;
        this.title = otherEvent.title;
        this.place = otherEvent.place;
        this.note = otherEvent.note;
        this.date = otherEvent.date;
    }

    /**
     * Getter which accesses private field of an event id
     * @return Integer value of an id
     */
    public int getId() {

        return id;
    }

    /**
     * Setter for private field of an event id
     * @param id id of an event
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * Getter which accesses private field of an event title
     * @return String value of an event title
     */
    public String getTitle() {

        return title;
    }

    /**
     * Setter for private field of an event title
     * @param newTitle title of an event
     */
    public void setTitle(String newTitle) {

        this.title = newTitle;
    }

    /**
     * Getter which accesses private field of an event place
     * @return String value of an event place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Setter for private field of an event place
     * @param place of an event
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Getter which accesses private field of an event note
     * @return String value of an event note
     */
    public String getNote() {

        return note;
    }

    /**
     * Setter for private field of an event note
     * @param newNote note for an event
     */
    public void setNote(String newNote) {

        this.note = newNote;
    }

    /**
     * Getter which accesses private field of an event date
     * @return Date format value of an event
     */
    public Date getDate() {

        return date;
    }

    /**
     * Setter for private field of an event date
     * @param newDate date of an event
     */
    public void setDate(Date newDate) {

        this.date = newDate;
    }

    /**
     * Override method which compares two objects
     * @param obj an object
     * @return true if compared objects equals, otherwise false
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (id != other.id)
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (place == null) {
            if (other.place != null)
                return false;
        } else if (!place.equals(other.place))
            return false;
        return true;
    }

    /**
     * Override method creating a String value
     * @return String value of an event description
     */
    @Override
    public String toString() {

        return "TITLE:  '" + title + "',  DATE:  " + date + ",  PLACE:  " + place + ",  NOTE:  '" + note + "'" + "\n";
    }
}

