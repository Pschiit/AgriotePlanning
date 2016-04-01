package fr.agriotes.planning.controllers;

import fr.agriotes.planning.dao.CatalogueDao;
import fr.agriotes.planning.dao.SeanceDao;
import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.Formateur;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Planning;
import fr.agriotes.planning.models.Seance;
import fr.agriotes.planning.models.SeanceRaw;
import fr.agriotes.planning.models.Session;
import fr.agriotes.planning.services.CatalogueDaoServices;
import fr.agriotes.planning.services.SeanceDaoServices;
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
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import fr.agriotes.planning.services.DetailModuleControllerServices;
import fr.agriotes.planning.services.CatalogueControllerServices;
import fr.agriotes.planning.services.CalendrierControllerServices;

public class PlanningController implements CatalogueControllerServices, CalendrierControllerServices, DetailModuleControllerServices {

    final private Planning planning = new Planning();
    final private CatalogueDaoServices catalogueDao = new CatalogueDao();
    final private SeanceDaoServices seanceDao = new SeanceDao();
    private DetailModuleController detailModuleController;

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
        cataloguePaneController.setCatalogueControllerService(this);
        calendrierAnnuelPaneController.setCalendrierControllerService(this);
        LoadCatalogue();
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
        try {
            planning.setCatalogue(catalogueDao.getCatalogue());
            cataloguePaneController.setCatalogue(planning.getCatalogue());
        } catch (SQLException ex) {
            Logger.getLogger(PlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCalendrierAnnuel(Session sessionSelectionnee) {
        assert calendrierAnnuelPaneController != null : "calendrierAnnuelPaneController null";
        try {
            planning.setLesSeancesFromRaw(seanceDao.getSeancesByIdSession(sessionSelectionnee.getId()));
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

    @Override
    public void setFormateurSelectionne(Formateur formateurSelectionne) {
        calendrierAnnuelPaneController.setFormateurSelectionne(formateurSelectionne);
    }

    @Override
    public Seance addSeance(Seance seance) {
        StringBuilder log = new StringBuilder();
        Seance result = null;
        boolean error = false;
        int nbSeance = 0;
        if (calendrierAnnuelPaneController.getSessionSelectionnee() == null) {
            log.append("Aucune session n'a été choisi.\n");
            error = true;
        }
        if (calendrierAnnuelPaneController.getModuleSelectionne() == null) {
            log.append("Aucun module n'a été choisi.\n");
            error = true;
        }
        if (calendrierAnnuelPaneController.getFormateurSelectionne() == null) {
            log.append("Aucun formateur n'a été choisi.\n");
            error = true;
        }
        for (Seance uneSeance : planning.getLesSeances()) {
            if (uneSeance.getSession() == seance.getSession()
                    && uneSeance.getModule() == seance.getModule()) {
                nbSeance ++;
                if(nbSeance >= seance.getModule().getNombreJoursTotal()){
                    log.append("Toutes les journées ont été programmées pour ce module.\n");
                    error = true;
                    break;
                }
            }

        }
        if (error) {
            fenetreErreur("La seance n'a pu été créée", log.toString());
        } else {
            try {
                List<SeanceRaw> seancesRaw = seanceDao.getSeancesByDate(seance.getDate());
                if (seancesRaw != null) {
                    for (SeanceRaw seanceRaw : seancesRaw) {
                        if (seanceRaw.getIdFormateur() == seance.getFormateur().getId() && seanceRaw.getIdModule() != seance.getModule().getId()) {
                            log.append("Le formateur "
                                    + planning.getCatalogue().getFormateur(seanceRaw.getIdFormateur())
                                    + " a déja une seance planifié le "
                                    + seance.getDate()
                                    + " sur le module "
                                    + planning.getCatalogue().getModule(seanceRaw.getIdModule())
                                    + ".\n");
                            fenetreErreur("La seance n'a pu été créée", log.toString());
                            error = true;
                            break;
                        } else if (seanceRaw.getIdSession() == seance.getSession().getId() && seanceRaw.getIdModule() != seance.getModule().getId()) {
                            log.append("La session "
                                    + planning.getCatalogue().getSession(seanceRaw.getIdSession())
                                    + " a déja une seance planifié le "
                                    + seance.getDate()
                                    + " sur le module "
                                    + planning.getCatalogue().getModule(seanceRaw.getIdModule())
                                    + ".\n");
                            fenetreErreur("La seance n'a pu été créée", log.toString());
                            error = true;
                            break;
                        }
                    }
                }
                if (!error) {
                    result = seanceDao.addSeance(seance);
                    planning.addSeance(result);
                }
            } catch (SQLException ex) {
                log.append(ex.getMessage());
                fenetreErreur("La seance n'a pu été créée", log.toString());
            }
        }
        return result;
    }

    @Override
    public Seance editSeance(Seance seance) {
        StringBuilder log = new StringBuilder();
        Seance result = null;
        try {
            result = seanceDao.updateSeance(seance);
            planning.editSeance(seance);
            calendrierAnnuelPaneController.editCell(seance);
        } catch (SQLException ex) {
            log.append(ex.getMessage());
            fenetreErreur("La seance n'a pu été modifiée", log.toString());
        }
        return result;
    }

    @Override
    public Seance removeSeance(Seance seance) {
        StringBuilder log = new StringBuilder();
        Seance result = null;
        try {
            result = seanceDao.removeSeance(seance);
            planning.removeSeance(seance);
            detailModuleController.removeSeances(result);
            calendrierAnnuelPaneController.removeCell(seance.getDate());
        } catch (SQLException ex) {
            log.append(ex.getMessage());
            fenetreErreur("La seance n'a pu été supprimée", log.toString());
        }
        return result;
    }

    @Override
    public void fenetreModule(Module module, Session session) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/agriotes/planning/views/DetailModule.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Détail");
            dialogStage.getIcons().add(new Image("/fr/agriotes/planning/content/img/News-s.png"));
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner((Stage) signOutButton.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the controller
            detailModuleController = loader.getController();
            detailModuleController.setModuleServices(this);
            detailModuleController.initialize(module, session, planning.getSeanceByModuleSession(module, session));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void goToLogin() throws IOException {
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fr/agriotes/planning/views/Login.fxml"));
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.show();
    }

    private void fenetreErreur(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/fr/agriotes/planning/content/img/Delete-s.png"));
        alert.showAndWait();
    }
}
