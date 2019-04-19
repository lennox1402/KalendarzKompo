package organizer.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import organizer.Main;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import static organizer.Main.db;
import static organizer.Main.repo;

public class DeleteOlderEventsController {

    @FXML
    private DatePicker date;
    @FXML
    private Button deleteOlderButton;

    /**
     * Initializes window for deleting events older than chosen date
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
     * On action method which deletes events after clicking the button
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     */
    @FXML
    private void deleteOlderClicked() throws SQLException, ClassNotFoundException {

        if(date.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Field can't be empty");
            alert.showAndWait();
        }
        else{
            LocalDate tmpDate1 = date.getValue();
            int day1 = tmpDate1.getDayOfMonth();
            int month1 = tmpDate1.getMonthValue();
            int year1 = tmpDate1.getYear();
            Date date = new Date((year1 - 1900), (month1 - 1), day1);

            for(int i = 0; i < repo.getAllEvents().size(); i++) {
                if(repo.getAllEvents().get(i).getDate().before(date)) {
                    db.deleteFromDataBase(repo.getAllEvents().get(i));
                }
            }
            repo.deleteOldEvents(date);
            Stage stage = (Stage)deleteOlderButton.getScene().getWindow();
            stage.close();
        }
    }
}
