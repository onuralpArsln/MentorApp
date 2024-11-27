package uk.ac.nott.cs.comp2013.mentorapp.model.user;

import java.time.LocalDateTime;

/**
 * Interface for any entity that has an availability.
 */
public interface HasAvailability {

  LocalDateTime getStartAvailability();

  void setStartAvailability(LocalDateTime date);

  LocalDateTime getEndAvailability();

  void setEndAvailability(LocalDateTime date);
}
