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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import fr.agriotes.planning.services.DetailModuleControllerServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SeanceCell extends ListCell<Seance> {

    HBox hBox = new HBox();
    Label labelDate = new Label("(empty)");
    Pane[] pane = {new Pane(), new Pane()};
    Button button = new Button();
    ComboBox<Formateur> formateurs = new ComboBox<>();
    Seance lastItem;

    public SeanceCell() {
        super();
        hBox.getChildren().addAll(labelDate, pane[0], formateurs, pane[1], button);
        HBox.setHgrow(pane[0], Priority.ALWAYS);
        pane[1].setPadding(new Insets(0.0, 6.0, 0.0, 0.0));
        ImageView minus = new ImageView(new Image("/fr/agriotes/planning/content/img/Minus-s.png"));
        minus.setFitWidth(25.0);
        minus.setFitHeight(25.0);
        button.setGraphic(minus);
    }
    
    @Override
    protected void updateItem(Seance item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            if (item != null) {
                List<Formateur> f = item.getModule().getFormateurs();
                if (f != null) {
                    ObservableList<Formateur> formateursObservables = FXCollections.observableArrayList();
                    for (Formateur unFormateur : f) {
                        formateursObservables.add(unFormateur);
                    }
                    formateurs.setItems(formateursObservables);
                    formateurs.setValue(item.getFormateur());
                }
                labelDate.setText(item.getDate().toString());
            }
            setGraphic(hBox);
        }
    }
    
    public void setEvent(final DetailModuleControllerServices detailModuleControllerServices){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                detailModuleControllerServices.removeSeance(lastItem);
            }
        });
        
        formateurs.valueProperty().addListener(new ChangeListener<Formateur>() {
            @Override 
            public void changed(ObservableValue ov, Formateur oldValue, Formateur newValue) {   
                if(newValue != null && newValue != lastItem.getFormateur()){
                    lastItem.setFormateur(newValue);
                    detailModuleControllerServices.editSeance(lastItem);
                }
            }
        });
    }
}
