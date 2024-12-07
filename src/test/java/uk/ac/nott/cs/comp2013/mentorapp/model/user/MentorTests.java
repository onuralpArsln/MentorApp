package uk.ac.nott.cs.comp2013.mentorapp.model.user;

import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import java.time.LocalDateTime;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class MentorTests {

  @Property
  public void testConstructor(String username, String password, LocalDateTime start,
      LocalDateTime end) {
    Mentor m = new Mentor(username, password, start, end);
    assertEquals(username, m.getUsername());
    assertEquals(password, m.getPassword());
    assertEquals(start, m.getStartAvailability());
    assertEquals(end, m.getEndAvailability());
  }

  @Property
  public void testEquals_Succeed(String username) {
    Mentor m1 = new Mentor(username, "password");
    Mentor m2 = new Mentor(username, "password");
    assertEquals(m1, m2);
  }

  @Property
  public void testEquals_Fail(String username1, String username2) {
    assumeFalse(username1.equals(username2));
    Mentor m1 = new Mentor(username1, "password");
    Mentor m2 = new Mentor(username2, "password");
    assertNotEquals(m1, m2);
  }

}
