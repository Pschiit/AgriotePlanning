package fr.agriotes.planning.models;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FenetreErreur {
    public FenetreErreur(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/fr/agriotes/planning/content/img/Delete-s.png"));
        alert.showAndWait();
    }
}
