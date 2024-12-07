package uk.ac.nott.cs.comp2013.mentorapp.model.user;

import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class MenteeTests {

  @Property
  public void testConstructor(String username, String password) {
    Mentee m = new Mentee(username, password);
    assertEquals(username, m.getUsername());
    assertEquals(password, m.getPassword());
    assertEquals(0, m.getCvText().length());
  }

  @Property
  public void testEquals_Succeed(String username) {
    Mentee m1 = new Mentee(username, "password");
    Mentee m2 = new Mentee(username, "password");
    assertEquals(m1, m2);
  }

  @Property
  public void testEquals_Fail(String username1, String username2) {
    assumeFalse(username1.equals(username2));
    Mentee m1 = new Mentee(username1, "password");
    Mentee m2 = new Mentee(username2, "password");
    assertNotEquals(m1, m2);
  }

}
