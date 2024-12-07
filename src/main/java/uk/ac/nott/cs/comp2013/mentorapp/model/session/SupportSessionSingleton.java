package uk.ac.nott.cs.comp2013.mentorapp.model.session;


public class SupportSessionSingleton {


    private SupportSession[] supportSessionsList = new SupportSession[100];
    private int sessionCount = -1;

    static private final SupportSessionSingleton supportSessionSingleton = new SupportSessionSingleton();

    private SupportSessionSingleton() {
    }

    public static SupportSessionSingleton getInstance() {
        return supportSessionSingleton;
    }


    public void addNewSessionToList(SupportSession session) {
        this.supportSessionsList[++sessionCount] = session;
        session.id = sessionCount;
    }

    public SupportSession[] getSupportSessionsList() {
        return supportSessionsList;
    }
}
