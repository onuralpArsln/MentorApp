package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * A {@code ViewChangeEvent} is raised whenever a {@link ManagedView} wants to change view.
 */
public class ViewChangeEvent extends Event {

  private static final EventType<ViewChangeEvent> TYPE = new EventType<>("ViewChange");
  private final String view;

  /**
   * Construct a new event.
   *
   * @param view string key of the view to change to
   */
  public ViewChangeEvent(String view) {
    super(TYPE);
    this.view = view;
  }

  /**
   * Get the key of the view to display.
   *
   * @return view key
   */
  public String getView() {
    return view;
  }
}
