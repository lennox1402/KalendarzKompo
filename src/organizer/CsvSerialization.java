package organizer;

import java.io.*;
import java.util.List;

public class CsvSerialization {

    /**
     * Default constructor
     */
    public CsvSerialization() {}

    /**
     * Serialize data from repository to a file
     * @param events list of all events
     * @throws MyException which is custom made exception, throws an exception when an error connected with saving data to file occurs
     */
    public static void serilizeToCSV(List<Event> events) throws MyException {

            String COMMA_DELIMITER = ",";
            String NEW_LINE_SEPARATOR = "\n";
            String FILE_HEADER = "Subject,Start Date,Start Time,End Date,End Time,All Day Event,Description,Location";

            try {
                FileWriter fileWriter = new FileWriter(new File("Events.csv"));
                fileWriter.append(FILE_HEADER);

                for (Event event : events) {
                    String date = Integer.toString(event.getDate().getMonth() + 1) + '/' + Integer.toString(event.getDate().getDate())
                            + '/' + Integer.toString(event.getDate().getYear() + 1900);
                    String time = Integer.toString(event.getDate().getHours()) + ":" + Integer.toString(event.getDate().getMinutes());
                    fileWriter.append(NEW_LINE_SEPARATOR);
                    fileWriter.append(event.getTitle());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(date);
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(time);
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(date);
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(time);
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("false");
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(event.getNote());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(event.getPlace());
                    fileWriter.append(COMMA_DELIMITER);
                }
                fileWriter.flush();
                fileWriter.close();
            }
            catch (IOException e) {
                throw new MyException("Serialization to CSV error", e);
            }
    }
}
