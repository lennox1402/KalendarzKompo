package organizer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import organizer.Event;
import organizer.Main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static organizer.Main.repo;

public class AllEventsForDayController {

    @FXML
    private javafx.scene.control.ListView<Event> allEventsList;

    private ObservableList<Event> events = FXCollections.observableArrayList();
    private MainCalendarController c;

    /**
     * Initializes window for displaying events in a list view for chosen day
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

        List<Event> tmp = new ArrayList<>();
        for(int i = 0; i < repo.getAllEvents().size(); i++) {
            if(repo.getAllEvents().get(i).getDate().getDate() == c.chosenDay && repo.getAllEvents().get(i).getDate().getMonth() == c.chosenMonth
                    && repo.getAllEvents().get(i).getDate().getYear() == c.chosenYear) {
                tmp.add(repo.getAllEvents().get(i));
            }
        }
        events.setAll(tmp);
        allEventsList.setItems(events);
    }
}
