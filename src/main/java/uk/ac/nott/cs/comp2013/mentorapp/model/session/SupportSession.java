package uk.ac.nott.cs.comp2013.mentorapp.model.session;

import uk.ac.nott.cs.comp2013.mentorapp.model.user.Administrator;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;

import java.time.LocalDateTime;

public class SupportSession {

    public int id;
    public Mentee menteeUser = null;
    public Mentor mentorUser = null;
    public Administrator overseer = null;
    public String topic = null;
    public String mentorTime = null; // New field to store the session time
}
