package fr.agriotes.planning.models;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import fr.agriotes.planning.services.CatalogueServices;

public class ModuleCell extends ListCell<Module> {

    HBox hBox = new HBox();
    Label labelModule = new Label("(empty)");
    Pane[] pane = {new Pane(), new Pane()};
    Button button = new Button();
    ComboBox<Formateur> formateurs = new ComboBox<>();
    Module lastItem;

    public ModuleCell() {
        super();
        hBox.getChildren().addAll(labelModule, pane[0], formateurs, pane[1], button);
        HBox.setHgrow(pane[0], Priority.ALWAYS);
        pane[1].setPadding(new Insets(0.0, 6.0, 0.0, 0.0));
        labelModule.setPadding(new Insets(6.0, 0.0, 0.0, 0.0));
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
            if (item != null) {
                List<Formateur> f = item.getFormateurs();
                if (f != null) {
                    ObservableList<Formateur> formateursObservables = FXCollections.observableArrayList();
                    for (Formateur unFormateur : f) {
                        formateursObservables.add(unFormateur);
                    }
                    formateurs.setItems(formateursObservables);
                    formateurs.setValue(f.get(0));
                }
                labelModule.setText(item.toString());
                Color[] colors = Color.values();
                button.setStyle("-fx-background-color:" + colors[item.getId() % colors.length]);
            }
            setGraphic(hBox);
        }
    }

    public void setEvent(final CatalogueServices catalogueServices,final Session session) {
        //selection du module et du formateur choisi
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                catalogueServices.selectModule(lastItem);
                catalogueServices.selectFormateur(formateurs.getValue());
            }
        });
        
        //affichage detaille du module
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                catalogueServices.loadDetailModule(lastItem, session);
            }
        });
    }
}
