package uk.ac.nott.cs.comp2013.mentorapp.controller;

import uk.ac.nott.cs.comp2013.mentorapp.model.CurrentUserSingleton;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;

import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

import java.time.LocalDateTime;


public class ProfilePageController {
    private final Repository<User, String> repo;
    private final CurrentUserSingleton currentUser;

    public ProfilePageController(Repository<User, String> model, CurrentUserSingleton currentUser) {
        this.repo = model;
        this.currentUser = currentUser;
    }


    public void updateMenteeData(String userName, String userPass, String cv) {
        Mentee user = new Mentee(userName, userPass, cv);
        repo.update(user);
    }


    public void updateMentorData(String userName, String userPass, String startAvailability, String endAvailability) {

        LocalDateTime startDateTime = LocalDateTime.parse(startAvailability);
        LocalDateTime endDateTime = LocalDateTime.parse(endAvailability);
        Mentor user = new Mentor(userName, userPass, startDateTime, endDateTime);
        repo.update(user);
    }
    
}
