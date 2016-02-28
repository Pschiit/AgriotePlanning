package fr.agriotes.planning.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AgriotePlanning extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fr/agriotes/planning/views/Login.fxml"));
        
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Agriote Planning");
        stage.setScene(scene);
        stage.show();
    }  
}
