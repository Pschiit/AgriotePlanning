package fr.agriote.controllers;

import fr.agriote.dao.Database;
import fr.agriote.dao.PersonneDao;
import fr.agriote.models.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                    Parent root = FXMLLoader.load(getClass().getResource("/fr/agriote/views/Planning.fxml"));
                    stage.setScene(new Scene(root, 1280, 720));
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
