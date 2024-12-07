package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import uk.ac.nott.cs.comp2013.mentorapp.controller.ProfilePageController;
import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;


public class ProfilePageView extends VBox implements ManagedView {


    private final ProfilePageController controller;
    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;

    private final CurrentUserSingleton currentUser;


    public ProfilePageView(ProfilePageController controller, CurrentUserSingleton currentUser) {
        this.controller = controller;
        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);
        this.currentUser = currentUser;


        buildView();
    }

    private void buildView() {


        // changed
        Text passwordLabel = new Text("profile page");

        Button btnMainpage = new Button("Back to Mainpage");
        // Adjust the value for more or less space


        setSpacing(10);

        btnMainpage.setOnAction(e -> {
            var eh = onViewChange.get();
            if (eh != null) {
                eh.handle(new ViewChangeEvent(ViewManager.MAINPAGE));
            }

        });


        getChildren().addAll(passwordLabel, btnMainpage);
    }


    private void updateByRole() {
        if (this.currentUser.user.getRole() == UserRole.MENTEE) {
            this.createMenteeView();
        } else if (this.currentUser.user.getRole() == UserRole.MENTOR) {
            this.createMentorView();
        }
    }

    private void createMenteeView() {

        Button btnApply = new Button("Apply Changes");
        getChildren().add(1, btnApply);


        // adding cv details
        TextField cvField = new TextField(((Mentee) this.currentUser.user).getCvText());
        getChildren().add(1, cvField);


        Text cvLabel = new Text("CV Text : ");
        getChildren().add(1, cvLabel);

        HBox hbox3 = new HBox(10);
        hbox3.setAlignment(Pos.CENTER_LEFT);
        hbox3.getChildren().addAll(cvLabel, cvField);

        getChildren().add(1, hbox3);


        // password detail

        Text userPassLabel = new Text("Password:");
        TextField userPassField = new TextField(this.currentUser.user.getPassword());

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(userPassLabel, userPassField);

        getChildren().add(1, hbox);

        // username details
        Text userNameLabel = new Text("Username:");
        TextField userNameField = new TextField(this.currentUser.name);

        HBox hbox2 = new HBox(10);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox2.getChildren().addAll(userNameLabel, userNameField);

        getChildren().add(1, hbox2);


        btnApply.setOnAction(e -> {
            // send data to controller
            this.controller.updateMenteeData(userNameField.getText(), userPassField.getText(), cvField.getText());

        });


    }

    private void createMentorView() {

        // Button to apply changes
        Button btnApply = new Button("Apply Changes");
        getChildren().add(1, btnApply);

        // Username details
        Text userNameLabel = new Text("Username:");
        TextField userNameField = new TextField(this.currentUser.name);

        HBox hbox2 = new HBox(10);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox2.getChildren().addAll(userNameLabel, userNameField);
        getChildren().add(1, hbox2);

        // Password details
        Text userPassLabel = new Text("Password:");
        TextField userPassField = new TextField(this.currentUser.user.getPassword());

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(userPassLabel, userPassField);
        getChildren().add(1, hbox);

        // Availability details
        Text startAvailLabel = new Text("Start Availability:");
        TextField startAvailField = new TextField(((Mentor) this.currentUser.user).getStartAvailability().toString());


        Text endAvailLabel = new Text("End Availability:");
        TextField endAvailField = new TextField(((Mentor) this.currentUser.user).getEndAvailability().toString());

        HBox hbox3 = new HBox(10);
        hbox3.setAlignment(Pos.CENTER_LEFT);
        hbox3.getChildren().addAll(startAvailLabel, startAvailField, endAvailLabel, endAvailField);
        getChildren().add(1, hbox3);

        // Apply button action
        btnApply.setOnAction(e -> {
            // Send data to controller
            this.controller.updateMentorData(userNameField.getText(), userPassField.getText(),
                    startAvailField.getText(), endAvailField.getText());
        });
    }


    // changed
    @Override
    public void onShowHook() {
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
}
