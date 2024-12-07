package uk.ac.nott.cs.comp2013.mentorapp.view;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.mentorapp.MentorApp;

// changed
import java.net.URL;

/**
 * <p>
 * A {@code ViewManager} is responsible for changing the scene currently presented in an
 * application's main stage. It is designed to work with implementations of {@link ManagedView} so
 * that views can be changed without having to pass references to this class around.
 * </p>
 *
 * <p>
 * Views are added to the manager using the {@link #addView(String, Parent)} method. The first
 * argument is a string used to identify the view, and the second argument is the view itself.  Note
 * that the provided view must extend {@link javafx.scene.Parent} or one of its subclasses like
 * {@code VBox}. The provided string can later be used to set the view using
 * {@link #setStageView(String)}. It is best practice to use string constants for these view names,
 * such as the provided {@link #LOGIN} and {@link #DUMMY} constants.
 * </p>
 *
 * <p>
 * An example of registering and setting a view is available in {@link MentorApp#start(Stage)}. You
 * should not normally need to manually change view; instead, refer to {@code ManagedView} and the
 * explanation for triggering view change events. When a new view is added to a {@code ViewManager},
 * that view's view change handler is automatically configured to trigger a view change when a view
 * change event is raised.
 * </p>
 */
public class ViewManager {

    public static final String LOGIN = "login_view";
    public static final String DUMMY = "dummy_view";

    //changed
    public static final String MAINPAGE = "main_page_view";
    public static final String PROFILEPAGE = "profile_page_view";

    private final Stage stage;
    private final Map<String, Scene> scenes = new HashMap<>();

    // changed
    // variable to add global css
    private final URL globalCss;

    /**
     * Construct a new manager.
     *
     * @param stage the stage used to display this manager's scenes
     */
    public ViewManager(Stage stage) {
        this.stage = stage;
        // changed
        this.stage.setTitle("Mentor Application");
        // changed
        // load global css from resources
        globalCss = getClass().getResource("/style.css");
    }

    /**
     * Add a new view to the manager.
     *
     * @param key  unique key to identify the scene
     * @param view view to add
     */
    public <T extends Parent & ManagedView> void addView(String key, T view) {
        view.setOnViewChange(e -> setStageView(e.getView()));

        // changed
        // global css added here
        // Create scene
        Scene scene = new Scene(view);
        // Add global CSS if available
        if (globalCss != null) {
            scene.getStylesheets().add(globalCss.toExternalForm());
        } else {
            System.out.println("Global CSS file not found!");
        }
        scenes.put(key, scene);
        // global css
        //  commented to test global
        // scenes.put(key, new Scene(view));
    }

    /**
     * Set which view should be displayed on the stage. Will do nothing if the given key does not
     * exist.
     *
     * @param key key of scene to display
     */
    public void setStageView(String key) {
        if (!scenes.containsKey(key)) {
            return;
        }
        Scene s = scenes.get(key);
        stage.setScene(s);


        // changed
        // mevcut viewi al
        Parent currentView = s.getRoot(); // mevcut viewe ulaşır
        // view içindeki onShowHook() methodunu çağır
        if (currentView instanceof ManagedView) {  // Manged View interfaceine onShowHook ekli böylece methodun varlığı kesin
            ((ManagedView) currentView).onShowHook();
        }

    }

}
