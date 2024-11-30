package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import uk.ac.nott.cs.comp2013.mentorapp.controller.MainPageController;

public class MainPageView extends VBox implements ManagedView {


    // fis this controller
    private final MainPageController controller;
    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;

    public MainPageView(MainPageController controller) {
        this.controller = controller;
        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);
        buildView();
    }


    private void buildView() {


        Text userNameLabel = new Text("MainPage");

        Button btnHello = new Button("Hello");

        // Set padding between elements
        setSpacing(10); // Adjust the value for more or less space




        btnHello.setOnAction(e -> {

                System.out.println("Hello");

        });

        getChildren().addAll(userNameLabel, btnHello);
    }






    @Override
    public EventHandler<? super ViewChangeEvent> getOnViewChange() {
        return onViewChange.get();
    }

    @Override
    public void setOnViewChange(EventHandler<? super ViewChangeEvent> eventHandler) {
        onViewChange.set(eventHandler);
    }
}
