package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.mentorapp.controller.MainPageController;
import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.session.SessionTopicList;
import uk.ac.nott.cs.comp2013.mentorapp.model.session.SupportSession;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Administrator;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.util.Arrays;

public class MainPageView extends VBox implements ManagedView {

    private final CurrentUserSingleton currentUser;
    private final MainPageController controller;
    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;

    public MainPageView(MainPageController controller, CurrentUserSingleton currentUser) {
        this.controller = controller;
        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);
        this.currentUser = currentUser;
        buildView();
    }

    private void buildView() {
        Text userNameLabel = new Text("Welcome " + this.currentUser.name);
        Button btnProfile = new Button("Profile Page");
        Button btnLogOut = new Button("Log Out");

        setSpacing(10); // Adjust the value for more or less space

        btnProfile.setOnAction(e -> {
            var eh = onViewChange.get();
            if (eh != null) {
                eh.handle(new ViewChangeEvent(ViewManager.PROFILEPAGE));
            }
        });

        btnLogOut.setOnAction(e -> {
            this.currentUser.isActiveSession = false;
            this.currentUser.isPersonalPageCreated = false;
            var eh = onViewChange.get();
            if (eh != null) {
                eh.handle(new ViewChangeEvent(ViewManager.LOGIN));
            }
        });

        getChildren().addAll(userNameLabel);
        getChildren().addAll(btnProfile);
        getChildren().addAll(btnLogOut);
    }

    private void updateByRole() {
        if (this.currentUser.user.getRole() == UserRole.MENTEE) {
            this.createMenteeView();
        } else if (this.currentUser.user.getRole() == UserRole.MENTOR) {
            this.createMentorView();
        } else if (this.currentUser.user.getRole() == UserRole.ADMIN) {
            this.createAdminView();
        }
    }

    @Override
    public void onShowHook() {

        this.controller.addUserDetailsToSingleton();
        getChildren().clear();
        this.buildView();
        this.updateByRole();

    }

    @Override
    public EventHandler<? super ViewChangeEvent> getOnViewChange() {
        return onViewChange.get();
    }

    @Override
    public void setOnViewChange(EventHandler<? super ViewChangeEvent> eventHandler) {
        onViewChange.set(eventHandler);
    }

    private void createMenteeView() {

        SupportSession[] allSessions = this.controller.getSessionList();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Select Support Topic");

        for (String help : SessionTopicList.availTopics) {
            comboBox.getItems().addAll(help);
        }

        Button btnSupport = new Button("Create Support Request");

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(btnSupport, comboBox);

        getChildren().add(1, hbox);

        btnSupport.setOnAction(e -> {
            if (comboBox.getValue() == null) {


                comboBox.setPromptText("First Select A Topic");


            } else {
                this.controller.requestSupport(comboBox.getValue());

                comboBox.setPromptText("Request Created");
                comboBox.getSelectionModel().clearSelection();
                comboBox.setPromptText("Request Created");
                this.onShowHook();


            }
        });


        for (SupportSession session : allSessions) {
            if (session == null) {
                continue;
            }
            if (session.menteeUser == null) {
                continue;
            }
            if (session.mentorUser == null) {
                continue;
            }
            if (session.menteeUser.getId().equals(this.currentUser.name)) {
                HBox sessionBox = createSessionBoxMenteeView(session);
                getChildren().add(1, sessionBox);
            }
        }


        Text title2 = new Text("Support Session");
        title2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        getChildren().add(1, title2);


        for (SupportSession session : allSessions) {
            if (session == null) {
                continue;
            }
            if (session.menteeUser == null) {
                continue;
            }
            if (session.mentorUser != null) {
                continue;
            }
            if (session.menteeUser.getId().equals(this.currentUser.name)) {
                HBox sessionBox = createSessionBoxMenteeView(session);
                getChildren().add(1, sessionBox);
            }
        }


        Text title = new Text("Pending Support Requests");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        getChildren().add(1, title);

    }

    private void createMentorView() {

        Text title = new Text("Assigned Sessions");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        getChildren().add(1, title);

        // Get all sessions and filter for the current mentor
        SupportSession[] allSessions = this.controller.getSessionList();

        for (SupportSession session : allSessions) {
            if (session == null) {
                continue;
            }
            if (session.mentorUser == null) {
                continue;
            }
            if (session.mentorUser.getId().equals(this.currentUser.name)) {
                HBox sessionBox = createSessionBox(session);
                getChildren().add(2, sessionBox);
            }
        }
    }

    private HBox createSessionBox(SupportSession session) {
        Text topicLabel = new Text("Topic: " + session.topic);
        Text menteeLabel = new Text("Mentee: " + session.menteeUser.getId());
        Text timeLabel = new Text("Time: " + session.mentorTime);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox sessionBox = new HBox(10);
        sessionBox.setAlignment(Pos.CENTER_LEFT);
        sessionBox.getChildren().addAll(topicLabel, menteeLabel, timeLabel, spacer);

        return sessionBox;
    }

    private HBox createSessionBoxMenteeView(SupportSession session) {
        Text topicLabel = new Text("Topic: " + session.topic);
        String labelText = "";
        if (session.mentorUser == null) {
            labelText = "No mentor assigned";
        } else {
            labelText = "Mentor: " + session.mentorUser.getId();
        }
        Text menteeLabel = new Text(labelText);
        Text timeLabel = new Text("Time: " + session.mentorTime);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox sessionBox = new HBox(10);
        sessionBox.setAlignment(Pos.CENTER_LEFT);
        sessionBox.getChildren().addAll(topicLabel, menteeLabel, timeLabel, spacer);

        return sessionBox;
    }

    private void createAdminView() {
        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Active Request", this.adminViewHelperRequestList(false));
        tab1.setClosable(false);
        Tab tab2 = new Tab("Matched Requests", this.adminViewHelperRequestList(true));
        tab2.setClosable(false);

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);

        getChildren().add(1, tabPane);
    }

    private ScrollPane adminViewHelperRequestList(boolean isMatched) {
        VBox container = new VBox();
        SupportSession[] supportSessions = this.controller.getSessionList();

        if (isMatched) {
            for (SupportSession session : supportSessions) {
                if (session == null) {
                    break;
                } else if (session.mentorUser != null) {
                    container.getChildren().add(adminViewHelperRequestListElementGenerator(session));
                }
            }
        } else {
            for (SupportSession session : supportSessions) {
                if (session == null) {
                    break;
                } else if (session.mentorUser == null) {
                    container.getChildren().add(adminViewHelperRequestListElementGenerator(session));
                }
            }
        }

        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        return scrollPane;
    }


    private HBox adminViewHelperRequestListElementGenerator(SupportSession session) {
        VBox details = new VBox();
        Text spacerLabel = new Text();
        Text topicLabel = new Text("Topic: " + session.topic);
        Text menteeLabel = new Text("Mentee Name: " + session.menteeUser.getId());
        Text mentorLabel = new Text(session.mentorUser == null ? "Mentor Name: Not Assigned" : "Mentor Name: " + session.mentorUser.getId());
        Text adminLabel = new Text(session.overseer == null ? "Overseer Name: Not Assigned" : "Overseer Name: " + session.overseer.getId());
        Text spacerLabel2 = new Text();
        setSpacing(5);

        details.getChildren().addAll(spacerLabel, topicLabel, menteeLabel, mentorLabel, adminLabel, spacerLabel2);

        // ComboBox for selecting a mentor
        ComboBox<String> mentorComboBox = new ComboBox<>();
        ComboBox<String> timeComboBox = new ComboBox<>();
        mentorComboBox.setPromptText("Select Mentor");
        timeComboBox.setPromptText("Select Mentor First");
        Text selectionWarningText = new Text("");

        // Populate mentorComboBox with mentors
        Mentor[] mentors = this.getMentorsArray();
        for (Mentor mentor : mentors) {
            mentorComboBox.getItems().add(mentor.getUsername());
        }

        Button btnAssignMentor = new Button();
        if (session.mentorUser == null) {
            // If no mentor is assigned, show Assign Mentor options
            btnAssignMentor.setText("Assign Mentor");

            mentorComboBox.setOnAction(e -> {
                String selectedMentor = mentorComboBox.getValue();
                timeComboBox.getItems().clear();

                if (selectedMentor != null) {
                    for (Mentor mentor : mentors) {
                        if (mentor.getUsername().equals(selectedMentor)) {
                            timeComboBox.getItems().addAll(mentor.getAvailableTimes());
                            break;
                        }
                    }
                }
            });

            btnAssignMentor.setOnAction(e -> {
                String selectedMentor = mentorComboBox.getValue();
                String selectedTime = timeComboBox.getValue();

                if (selectedMentor == null || selectedTime == null) {
                    selectionWarningText.setText("Please select both a mentor and a time.");
                    selectionWarningText.setStyle("-fx-fill: red;");
                    return;
                }

                for (Mentor mentor : mentors) {
                    if (mentor.getUsername().equals(selectedMentor)) {
                        session.mentorUser = mentor;
                        session.mentorTime = selectedTime;
                        updateSessionUI(session); // Update session UI with the new mentor and time
                        break;
                    }
                }
            });
        } else {
            // If a mentor is assigned, show Cancel Mentor option
            btnAssignMentor.setText("Cancel Mentor");
            btnAssignMentor.setOnAction(e -> {
                this.controller.cancelSession(session);
                updateSessionUI(session); // Update session UI with no mentor assigned
            });
        }

        // VBox for ComboBoxes
        VBox comboBoxContainer = new VBox(5);
        comboBoxContainer.getChildren().addAll(mentorComboBox, timeComboBox, selectionWarningText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox listElement = new HBox(10);
        listElement.setAlignment(Pos.CENTER_LEFT);

        if (session.mentorUser == null) {
            listElement.getChildren().addAll(details, spacer, comboBoxContainer, btnAssignMentor);
        } else {
            listElement.getChildren().addAll(details, spacer, btnAssignMentor);
        }

        return listElement;
    }


    private Mentor[] getMentorsArray() {
        return this.controller.getMentorsArray();  // Fetch the mentors array from the controller
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateSessionUI(SupportSession session) {
        session.overseer = ((Administrator) this.currentUser.user);
        getChildren().clear();
        this.buildView();
        this.updateByRole();
    }
}
