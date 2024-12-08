package uk.ac.nott.cs.comp2013.mentorapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.session.SupportSession;
import uk.ac.nott.cs.comp2013.mentorapp.model.session.SupportSessionSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.*;

public class MainPageController {

    //changed
    private final Repository<User, String> repo;
    private final CurrentUserSingleton currentUser;
    private final SupportSessionSingleton supportSessionSingleton;

    //changed
    public MainPageController(Repository<User, String> model,
                              CurrentUserSingleton currentUser,
                              SupportSessionSingleton supportSessionSingleton) {
        this.repo = model;
        this.currentUser = currentUser;
        this.supportSessionSingleton = supportSessionSingleton;
    }


    public void requestSupport(String topic) {
        SupportSession supportSession = new SupportSession();
        supportSession.menteeUser = ((Mentee) this.currentUser.user);
        supportSession.topic = topic;
        this.supportSessionSingleton.addNewSessionToList(supportSession);

    }

    // changed the current singleton
    public void addUserDetailsToSingleton() {
        this.currentUser.setUser(repo.selectById(this.currentUser.name));
    }

    public SupportSession[] getSessionList() {
        return this.supportSessionSingleton.getSupportSessionsList();
    }

    public void cancelSession(SupportSession session) {
        session.mentorUser = null;
        session.overseer = ((Administrator) this.currentUser.user);
        session.mentorTime = null;
    }

    /**
     * Retrieves all the mentors from the repository.
     *
     * @return Array of Mentor objects.
     */
    public Mentor[] getMentorsArray() {
        // Get all users from the repository
        List<User> allUsers = repo.selectAll();

        // Filter out mentors and collect them in an array
        List<Mentor> mentorsList = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getRole() == UserRole.MENTOR) {
                mentorsList.add((Mentor) user);  // Safe to cast since the role is MENTOR
            }
        }

        // Convert the list of mentors to an array and return
        return mentorsList.toArray(new Mentor[0]);
    }
}
