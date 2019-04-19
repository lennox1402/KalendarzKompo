package organizer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import organizer.Main;
import java.io.IOException;
import java.sql.SQLException;

import static organizer.Main.db;
import static organizer.Main.repo;

public class EditEvent2Controller {

    @FXML
    private ChoiceBox<String> optionsBox;
    @FXML
    private TextField option;
    @FXML
    private Button editButton;

    private ObservableList<String> options = FXCollections.observableArrayList("Change title", "Change place", "Change note");

    static String chosenOption;
    private MainCalendarController c;
    private EditEventController e;

    /**
     * Initializes window for editing event's title, place or note chosen from choice box
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

        optionsBox.setItems(options);
    }

    /**
     * On action method which edits an event after clicking the button
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     */
    @FXML
    private void editClicked() throws SQLException, ClassNotFoundException {

        if(optionsBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Field can't be empty");
            alert.showAndWait();
        }
        else {
            chosenOption = optionsBox.getValue();
            if(chosenOption == "Change title") {
                if(option.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Field can't be empty");
                    alert.showAndWait();
                }
                else {
                    String newTitle = option.getText();
                    repo.changeTitleOfEvent(e.chosenEvent, newTitle);
                    db.updateInDataBase(e.chosenEvent);
                    Stage stage = (Stage)editButton.getScene().getWindow();
                    stage.close();
                }
            }
            else if(chosenOption == "Change place") {
                if(option.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Field can't be empty");
                    alert.showAndWait();
                }
                else {
                    String newPlace = option.getText();
                    repo.changePlaceOfEvent(e.chosenEvent, newPlace);
                    db.updateInDataBase(e.chosenEvent);
                    Stage stage = (Stage)editButton.getScene().getWindow();
                    stage.close();
                }
            }
            else if(chosenOption == "Change note") {

                String newNote = option.getText();
                repo.changeNoteOfEvent(e.chosenEvent, newNote);
                db.updateInDataBase(e.chosenEvent);
                Stage stage = (Stage)editButton.getScene().getWindow();
                stage.close();
            }
        }
    }
}
