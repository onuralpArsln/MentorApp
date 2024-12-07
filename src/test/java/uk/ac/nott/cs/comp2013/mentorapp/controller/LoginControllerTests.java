package uk.ac.nott.cs.comp2013.mentorapp.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

public class LoginControllerTests {

  private Repository<User, String> model;
  private LoginController controller;

  @BeforeEach
  public void setUp() {
    model = mock();
    controller = new LoginController(model);
  }

  @Test
  public void testLoginClickMissingUser() {
    when(model.selectById("user")).thenReturn(Optional.empty());
    boolean loginResult = controller.onLoginClick("user", "password");
    assertFalse(loginResult);
  }

  @Test
  public void testLoginClickBadPassword() {
    when(model.selectById("user")).thenReturn(Optional.of(new Mentee("user", "password")));
    boolean loginResult = controller.onLoginClick("user", "logmein");
    assertFalse(loginResult);
  }

  @Test
  public void testLoginClickGood() {
    when(model.selectById("user")).thenReturn(Optional.of(new Mentee("user", "password")));
    boolean loginResult = controller.onLoginClick("user", "password");
    assertTrue(loginResult);
  }

}
