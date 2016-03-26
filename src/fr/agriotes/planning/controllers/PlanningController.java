package fr.agriotes.planning.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

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
        try {
            goToLogin();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    protected void handleRefreshCatalogueAction(ActionEvent event) {
        assert cataloguePaneController != null : "catalogueController null";
        cataloguePaneController.initialize();
        cataloguePaneController.setModuleSelectionne(null);
    }

    private void goToLogin() throws IOException {
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fr/agriotes/planning/views/Login.fxml"));
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.show();
    }
}
