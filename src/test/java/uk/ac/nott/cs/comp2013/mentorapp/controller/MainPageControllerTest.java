package uk.ac.nott.cs.comp2013.mentorapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.session.SupportSession;
import uk.ac.nott.cs.comp2013.mentorapp.model.session.SupportSessionSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainPageControllerTest {

    private Repository<User, String> repoMock;
    private CurrentUserSingleton currentUserMock;
    private SupportSessionSingleton supportSessionSingletonMock;
    private MainPageController controller;

    @BeforeEach
    void setUp() {
        repoMock = mock(Repository.class);
        currentUserMock = mock(CurrentUserSingleton.class);
        supportSessionSingletonMock = mock(SupportSessionSingleton.class);
        controller = new MainPageController(repoMock, currentUserMock, supportSessionSingletonMock);
    }

    @Test
    void testRequestSupport() {
        // Arrange
        String topic = "Math Help";
        Mentee mockMentee = mock(Mentee.class);
        when(currentUserMock.user).thenReturn(mockMentee);

        // Act
        controller.requestSupport(topic);

        // Assert
        verify(supportSessionSingletonMock, times(1)).addNewSessionToList(any(SupportSession.class));
    }

    @Test
    void testOnLoginClick_ValidCredentials() {
        // Arrange
        String username = "testAdmin";
        String password = "testP";
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn(username);
        when(mockUser.getPassword()).thenReturn(password);
        when(repoMock.selectById(username)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = controller.onLoginClick(username, password);

        // Assert
        assertTrue(result);
    }

    @Test
    void testOnLoginClick_InvalidCredentials() {
        // Arrange
        String username = "JohnDoe";
        String password = "wrongPass";
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn(username);
        when(mockUser.getPassword()).thenReturn("securePass");
        when(repoMock.selectById(username)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = controller.onLoginClick(username, password);

        // Assert
        assertFalse(result);
    }

    @Test
    void testGetMentorsArray() {
        // Arrange
        List<User> allUsers = new ArrayList<>();
        Mentor mockMentor = mock(Mentor.class);
        when(mockMentor.getRole()).thenReturn(UserRole.MENTOR);
        allUsers.add(mockMentor);

        when(repoMock.selectAll()).thenReturn(allUsers);

        // Act
        Mentor[] mentors = controller.getMentorsArray();

        // Assert
        assertEquals(1, mentors.length);
        assertEquals(mockMentor, mentors[0]);
    }

    @Test
    void testCancelSession() {
        // Arrange
        SupportSession session = new SupportSession();
        Administrator mockAdmin = mock(Administrator.class);
        when(currentUserMock.user).thenReturn(mockAdmin);

        // Act
        controller.cancelSession(session);

        // Assert
        assertNull(session.mentorUser);
        assertNull(session.mentorTime);
        assertEquals(mockAdmin, session.overseer);
    }
}
