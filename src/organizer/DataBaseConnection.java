package organizer;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseConnection {

    private static Connection conn;
    private static Statement stat;
    private static String settings = "jdbc:mysql://localhost:3306/calendar?verifyServerCertificate=false&useSSL=true&autoReconnect=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String username = "root";
    private static String password = "kompo1";

    /**
     * Default constructor
     */
    public DataBaseConnection() {}

    /**
     * Converts given date form Date format to String
     * @param date date of an event
     * @return date in String format
     */
    public String convertDateToString(Date date) {

        String tmp = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(tmp);
        String dateToBase = format.format(date);
        return dateToBase;
    }

    /**
     * Converts given String to a Date format
     * @param date date of an event
     * @return date in Date format
     */
    public Date convertStringToDate(String date) {

        String tmp = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(tmp);
        Date dateObj = null;

        try {
            dateObj = format.parse(date);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
        return dateObj;
    }

    /**
     * Saves given data about an event to database
     * @param event an event
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     */
    public void saveToDataBase(Event event) throws SQLException, ClassNotFoundException {

        int id;
        String title, place, note, date;
        char quot = '"';

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(settings, username, password);
        stat = conn.createStatement();

        id = event.getId();
        title = event.getTitle();
        place = event.getPlace();
        note = event.getNote();
        date = this.convertDateToString(event.getDate());
        String query1 = "insert into calendarevents values("+id+","+quot+title+quot+","+quot+place+quot+","+quot+date+quot+","+quot+note+quot+");";
        stat.executeUpdate(query1);

        if(!conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * Deletes data about an event from database
     * @param event an event
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     */
    public void deleteFromDataBase(Event event) throws SQLException, ClassNotFoundException {

        int id;
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(settings, username, password);
        stat = conn.createStatement();

        id = event.getId();
        String query1 = "delete from calendarevents where EVENT_ID = " +id;
        stat.executeUpdate(query1);

        if(!conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * Deletes data about all events from database
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     */
    public void deleteAllFromDatabase() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(settings, username, password);
        stat = conn.createStatement();

        String query1 = "delete from calendarevents";
        stat.executeUpdate(query1);

        if(!conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * Gets data about all events in database
     * @return List of all events from database
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     */
    public List<Event> getFromDataBase() throws ClassNotFoundException, SQLException {

        int id;
        String title, place, note;
        Date date;
        List<Event> tmp = new ArrayList<>();

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(settings, username, password);
        stat = conn.createStatement();
        String query = "select * from calendarevents";
        ResultSet rs = stat.executeQuery(query);

        while(rs.next()) {
            id = Integer.parseInt(rs.getString(1));
            title = rs.getString(2);
            place = rs.getString(3);
            note = rs.getString(5);
            date = this.convertStringToDate(rs.getString(4));
            Event event = new Event(title, place, date, note);
            event.setId(id);
            tmp.add(event);
        }

        if(!conn.isClosed()) {
            conn.close();
        }
        return tmp;
    }

    /**
     * Updates data of an event in database
     * @param event an event
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     */
    public void updateInDataBase(Event event) throws ClassNotFoundException, SQLException {

        int id;
        String title, place, note;
        char quot = '"';

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(settings, username, password);
        stat = conn.createStatement();

        id = event.getId();
        title = event.getTitle();
        place = event.getPlace();
        note = event.getNote();
        String query2 = "update calendarevents set TITLE = " +quot+title+quot+ ", PLACE = " +quot+place+quot+ ", NOTE = " +quot+note+quot+ "where EVENT_ID = " +id;
        stat.executeUpdate(query2);

        if(!conn.isClosed()) {
            conn.close();
        }
    }
}

