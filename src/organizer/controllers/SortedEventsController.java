package organizer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import organizer.DatesComparator;
import organizer.Event;
import organizer.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static organizer.Main.repo;

public class SortedEventsController {

    @FXML
    private ListView<Event> sortedEventsList;

    private ObservableList<Event> events = FXCollections.observableArrayList();

    /**
     * Initializes window for displaying events sorted by dates
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

        List<Event> sorted = new ArrayList<>();
        for(int i = 0; i < repo.getAllEvents().size(); i++) {
            sorted.add(repo.getAllEvents().get(i));
        }
        sorted.sort(new DatesComparator());
        events.setAll(sorted);
        sortedEventsList.setItems(events);
    }
}
