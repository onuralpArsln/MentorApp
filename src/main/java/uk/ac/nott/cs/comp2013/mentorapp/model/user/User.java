package uk.ac.nott.cs.comp2013.mentorapp.model.user;

import uk.ac.nott.cs.comp2013.mentorapp.model.HasId;

/**
 * Base interface for any user of the system. Uses the username as id.
 */
public interface User extends HasId<String> {

    default String getId() {
        return getUsername();
    }

    default void setId(String id) {
        setUsername(id);
    }

    UserRole getRole();

    void setRole(UserRole role);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

}
