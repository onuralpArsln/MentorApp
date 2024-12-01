package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import uk.ac.nott.cs.comp2013.mentorapp.controller.MainPageController;
import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;


public class MainPageView extends VBox implements ManagedView {


    // changed
    private final CurrentUserSingleton currentUser;
    private final MainPageController controller;
    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;


    public MainPageView(MainPageController controller, CurrentUserSingleton currentUser) {
        this.controller = controller;
        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);
        this.currentUser=currentUser;
        buildView();
    }

// same for all users
    private void buildView() {

        Text userNameLabel = new Text("MainPage");
        Button btnHello = new Button("Hello");

        Button btnLogOut = new Button("Log Out");

        // Set padding between elements
        setSpacing(10); // Adjust the value for more or less space

        btnHello.setOnAction(e -> {

                System.out.println(this.currentUser.name);

        });

        // changed
        // logout button
        btnLogOut.setOnAction(e -> {
            this.currentUser.isActiveSession=false;
            this.currentUser.isPersonalPageCreated=false;
            var eh = onViewChange.get();
            if (eh != null) {
                eh.handle(new ViewChangeEvent(ViewManager.LOGIN));
            }

        });


        getChildren().addAll(userNameLabel);
        getChildren().addAll( btnHello);
        getChildren().addAll(btnLogOut);
    }

    // changes by role
    private void updateByRole(){
        if(this.currentUser.user.getRole() == UserRole.MENTEE){
            Text menteeLabel = new Text("mentee");

            System.out.println( ((Mentee) this.currentUser.user).getCvText());

            getChildren().add(1,menteeLabel);
    }else if(this.currentUser.user.getRole() == UserRole.MENTOR){
            Text mentorLabel = new Text("mentor");
            getChildren().add(1,mentorLabel);
        }else if(this.currentUser.user.getRole() == UserRole.ADMIN){
            Text adminLabel = new Text("admin");
            getChildren().add(1,adminLabel);
        }
    }


    @Override
    public void onShowHook(){


        if( this.currentUser.isPersonalPageCreated == false ){
            this.controller.addUserDetailsToSingleton();
            getChildren().clear();
            this.buildView();
            this.updateByRole();
        }




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

// getChildren().clear();
/*
*
    // Create a HBox to arrange the elements horizontally
    HBox hbox = new HBox(10); // 10 is the spacing between the elements

    // Add the label and text field to the HBox
    hbox.getChildren().addAll(label, textField);

    // Optional: Set alignment and padding for the HBox
    hbox.setAlignment(Pos.CENTER); // Align items in the center
    hbox.setPadding(new Insets(10)); // Add padding around the HBox

    // Add the HBox to the main layout (for example, VBox or Parent container)
    getChildren().add(hbox);
*
* */
