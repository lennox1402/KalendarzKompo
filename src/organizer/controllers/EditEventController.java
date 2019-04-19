package organizer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import organizer.Event;
import organizer.Main;
import java.io.IOException;

import static organizer.Main.repo;

public class EditEventController {

    @FXML
    private ChoiceBox<String> titlesBox;
    @FXML
    private Button editButton;

    private ObservableList<String> titles = FXCollections.observableArrayList();

    static String chosenTitle;
    static Event chosenEvent;

    private MainCalendarController c;

    /**
     * Initializes window in which event is chosen for editing by the title from choice box
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

        prepareChoiceBox();
    }

    /**
     * On action method which chooses an event for editing after clicking the button
     */
    @FXML
    private void editClicked() {

        if(titlesBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Field can't be empty");
            alert.showAndWait();
        }
        else {
            chosenTitle = titlesBox.getValue();

            for(int i = 0; i < repo.getAllEvents().size(); i++) {
                if(repo.getAllEvents().get(i).getTitle() == chosenTitle && repo.getAllEvents().get(i).getDate().getDate() == c.chosenDay && repo.getAllEvents().get(i).getDate().getMonth() == c.chosenMonth && repo.getAllEvents().get(i).getDate().getYear() == c.chosenYear) {
                    chosenEvent = new Event(repo.getAllEvents().get(i));
                }
            }
            try {
                int currentMonth = c.month;
                int currentYear = c.year;

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.EDIT_EVENT2_FXML));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Edit event");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

                c.month = currentMonth;
                c.year = currentYear;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)editButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Sets values in a choice box
     */
    private void prepareChoiceBox() {

        for(int i = 0; i < repo.getAllEvents().size(); i++) {
            if(repo.getAllEvents().get(i).getDate().getDate() == c.chosenDay && repo.getAllEvents().get(i).getDate().getMonth() == c.chosenMonth
                    && repo.getAllEvents().get(i).getDate().getYear() == c.chosenYear) {
                titles.add(repo.getAllEvents().get(i).getTitle());
            }
        }
        titlesBox.setItems(titles);
    }
}
