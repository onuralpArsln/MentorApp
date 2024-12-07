# Mentor App Coursework

This repository is for Part 2 of the COMP2013 coursework, the
mentor scheme app. It has already been set up with some skeleton
code including an implementation of the model.

All code for the app should be created under the
`uk.ac.nott.cs.comp2013.mentorapp` package. Use appropriate
sub-packages to organise the code well. Application code goes
under `src/main/java` and tests go under `src/test/java`.

## Starting the coursework

1. Fork this repository into your own user space, like you did
   in PGA.
2. In the IntelliJ launch window, instead of starting a new project
   select "Get from VCS."
3. Enter the URL of your personal repository and a Gitlab personal
   access token in place of your password.
4. When the project opens, verify that you can successfully build
   the project.
5. Start work!

## Framework

The provided code is there to help you get started. If you believe
it is necessary you may modify or add to this structure, but you
will need to justify this in the report.

If you identify an actual bug somewhere in the provided code, you
can raise an issue on the original repository and Ian will
respond to it.

### Using the view manager

The `ViewManager` class is used to switch between scenes. Views that
can be switched should implement the `ManagedView` interface as well as
extending a chosen JavaFX layout (e.g. `VBox`). Using the view manager
requires you to add the views you want to manage, and trigger view
change events from within your views. Here are specific instructions:

1. **Registering views.** The `ViewManager#addView` method takes two arguments,
   a string label for the view and the view itself. It is best practice to define
   your labels as constants so that you only have to change them in one place (see
   the `ViewManager` class implementation for an example with the `DUMMY` constant).
2. **Changing views.** Views are changed by using `ViewChangeEvents`. The
   `ManagedView` interface requires each class to provide a getter and setter for an
   `EventHandler` that handles a `ViewChangeEvent`. The default `LoginView` has an
   `onViewChange` property that you can use as an example. You can copy this property and
   the two interface methods from `LoginView` into your own classes.

   To actually trigger a view change, you must do the following:

    1. Create a new `ViewChangeEvent`. The constructor takes one argument, which is the
       string label of the view you want to switch to.
    2. Get the view change handler from the property you created and check if it is null.
       If it is not null, you can then pass your `ViewChangeEvent` object as an argument to
       the event handler's `handle` method.

The `ViewManager` takes care of the actual mechanics of switching scenes. This architecture
is designed to reduce the coupling between individual views and the view manager---someone
programming an individual view doesn't actually need to know how views are switched, they
just need to trigger the appropriate event.
