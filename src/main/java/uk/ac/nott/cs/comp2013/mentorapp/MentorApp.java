package uk.ac.nott.cs.comp2013.mentorapp;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;
import uk.ac.nott.cs.comp2013.mentorapp.controller.MainPageController;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.RepositoryFactory;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.view.LoginView;
import uk.ac.nott.cs.comp2013.mentorapp.view.MainPageView;
import uk.ac.nott.cs.comp2013.mentorapp.view.ViewManager;


/**
 * Main class for the entire mentor app. Remember to run the application using Gradle's {@code run}
 * task rather than running the app through IntelliJ.
 */
public class MentorApp extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  // prepares data
  private Repository<User, String> loadMockData() throws IOException {
    RepositoryFactory factory = new RepositoryFactory();
    return factory.userHashMapRepository("/MOCK_DATA.csv");
  }

  // prepares login view adds controller
  private LoginView createLoginView(Repository<User, String> repo) {
    LoginController controller = new LoginController(repo);
    return new LoginView(controller);
  }

  // changed added main page view and controller
  private MainPageView createMainPageView(Repository<User, String> repo){
    MainPageController controller = new MainPageController(repo);
  return new MainPageView(controller);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Repository<User, String> mockData = loadMockData();

    // changed
    // for setting launch size
    stage.setWidth(800);
    stage.setHeight(600);

    ViewManager vm = new ViewManager(stage);


// oluşturduğun sayfaları burada eklemen lazım

// changed $$$$$$$$$$$$$$$$$$$$
    // add pages to your view manager
    // mock data is reposityory from csv
    vm.addView(ViewManager.MAINPAGE, createMainPageView(mockData));
    vm.addView(ViewManager.LOGIN, createLoginView(mockData));
    // set opeening page
    vm.setStageView(ViewManager.LOGIN);
    stage.show();
  }
}
