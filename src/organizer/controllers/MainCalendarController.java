package organizer.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import organizer.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static organizer.Main.db;
import static organizer.Main.repo;

public class MainCalendarController {

    static int month, year;
    private static int currentMonth, currentYear, currentDay, day;
    private GregorianCalendar cal;
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @FXML
    private Label monthLabel;
    @FXML
    private GridPane gridPane;
    @FXML
    private ComboBox<Integer> yearsCombo;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button addingButton, deletingButton, editingButton, displayingButton, deletingOlderButton, gettingButton, sortingButton,
    serializingButton, deserializingButton, serCSVButton, previousButton, nextButton, currentDayButton;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menu1, menu2;

    static int chosenDay;
    static int chosenMonth;
    static int chosenYear;

    static List<Event> tmp = new ArrayList<>();
    static boolean ifTimerInitialized = false;

    /**
     * Initializes main window which contains the calendar in a grid pane, all buttons and menu, initializes all windows connected to the main one, also sets timer searching for upcoming events alerts
     * @throws SQLException if an error in connection to database occurs or a query is wrong
     * @throws ClassNotFoundException if there is wrong JDBC Driver given
     */
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {

        if (!ifTimerInitialized) {
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.minutes(1),
                    ae -> loadAlert()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            ifTimerInitialized = true;
        }

        pane.setStyle("-fx-background-color: #e6e6e6");
        addingButton.setVisible(false);
        deletingButton.setVisible(false);
        editingButton.setVisible(false);
        displayingButton.setVisible(false);

        repo.setAllEvents(db.getFromDataBase());

        cal = new GregorianCalendar();
        day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        month = cal.get(GregorianCalendar.MONTH);
        year = cal.get(GregorianCalendar.YEAR);
        currentMonth = month;
        currentYear = year;
        currentDay = day;

        prepareComboBox();

        monthLabel.setText(months[currentMonth]);
        refreshCalendar(currentMonth, currentYear);
        gridPane.setStyle("-fx-background-color: #e6e6e6; -fx-grid-lines-visible: true");

        for (Node node : gridPane.getChildren()) {

            if (node instanceof Label) {
                node.setOnMouseClicked(e -> {

                    if(((Label) node).getText().trim().isEmpty() || ((Label) node).getText().equals("Monday") || ((Label) node).getText().equals("Tuesday")
                            || ((Label) node).getText().equals("Wednesday") || ((Label) node).getText().equals("Thursday") || ((Label) node).getText().equals("Friday")
                            || ((Label) node).getText().equals("Saturday") || ((Label) node).getText().equals("Sunday")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid operation");
                        alert.show();
                    }
                    else {
                        chosenDay = Integer.parseInt(((Label) node).getText());
                        chosenMonth = monthToInt(monthLabel.getText());
                        chosenYear = yearsCombo.getValue() - 1900;

                        for (Node node2 : gridPane.getChildren()) {
                            if (node2 instanceof Label) {

                                if (node2.getStyle().equals("-fx-border-width: 2; -fx-border-color: black")) {
                                    node2.setStyle("-fx-border-width: 0; -fx-border-color: #e6e6e6");
                                }
                            }
                        }
                        node.setStyle("-fx-border-width: 2; -fx-border-color: black");
                        addingButton.setVisible(true);
                        deletingButton.setVisible(true);
                        editingButton.setVisible(true);
                        displayingButton.setVisible(true);
                    }
                });
            }
        }
    }

    /**
     * Sets alert for an event
     */
    private void loadAlert() {

        Date currentDate = new Date();
        long secondsDifference;

        for (int i = 0; i < repo.getAllEvents().size(); i++) {
            secondsDifference = ((repo.getEvent(i).getDate().getTime() - currentDate.getTime()) / 1000) / 60;
            if (secondsDifference == 1440) {
                System.out.println(repo.getEvent(i).toString());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("Upcoming event:\n" + repo.getEvent(i).toString());
                alert.show();
            }
        }
    }

    /**
     * Sets days in a grid pane like in calendar
     * @param startDay first day of month
     * @param numberOfDays number of days for month
     */
    private void setDays(int startDay, int numberOfDays) {

        int i = 1;
        if (startDay == 1) {
            startDay += 7;
        }

        for (Node node : gridPane.getChildren()) {

            if (node instanceof Label) {
                if (startDay > 2 - 7) {
                    startDay--;
                } else {
                    if (i <= numberOfDays) {
                        ((Label) node).setText(Integer.toString(i));

                        ((Label) node).setFont(Font.font("System", FontWeight.NORMAL, 12));
                        node.setStyle("");

                        List<Integer> daysWithEvent = getDaysWithEvent(month, year);

                        if (daysWithEvent.size() > 0) {
                            for (int j = 0; j < daysWithEvent.size(); j++) {
                                if (Integer.parseInt(((Label) node).getText()) == daysWithEvent.get(j)) {
                                    ((Label) node).setFont(Font.font("System", FontWeight.BOLD, 16));
                                }
                            }
                        }

                        if (month == currentMonth && year == currentYear) {
                            if (i == currentDay) {
                                ((Label) node).setBackground(new Background(new BackgroundFill(Color.rgb(45, 134, 89), CornerRadii.EMPTY, Insets.EMPTY)));
                            }
                        } else {
                            ((Label) node).setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                        i++;
                    }
                }
            }
        }
    }

    /**
     * Refreshes calendar
     * @param month1 a month
     * @param year1 a year
     */
    private void refreshCalendar(int month1, int year1) {

        GregorianCalendar cal = new GregorianCalendar(year1, month1, 1);
        int numberOfDays, startOfMonth;

        numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
        setDays(startOfMonth, numberOfDays);
    }

    /**
     * On action method which changes month to the next one after clicking the button
     */
    @FXML
    private void nextMonthClicked() {

        if (month == 11) {
            month = 0;
            year++;
            yearsCombo.getSelectionModel().selectNext();
        }
        else {
            month++;
        }

        clearCalendar();
        refreshCalendar(month, year);
        monthLabel.setText(months[month]);

        addingButton.setVisible(false);
        deletingButton.setVisible(false);
        editingButton.setVisible(false);
        displayingButton.setVisible(false);
    }

    /**
     * On action method which changes month to the previous one after clicking the button
     */
    @FXML
    private void previousMonthClicked() {

        if (month == 0) {
            month = 11;
            year--;
            yearsCombo.getSelectionModel().selectPrevious();
        }
        else {
            month--;
        }

        clearCalendar();
        refreshCalendar(month, year);
        monthLabel.setText(months[month]);

        addingButton.setVisible(false);
        deletingButton.setVisible(false);
        editingButton.setVisible(false);
        displayingButton.setVisible(false);
    }

    /**
     * Clears calendar
     */
    private void clearCalendar() {

        int i = 7;
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Label) {
                if (i <= 0) {
                    ((Label) node).setText("");
                } else {
                    i--;
                }
            }
        }
    }

    /**
     * Sets values of years in a choice box
     */
    private void prepareComboBox() {

        int i = currentYear;
        for (i = i - 100; i <= currentYear + 100; i++) {
            yearsCombo.getItems().add(i);
        }
        yearsCombo.getSelectionModel().select(100);

        yearsCombo.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                year = newValue;
                clearCalendar();
                refreshCalendar(month, year);
                addingButton.setVisible(false);
                deletingButton.setVisible(false);
                editingButton.setVisible(false);
                displayingButton.setVisible(false);
            }
        });
    }

    /**
     * On action method which show current date after clicking the button
     */
    @FXML
    private void comeBackToCurrent() {

        month = currentMonth;
        year = currentYear;
        clearCalendar();
        refreshCalendar(currentMonth, currentYear);
        yearsCombo.getSelectionModel().select(100);
        monthLabel.setText(months[month]);

        addingButton.setVisible(false);
        deletingButton.setVisible(false);
        editingButton.setVisible(false);
        displayingButton.setVisible(false);
    }

    /**
     * On action method which shows adding event window after clicking the button
     */
    @FXML
    private void addEventClicked() {

        try {
            int currentMonth = month;
            int currentYear = year;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.ADD_EVENT_FXML));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Add event");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    refreshCalendar(month, year);
                    addingButton.setVisible(false);
                    deletingButton.setVisible(false);
                    editingButton.setVisible(false);
                    displayingButton.setVisible(false);
                }
            });
            stage.show();

            month = currentMonth;
            year = currentYear;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method which shows deleting event window after clicking the button
     */
    @FXML
    private void deleteEventClicked() {

        try {
            int currentMonth = month;
            int currentYear = year;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.DELETE_EVENT_FXML));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Delete event");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    refreshCalendar(month, year);
                    addingButton.setVisible(false);
                    deletingButton.setVisible(false);
                    editingButton.setVisible(false);
                    displayingButton.setVisible(false);
                }
            });
            stage.show();

            month = currentMonth;
            year = currentYear;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method which shows window for deleting events older than chosen date after clicking the button
     */
    @FXML
    private void deleteOlderClicked() {

        try {
            int currentMonth = month;
            int currentYear = year;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.DELETE_OLDER_EVENTS_FXML));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Delete older events");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    refreshCalendar(month, year);
                    addingButton.setVisible(false);
                    deletingButton.setVisible(false);
                    editingButton.setVisible(false);
                    displayingButton.setVisible(false);
                }
            });
            stage.show();

            month = currentMonth;
            year = currentYear;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method which shows window for editing an event after clicking the button
     */
    @FXML
    private void editEventClicked() {

        try {
            int currentMonth = month;
            int currentYear = year;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.EDIT_EVENT_FXML));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Edit event");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    refreshCalendar(month, year);
                    addingButton.setVisible(false);
                    deletingButton.setVisible(false);
                    editingButton.setVisible(false);
                    displayingButton.setVisible(false);
                }
            });
            stage.show();

            month = currentMonth;
            year = currentYear;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method which shows window for getting events between two chosen dates after clicking the button
     */
    @FXML
    private void getBetweenClicked() {

        try {
            int currentMonth = month;
            int currentYear = year;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.EVENTS_BETWEEN_DATES_FXML));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Get events between dates");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            addingButton.setVisible(false);
            deletingButton.setVisible(false);
            editingButton.setVisible(false);
            displayingButton.setVisible(false);

            month = currentMonth;
            year = currentYear;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method which shows window for displaying all event taking place on chosen date after clicking the button
     */
    @FXML
    private void displayDayClicked() {

        try {
            int currentMonth = month;
            int currentYear = year;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.ALL_EVENTS_FOR_DAY_FXML));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("All event for that day");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            addingButton.setVisible(false);
            deletingButton.setVisible(false);
            editingButton.setVisible(false);
            displayingButton.setVisible(false);

            month = currentMonth;
            year = currentYear;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method which shows window for displaying events sorted by dates after clicking the button
     */
    @FXML
    private void sortClicked() {

        try {
            int currentMonth = month;
            int currentYear = year;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.SORTED_EVENTS_FXML));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Sorted events");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            addingButton.setVisible(false);
            deletingButton.setVisible(false);
            editingButton.setVisible(false);
            displayingButton.setVisible(false);

            month = currentMonth;
            year = currentYear;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method for serializing to xml file all events after clicking the button
     */
    @FXML
    private void serializeClicked() {

        XmlSerialization.serialize(repo);

        addingButton.setVisible(false);
        deletingButton.setVisible(false);
        editingButton.setVisible(false);
        displayingButton.setVisible(false);
    }

    /**
     * On action method which shows window for displaying deserialized events after clicking the button
     */
    @FXML
    private void deserializeClicked() {

        try {
            int currentMonth = month;
            int currentYear = year;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.DESERIALIZED_EVENTS_FXML));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Deserialized events");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    refreshCalendar(month, year);
                    addingButton.setVisible(false);
                    deletingButton.setVisible(false);
                    editingButton.setVisible(false);
                    displayingButton.setVisible(false);
                }
            });
            stage.show();

            month = currentMonth;
            year = currentYear;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method for serializing to csv file all events after clicking the button
     */
    @FXML
    private void serializeCSVClicked() {

        try {
            CsvSerialization.serilizeToCSV(repo.getAllEvents());
        }
        catch (MyException e) {
            e.printStackTrace();
        }

        addingButton.setVisible(false);
        deletingButton.setVisible(false);
        editingButton.setVisible(false);
        displayingButton.setVisible(false);
    }

    /**
     * On action method displaying window with information about the program and authors after choosing in menu
     */
    @FXML
    private void getDescription() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Description");
        alert.setHeaderText(null);
        alert.setContentText("'Calendar application made using JavaFX components'\nAuthors:\nMarta Dębecka 210161\nOlga Kalinowska 210213");
        alert.showAndWait();
    }

    /**
     * On action method changing theme for dark after choosing in menu
     */
    @FXML
    private void changeTheme() {

        pane.setStyle("-fx-background-color: #bfbfbf");
        addingButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        deletingButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        displayingButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        editingButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        deletingOlderButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        gettingButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        sortingButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        serializingButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        deserializingButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        serCSVButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        previousButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        nextButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        yearsCombo.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        currentDayButton.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        menuBar.setStyle("-fx-background-color: #a6a6a6; -fx-border-color: #666666");
        menu1.setStyle("-fx-background-color: #a6a6a6");
        menu2.setStyle("-fx-background-color: #a6a6a6");
    }

    /**
     * On action method changing theme for bright after choosing in menu
     */
    @FXML
    private void changeTheme2() {

        pane.setStyle("-fx-background-color: #e6e6e6");
        addingButton.setStyle("");
        deletingButton.setStyle("");
        displayingButton.setStyle("");
        editingButton.setStyle("");
        deletingOlderButton.setStyle("");
        gettingButton.setStyle("");
        sortingButton.setStyle("");
        serializingButton.setStyle("");
        deserializingButton.setStyle("");
        serCSVButton.setStyle("");
        previousButton.setStyle("");
        nextButton.setStyle("");
        yearsCombo.setStyle("");
        currentDayButton.setStyle("");
        menuBar.setStyle("");
        menu1.setStyle("");
        menu2.setStyle("");
    }

    /**
     * Gets days on which events are taking place
     * @param month a month
     * @param year a year
     * @return list containing number of days on which any events are taking place
     */
    private List<Integer> getDaysWithEvent(int month, int year) {

        List<Integer> days = new ArrayList<>();
        List<Event> events = repo.getAllEvents();

        for (Event e : events) {
            if (e.getDate().getMonth() == month && e.getDate().getYear() + 1900 == year) {
                days.add(e.getDate().getDate());
            }
        }
        return days;
    }

    @FXML
    private void clicked1() {}

    /**
     * Converts String values of months to Integer
     * @param month name of month
     * @return Integer value for each given month
     */
    private int monthToInt(String month) {

        int intMonth = 0;
        switch(month) {
            case "January": {
                intMonth = 0;
                break;
            }
            case "February": {
                intMonth = 1;
                break;
            }
            case "March": {
                intMonth = 2;
                break;
            }
            case "April": {
                intMonth = 3;
                break;
            }
            case "May": {
                intMonth = 4;
                break;
            }
            case "June": {
                intMonth = 5;
                break;
            }
            case "July": {
                intMonth = 6;
                break;
            }
            case "August": {
                intMonth = 7;
                break;
            }
            case "September": {
                intMonth = 8;
                break;
            }
            case "October": {
                intMonth = 9;
                break;
            }
            case "November": {
                intMonth = 10;
                break;
            }
            case "December": {
                intMonth = 11;
                break;
            }
        }
        return intMonth;
    }
}
