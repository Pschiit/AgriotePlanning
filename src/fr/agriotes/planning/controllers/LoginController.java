package fr.agriotes.planning.controllers;

import fr.agriotes.planning.dao.PersonneDao;
import fr.agriotes.planning.models.Personne;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
    private CheckBox saveEmail;

    @FXML
    public void initialize() {
        String path = System.getProperty("user.dir") + "\\build\\email.txt";
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String line;
            if ((line = in.readLine()) != null) {
                emailField.setText(line);
                saveEmail.setSelected(true);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    protected void handleSignInAction(ActionEvent event
    ) {
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
        if (saveEmail.isSelected()) {
            saveEmailInFile();
        } else {
            eraseEmailSave();
        }
        passwordField.setText("");
    }

    private void saveEmailInFile() {
        eraseEmailSave();
        Path path = Paths.get(System.getProperty("user.dir") + "\\build\\email.txt");
        try {
            Files.createFile(path);
            BufferedWriter bwr = Files.newBufferedWriter(path,StandardCharsets.UTF_8, StandardOpenOption.WRITE);
            bwr.write(emailField.getText());
            bwr.close();
        } catch (IOException ex) {
            actiontarget.setText(ex.getMessage());
        }
    }

    private void eraseEmailSave() {
        Path path = Paths.get(System.getProperty("user.dir") + "\\build\\email.txt");
        try {
            Files.delete(path);
        } catch (IOException ex) {
            actiontarget.setText(ex.getMessage());
        }
    }

}
