package uk.ac.nott.cs.comp2013.mentorapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class ProfilePageControllerTest {

    private Repository repoMock;
    private CurrentUserSingleton currentUserMock;
    private ProfilePageController controller;

    @BeforeEach
    void setUp() {
        repoMock = Mockito.mock(Repository.class);
        currentUserMock = Mockito.mock(CurrentUserSingleton.class);
        controller = new ProfilePageController(repoMock, currentUserMock);
    }

    @Test
    void testUpdateMenteeData() {
        // Arrange
        String userName = "JohnDoe";
        String userPass = "securePass123";
        String cv = "CV content here";

        // Act
        controller.updateMenteeData(userName, userPass, cv);

        // Assert
        Mentee expectedMentee = new Mentee(userName, userPass, cv);
        verify(repoMock, times(1)).update(expectedMentee);
    }

    @Test
    void testUpdateMentorData() {
        // Arrange
        String userName = "JaneDoe";
        String userPass = "anotherSecurePass";
        String startAvailability = "2024-12-08T09:00:00";
        String endAvailability = "2024-12-08T17:00:00";

        // Act
        controller.updateMentorData(userName, userPass, startAvailability, endAvailability);

        // Assert
        Mentor expectedMentor = new Mentor(
                userName,
                userPass,
                LocalDateTime.parse(startAvailability),
                LocalDateTime.parse(endAvailability)
        );
        verify(repoMock, times(1)).update(expectedMentor);
    }
}
