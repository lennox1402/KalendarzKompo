package organizer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import organizer.Event;
import organizer.Main;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import static organizer.Main.repo;
import static organizer.Main.db;

public class AddEventController {

    @FXML
    private TextField title;
    @FXML
    private TextField place;
    @FXML
    private ChoiceBox<Integer> hour;
    @FXML
    private ChoiceBox<Integer> minute;
    @FXML
    private TextArea note;
    @FXML
    private Button addButton;

    private ObservableList<Integer> hours = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
            17, 18, 19, 20, 21, 22, 23);
    private ObservableList<Integer> minutes = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
    17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
            50, 51, 52, 53, 54, 55, 56, 57, 58, 59);
    private MainCalendarController c;

    /**
     * Initializes adding event window with text fields for title and place, choice boxes for hours and minutes, text area for note and adding button
     */
    @FXML
    public void initialize() {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(Main.MAIN_CALENDAR_FXML));
        try {
            fxmlLoader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        c = fxmlLoader.getController();

        hour.setItems(hours);
        minute.setItems(minutes);
    }

    /**
     * On action method which adds an event after clicking the button
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     */
    @FXML
    private void addClicked() throws SQLException, ClassNotFoundException {

        if(title.getText().trim().isEmpty() || place.getText().trim().isEmpty() || hour.getValue() == null || minute.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Field can't be empty");
            alert.showAndWait();
        }
        else {
            Integer hour1 = hour.getValue();
            Integer minute1 = minute.getValue();
            Date date = new Date(c.chosenYear, c.chosenMonth, c.chosenDay, hour1, minute1);
            Event newEvent = new Event(title.getText(), place.getText(), date, note.getText());

            repo.addEvent(newEvent);
            db.saveToDataBase(newEvent);

            Stage stage = (Stage)addButton.getScene().getWindow();
            stage.close();
        }
    }
}
