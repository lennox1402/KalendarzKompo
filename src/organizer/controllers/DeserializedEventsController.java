package organizer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import organizer.Event;
import organizer.Main;
import organizer.MyException;
import organizer.XmlSerialization;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static organizer.Main.db;
import static organizer.Main.repo;

public class DeserializedEventsController {

    @FXML
    private javafx.scene.control.ListView<Event> deserializedList;

    private ObservableList<Event> events = FXCollections.observableArrayList();

    /**
     * Initializes window for displaying deserialized events from xml file in a list view
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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText("All data in Database and Repository will be replaced by the data from XML file");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            deserialize();
        }
        else {
            alert.close();
        }
    }

    /**
     * Deserializes events from xml file
     */
    private void deserialize() {

        try {
            db.deleteAllFromDatabase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < repo.getAllEvents().size(); i++) {
            repo.deleteEvent(repo.getAllEvents().get(i));
        }

        repo = XmlSerialization.deserialize();

        for(int i = 0; i < repo.getAllEvents().size(); i++) {
            try {
                db.saveToDataBase(repo.getAllEvents().get(i));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        events.setAll(repo.getAllEvents());
        deserializedList.setItems(events);
    }
}
