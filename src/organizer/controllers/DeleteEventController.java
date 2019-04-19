package organizer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import organizer.Main;
import java.io.IOException;
import java.sql.SQLException;

import static organizer.Main.db;
import static organizer.Main.repo;

public class DeleteEventController {

    @FXML
    private ChoiceBox<String> titlesBox;
    @FXML
    private Button deleteButton;

    private ObservableList<String> titles = FXCollections.observableArrayList();

    /**
     * Title of an event which will be deleted
     */
    public static String chosenTitle;

    private MainCalendarController c;

    /**
     * Initializes window for deleting an event chosen by the title from choice box
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
     * On action method which deletes an event after clicking the button
     */
    @FXML
    private void deleteClicked() {

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
                if(repo.getAllEvents().get(i).getTitle() == chosenTitle && repo.getAllEvents().get(i).getDate().getDate() == c.chosenDay
                        && repo.getAllEvents().get(i).getDate().getMonth() == c.chosenMonth && repo.getAllEvents().get(i).getDate().getYear() == c.chosenYear) {
                    try {
                        db.deleteFromDataBase(repo.getAllEvents().get(i));
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    repo.deleteEvent(repo.getAllEvents().get(i));
                }
            }
            Stage stage = (Stage)deleteButton.getScene().getWindow();
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
