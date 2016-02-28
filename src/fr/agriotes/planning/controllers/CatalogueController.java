package fr.agriotes.planning.controllers;

import fr.agriotes.planning.dao.CatalogueDao;
import fr.agriotes.planning.models.Catalogue;
import fr.agriotes.planning.models.Session;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class CatalogueController {

    @FXML
    Catalogue catalogue;
    @FXML
    VBox vBox;

    @FXML
    public void initialize() {
        System.out.println("Planning loading");
        try {
            catalogue = CatalogueDao.getPlanning();
            vBox.getChildren().clear();
            for (Map.Entry<Integer, Session> entry : catalogue.getLesSessions().entrySet()) {
                int key = entry.getKey();
                Session laSession = entry.getValue();

                TitledPane titledPane = new TitledPane();
                titledPane.setText(laSession.toString());
                vBox.getChildren().add(titledPane);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
