package fr.agriotes.planning.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PlanningController {

    @FXML
    private Button signOutButton;
    @FXML
    private AnchorPane cataloguePane;
    @FXML
    private CatalogueController cataloguePaneController;

    @FXML
    private void initialize() {
    }

    @FXML
    protected void handleSignOutAction(ActionEvent event) {
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fr/agriotes/planning/views/Login.fxml"));
            stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    protected void handleRefreshCatalogueAction(ActionEvent event) {
        if(cataloguePaneController != null)
            cataloguePaneController.initialize();
        else
            System.err.println("catalogueController null");
    }
}
