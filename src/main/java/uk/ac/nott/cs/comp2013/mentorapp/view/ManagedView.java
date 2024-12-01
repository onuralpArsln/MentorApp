package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * <p>
 * Interface for any class which a {@link ViewManager} can manage. The interface methods enable the
 * {@code ViewManager} to set an event handler to automatically change view in response to an
 * event.
 * </p>
 * <p>
 * An implementing class will need a private member of type
 * {@code EventHandler<? super ViewChangeEvent>}. This may be wrapped in an
 * {@link javafx.beans.property.ObjectProperty} as in the example {@link LoginView} to match common
 * JavaFX implementations.
 * </p>
 * <p>
 * Event handlers have a method called {@link EventHandler#handle(Event)} which takes an event
 * object and carries out the specified action. See the code in the {@code onAction} handler in
 * {@code LoginView}'s login button for an example of how to raise an event and cause a view
 * change.
 * </p>
 */
public interface ManagedView {

  /**
   * Get this view's current event handler.
   *
   * @return the event handler
   */
  EventHandler<? super ViewChangeEvent> getOnViewChange();

  /**
   * Set this view's event handler
   *
   * @param eventHandler the event handler
   */
  void setOnViewChange(EventHandler<? super ViewChangeEvent> eventHandler);


  //changed
  void onShowHook();
}
