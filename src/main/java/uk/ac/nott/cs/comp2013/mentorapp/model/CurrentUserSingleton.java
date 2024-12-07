package uk.ac.nott.cs.comp2013.mentorapp.model;

import uk.ac.nott.cs.comp2013.mentorapp.model.user.Administrator;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

import java.util.Optional;

public class CurrentUserSingleton {

    public String name;
    public User user;
    public boolean isActiveSession = false;
    public boolean isPersonalPageCreated = false;

    private static final CurrentUserSingleton singleton = new CurrentUserSingleton();

    private CurrentUserSingleton() {
    }

    public static CurrentUserSingleton getInstance() {
        return singleton;
    }


    //changed
    public void setUser(Optional<User> userOptional) {
        userOptional.ifPresent(user -> {
            if (user instanceof Mentee) {
                this.user = (Mentee) user;
                // Do something with the Mentee
            } else if (user instanceof Mentor) {
                this.user = (Mentor) user;
                // Do something with the Mentor
            } else if (user instanceof Administrator) {
                this.user = (Administrator) user;
                // Do something with the Administrator
            }
        });
    }


}
