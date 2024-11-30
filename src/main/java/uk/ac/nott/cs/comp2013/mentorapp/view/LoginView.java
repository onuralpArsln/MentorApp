package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;
import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;

/**
 * The {@code LoginView} provides a login screen for users to access the app. It also serves as a
 * simple example of how to implement {@code ManagedView}. Note that this class extends {@code VBox}
 * so that we can use JavaFX's built-in layout logic.
 */
public class LoginView extends VBox implements ManagedView {

  private final LoginController controller;
  protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;
  private TextField txtUsername, txtPassword;
  private  CurrentUserSingleton currentUser;

    public LoginView(LoginController controller, CurrentUserSingleton currentUser) {
    this.controller = controller;
    this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);
    this.currentUser=  currentUser;


    buildView();
  }

  private void buildView() {



    Text passwordLabel = new Text("Password");
    Text userNameLabel = new Text("Username");



    txtUsername = new TextField();
    txtPassword = new TextField();
    Button btnLogin = new Button("Login");

    // Set padding between elements
    setSpacing(10); // Adjust the value for more or less space




    btnLogin.setOnAction(e -> {
      boolean success = controller.onLoginClick(txtUsername.getText(), txtPassword.getText());
      if (success) {
        this.currentUser.name=txtUsername.getText();
        System.out.println("login succes");
        var eh = onViewChange.get();
        if (eh != null) {
          // changed
          //eh.handle(new ViewChangeEvent(ViewManager.DUMMY));
          // instead of dummy connect to main page
          eh.handle(new ViewChangeEvent(ViewManager.MAINPAGE));
        }
      }else{
        System.out.println("wrong password");
      }
    });

    getChildren().addAll(userNameLabel,txtUsername, passwordLabel,txtPassword, btnLogin);
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
