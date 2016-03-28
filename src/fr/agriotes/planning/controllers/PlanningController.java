package fr.agriotes.planning.controllers;

import fr.agriotes.planning.services.CalendrierService;
import fr.agriotes.planning.services.CatalogueService;
import fr.agriotes.planning.dao.CatalogueDao;
import fr.agriotes.planning.dao.SeanceDao;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Planning;
import fr.agriotes.planning.models.Seance;
import fr.agriotes.planning.models.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PlanningController implements CatalogueService, CalendrierService{

    private Planning planning = new Planning();

    @FXML
    private Button signOutButton;
    @FXML
    private AnchorPane cataloguePane;
    @FXML
    private CatalogueController cataloguePaneController;
    @FXML
    private AnchorPane calendrierAnnuelPane;
    @FXML
    private CalendrierAnnuelController calendrierAnnuelPaneController;

    @FXML
    private void initialize() {
        cataloguePaneController.setCatalogueService(this);
        LoadCatalogue();
        calendrierAnnuelPaneController.setCalendrierService(this);
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
    protected void handleRefreshAction(ActionEvent event) {
        LoadCatalogue();
    }

    public void LoadCatalogue() {
        assert cataloguePaneController != null : "catalogueController null";
        CatalogueDao catalogueDao = new CatalogueDao();
        try {
            planning.setCatalogue(catalogueDao.getCatalogue());
            cataloguePaneController.setCatalogue(planning.getCatalogue());
        } catch (SQLException ex) {
            Logger.getLogger(PlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCalendrierAnnuel(Session sessionSelectionnee) {
        assert calendrierAnnuelPaneController != null : "calendrierAnnuelPaneController null";
        SeanceDao seanceDao = new SeanceDao();
        try {
            planning.setLesSeances(seanceDao.getSeancesByIdSession(sessionSelectionnee.getId()));
            calendrierAnnuelPaneController.setLesSeances(planning.getLesSeances());
        } catch (SQLException ex) {
            Logger.getLogger(PlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
        calendrierAnnuelPaneController.setSessionSelectionnee(sessionSelectionnee);
    }

    @Override
    public void setSessionSelectionnee(Session sessionSelectionnee) {
        loadCalendrierAnnuel(sessionSelectionnee);
    }

    @Override
    public void setModuleSelectionne(Module moduleSelectionne) {
        calendrierAnnuelPaneController.setModuleSelectionne(moduleSelectionne);
    }

    private void goToLogin() throws IOException {
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fr/agriotes/planning/views/Login.fxml"));
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.show();
    }

    @Override
    public Seance addSeance(Seance seance) {
        SeanceDao seanceDao = new SeanceDao();
        try {
            Seance result = seanceDao.addSeance(seance);
            planning.addSeance(result);
        } catch (SQLException ex) {
            Logger.getLogger(PlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return seance;
    }
}
