package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;

/**
 * The {@code LoginView} provides a login screen for users to access the app. It also serves as a
 * simple example of how to implement {@code ManagedView}. Note that this class extends {@code VBox}
 * so that we can use JavaFX's built-in layout logic.
 */
public class LoginView extends VBox implements ManagedView {

  private final LoginController controller;
  protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;
  private TextField txtUsername, txtPassword;
  private Text testText;

  public LoginView(LoginController controller) {
    this.controller = controller;
    this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);


    buildView();
  }

  private void buildView() {
    testText = new Text("df");
    txtUsername = new TextField();
    txtPassword = new TextField();
    Button btnLogin = new Button("Login");




    btnLogin.setOnAction(e -> {
      boolean success = controller.onLoginClick(txtUsername.getText(), txtPassword.getText());
      if (success) {
        var eh = onViewChange.get();
        if (eh != null) {
          eh.handle(new ViewChangeEvent(ViewManager.DUMMY));
        }
      }
    });

    getChildren().addAll(testText,txtUsername, txtPassword, btnLogin);
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
