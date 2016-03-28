package fr.agriotes.planning.models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ModuleCell extends ListCell<Module> {

    HBox hbox = new HBox();
    Label label = new Label("(empty)");
    Pane pane = new Pane();
    Button button = new Button();
    Module lastItem;

    public ModuleCell() {
        super();
        hbox.getChildren().addAll(label, pane, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
        label.setPadding(new Insets(6.0, 0.0, 0.0, 0.0));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(lastItem + " : " + event);
            }
        });
        ImageView image = new ImageView(new Image("/fr/agriotes/planning/content/img/News-s.png"));
        image.setFitWidth(25.0);
        image.setFitHeight(25.0);
        button.setGraphic(image);
    }

    @Override
    protected void updateItem(Module item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            label.setText(item != null ? item.toString() : "<null>");
            setGraphic(hbox);
        }
        if (item != null) {
            Color[] colors = Color.values();
            button.setStyle("-fx-background-color:" + colors[item.getId() % colors.length]);
        }
    }
}
