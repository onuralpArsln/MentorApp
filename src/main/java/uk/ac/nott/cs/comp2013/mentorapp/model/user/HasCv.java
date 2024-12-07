package uk.ac.nott.cs.comp2013.mentorapp.model.user;

/**
 * Interface for any entity that can have a CV.
 */
public interface HasCv {

  String getCvText();

  void setCvText(String text);
}
