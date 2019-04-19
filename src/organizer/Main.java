package organizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String MAIN_CALENDAR_FXML = "/fxml/MainCalendar.fxml";
    public static final String ADD_EVENT_FXML = "/fxml/AddEvent.fxml";
    public static final String DELETE_EVENT_FXML = "/fxml/DeleteEvent.fxml";
    public static final String DELETE_OLDER_EVENTS_FXML = "/fxml/DeleteOlderEvents.fxml";
    public static final String EDIT_EVENT_FXML = "/fxml/EditEvent.fxml";
    public static final String EDIT_EVENT2_FXML = "/fxml/EditEvent2.fxml";
    public static final String EVENTS_BETWEEN_DATES_FXML = "/fxml/EventsBetweenDates.fxml";
    public static final String ALL_EVENTS_FOR_DAY_FXML = "/fxml/AllEventsForDay.fxml";
    public static final String DESERIALIZED_EVENTS_FXML = "/fxml/DeserializedEvents.fxml";
    public static final String SORTED_EVENTS_FXML = "/fxml/SortedEvents.fxml";

    public static Repository repo = new Repository();
    public static DataBaseConnection db = new DataBaseConnection();

    /**
     * Override method which initializes main scene of GUI application
     * @param primaryStage a stage
     * @throws Exception if there is an error in loading main scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(MAIN_CALENDAR_FXML));
        primaryStage.setTitle("Calendar App");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
