package fr.agriotes.planning.controllers;

import fr.agriotes.planning.dao.PersonneDao;
import fr.agriotes.planning.models.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Text actiontarget;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void handleSignInAction(ActionEvent event) {
        try {
            Personne utilisateur = PersonneDao.getByEmailPassword(emailField.getText(), passwordField.getText());
            if (utilisateur != null) {
                if (utilisateur.isAdmin()) {
                    Stage stage = (Stage) actiontarget.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/fr/agriotes/planning/views/Planning.fxml"));
                    stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
                    stage.show();
                } else {
                    actiontarget.setText("Vous n'etez pas autorisé(e) à modifier le planning.");
                }
            } else {
                actiontarget.setText("Email ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            actiontarget.setText(e.getMessage());
        }
        passwordField.setText("");
    }

}
