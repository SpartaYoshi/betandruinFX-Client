package uicontrollers;
import java.awt.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

import businessLogic.BlFacade;
import com.toedter.calendar.JCalendar;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.TeamPlayingException;
import exceptions.TeamRepeatedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;

public class CreateNewEventController implements Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button closeButton;

    @FXML
    private Button createEventButton;

    @FXML
    private TextField eventtextField;

    @FXML
    private Label listOfEventsLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label writeEventText;

    @FXML
    private TableView<Event> tblEvents;

    @FXML
    private TableColumn<Event, Integer> ec1;

    @FXML
    private TableColumn<Event, String> ec2;


    private final BlFacade businessLogic;
    private MainGUI mainGUI;

    public CreateNewEventController(BlFacade bl) {
        this.businessLogic = bl;
    }




    @FXML
    void jButtonClose_actionPerformed(ActionEvent event) {
        closeButton.setVisible(false);
    }

    @FXML
    void jButtonCreateEvent_actionPerformed(ActionEvent event) {
        try {
            messageLabel.setText("");

            String[] description = eventtextField.getText().split("-");

            String team1 = description[0];
            String team2 = description[1];

            //parse the date to day month and year, without having into account the hour.
            //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            //Date date = formatter.parse(formatter.format(calendar.getValue()));
            //date = formatter.parse(formatter.format(calendar.getValue()));


            ///////////////
            LocalDate localDate = calendar.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            if (description.length>0) {
                Event newEvent = businessLogic.createEvent(team1, team2, date);

                if (newEvent!= null) {
                    messageLabel.getStyleClass().setAll("lbl","lbl-success");
                    messageLabel.setText("The event has been succesfully created.");
                    tblEvents.getItems().add(newEvent);
                    holidays.add(Dates.convertToLocalDateViaInstant(date));
                }
                else {
                    messageLabel.getStyleClass().setAll("lbl","lbl-danger");
                    messageLabel.setText("Error. The event could not be created.");
                }

            }
        }
        catch (EventFinished e2) {
            messageLabel.getStyleClass().setAll("lbl","lbl-danger");
            messageLabel.setText("The event could not be created. Try again selecting another date");
        }
        catch (TeamPlayingException e3) {
            messageLabel.getStyleClass().setAll("lbl","lbl-danger");
            messageLabel.setText("Error! Try it again changing the teams. One of those teams is already playing a match that day");

        }
        catch (TeamRepeatedException e5){
            messageLabel.getStyleClass().setAll("lbl","lbl-danger");
            messageLabel.setText("Error! Both teams are the same");

        }
        catch(Exception e4) {
            messageLabel.getStyleClass().setAll("lbl","lbl-danger");
            messageLabel.setText("Error! It must be written as: Local team's name - Visitor team's name");
        }


    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


    private List<LocalDate> holidays = new ArrayList<>();


    private void setEvents(int year, int month) {
        Date date = Dates.toDate(year,month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    @FXML
    void initialize() {
        createEventButton.getStyleClass().setAll("btn", "btn-primary");

        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

        calendar.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
            DatePickerSkin skin = (DatePickerSkin) calendar.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {
                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
                    String month = ((Label) (labels.get(0))).getText();
                    String year =  ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);
                    setEventsPrePost(ym.getYear(), ym.getMonthValue());
                });
            });


        });

        calendar.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (holidays.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            }
                        }
                    }
                };
            }
        });

        // a date has been chosen, update the combobox of Events
        calendar.setOnAction(actionEvent -> {
            tblEvents.getItems().clear();
            Vector<Event> events = businessLogic.getEvents(Dates.convertToDate(calendar.getValue()));
            for (domain.Event ev : events) {
                tblEvents.getItems().add(ev);
            }
        });

        // Bind columns to Event attributes
        ec1.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
        ec2.setCellValueFactory(new PropertyValueFactory<>("description"));


    }



}
