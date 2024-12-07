package uk.ac.nott.cs.comp2013.mentorapp.controller;

import java.util.Optional;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

public class LoginController {

  private final Repository<User, String> repo;

  public LoginController(Repository<User, String> model) {
    this.repo = model;
  }


  public void onTest(){
    System.out.println("testing");
  }

  public void onTest(String testWord){
    System.out.println(testWord);
  }


  public boolean onLoginClick(String username, String password) {
    Optional<User> user = repo.selectById(username);
    if (user.isEmpty()) {
      return false;
    }

    User u = user.get();
    return u.getUsername().equals(username) && u.getPassword().equals(password);
  }
}
