package uk.ac.nott.cs.comp2013.mentorapp;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.RepositoryFactory;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.view.LoginView;
import uk.ac.nott.cs.comp2013.mentorapp.view.ViewManager;


/**
 * Main class for the entire mentor app. Remember to run the application using Gradle's {@code run}
 * task rather than running the app through IntelliJ.
 */
public class MentorApp extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  private Repository<User, String> loadMockData() throws IOException {
    RepositoryFactory factory = new RepositoryFactory();
    return factory.userHashMapRepository("/MOCK_DATA.csv");
  }

  private LoginView createLoginView(Repository<User, String> repo) {
    LoginController controller = new LoginController(repo);
    return new LoginView(controller);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Repository<User, String> mockData = loadMockData();

    ViewManager vm = new ViewManager(stage);



    vm.addView(ViewManager.LOGIN, createLoginView(mockData));
    vm.setStageView(ViewManager.LOGIN);
    stage.show();
  }
}
