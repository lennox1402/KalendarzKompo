package organizer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import organizer.Event;
import organizer.Main;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import static organizer.Main.repo;

public class EventsBetweenDatesController {

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ListView<Event> listView;

    private ObservableList<Event> events = FXCollections.observableArrayList();

    /**
     * Initializes window for displaying events between two chosen dates
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
    }

    /**
     * On action method which accepts chosen dates after clicking the button
     */
    @FXML
    private void getClicked() {

        if(startDate.getValue() == null || endDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Field can't be empty");
            alert.showAndWait();
        }
        else if(endDate.getValue().isBefore(startDate.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("End date can't be before start date");
            alert.showAndWait();
        }
        else {
            LocalDate tmpDate1 = startDate.getValue();
            int day1 = tmpDate1.getDayOfMonth();
            int month1 = tmpDate1.getMonthValue();
            int year1 = tmpDate1.getYear();
            Date start = new Date((year1 - 1900), (month1 - 1), day1);

            LocalDate tmpDate2 = endDate.getValue();
            int day2 = tmpDate2.getDayOfMonth();
            int month2 = tmpDate2.getMonthValue();
            int year2 = tmpDate2.getYear();
            Date end = new Date((year2 - 1900), (month2 - 1), day2);

            events.setAll(repo.getEventsBetweenDates(start, end));
            listView.setItems(events);
        }
    }
}
