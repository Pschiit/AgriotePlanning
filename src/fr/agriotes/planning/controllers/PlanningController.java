package fr.agriotes.planning.controllers;

import fr.agriotes.planning.dao.CatalogueDao;
import fr.agriotes.planning.models.Catalogue;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;

public class PlanningController {
    @FXML
    Catalogue catalogue;
    
    @FXML
    private void initialize(){
        System.out.println("Planning loading");
        try {
            catalogue = CatalogueDao.getPlanning();
        } catch (SQLException ex) {
            Logger.getLogger(PlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catalogue.afficheCatalogue();
    }
}
