package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;


//changed
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;
import javafx.scene.input.KeyCode;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;


/**
 * The {@code LoginView} provides a login screen for users to access the app. It also serves as a
 * simple example of how to implement {@code ManagedView}. Note that this class extends {@code VBox}
 * so that we can use JavaFX's built-in layout logic.
 */
public class LoginView extends VBox implements ManagedView {

    private final LoginController controller;
    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Text warningLabel;
    private CurrentUserSingleton currentUser;
    private boolean isPasswordHidden = true;

    public LoginView(LoginController controller, CurrentUserSingleton currentUser) {
        this.controller = controller;
        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);
        this.currentUser = currentUser;


        buildView();
    }

    private void buildView() {


        // changed
        Text passwordLabel = new Text("Password");
        Text userNameLabel = new Text("Username");
        this.warningLabel = new Text("");
        warningLabel.setFill(Color.RED);


        txtUsername = new TextField();
        txtPassword = new PasswordField();
        Button btnLogin = new Button("Login");

        // adding image
        Image image = new Image("/uonBanner.png");
        ImageView imageView = new ImageView(image);


        // changed
        // Set padding between elements
        setSpacing(10); // Adjust the value for more or less space


        btnLogin.setOnAction(e -> {
            boolean success = controller.onLoginClick(txtUsername.getText(), txtPassword.getText());
            if (success) {
                // changed
                this.currentUser.name = txtUsername.getText();
                // page change code
                var eh = onViewChange.get();
                if (eh != null) {
                    // changed
                    //eh.handle(new ViewChangeEvent(ViewManager.DUMMY));
                    // instead of dummy connect to main page
                    eh.handle(new ViewChangeEvent(ViewManager.MAINPAGE));
                }
            } else {
                this.warningLabel.setText("Incorrect Credentials");

            }
        });

        this.txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                boolean success = controller.onLoginClick(txtUsername.getText(), txtPassword.getText());
                if (success) {
                    // changed
                    this.currentUser.name = txtUsername.getText();
                    this.currentUser.isActiveSession = true;
                    // page change code
                    var eh = onViewChange.get();
                    if (eh != null) {
                        // changed
                        //eh.handle(new ViewChangeEvent(ViewManager.DUMMY));
                        // instead of dummy connect to main page
                        eh.handle(new ViewChangeEvent(ViewManager.MAINPAGE));
                    }
                } else {
                    warningLabel.setText("Incorrect Credentials");

                }
                // Clear the text field after pressing Enter
            }
        });

        getChildren().addAll(imageView, userNameLabel, txtUsername, passwordLabel, txtPassword, warningLabel, btnLogin);
    }

    // changed
    @Override
    public void onShowHook() {
        this.txtPassword.clear();
        this.txtUsername.clear();
        this.warningLabel.setText("");

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
