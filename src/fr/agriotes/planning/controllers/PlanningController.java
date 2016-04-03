package fr.agriotes.planning.controllers;

import fr.agriotes.planning.dao.CatalogueDao;
import fr.agriotes.planning.dao.SeanceDao;
import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.FenetreErreur;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.List;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import fr.agriotes.planning.services.CalendrierServices;
import fr.agriotes.planning.services.CatalogueServices;
import fr.agriotes.planning.services.PlanningServices;
import java.util.ArrayList;

public class PlanningController implements CatalogueServices, PlanningServices {

    final private Planning planning = new Planning();
    private Session sessionSelectionnee;
    private Module moduleSelectionne;
    private Formateur formateurSelectionne;
    final private CatalogueDaoServices catalogueDao = new CatalogueDao();
    final private SeanceDaoServices seanceDao = new SeanceDao();
    private DetailModuleController detailModuleController;

    public Session getSessionSelectionnee() {
        return sessionSelectionnee;
    }

    public void setSessionSelectionnee(Session sessionSelectionnee) {
        this.sessionSelectionnee = sessionSelectionnee;
    }

    public Module getModuleSelectionne() {
        return moduleSelectionne;
    }

    public void setModuleSelectionne(Module moduleSelectionne) {
        this.moduleSelectionne = moduleSelectionne;
    }

    public Formateur getFormateurSelectionne() {
        return formateurSelectionne;
    }

    public void setFormateurSelectionne(Formateur formateurSelectionne) {
        this.formateurSelectionne = formateurSelectionne;
    }

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
        calendrierAnnuelPaneController.setPlanningServices(this);
        LoadCatalogue();
    }

    @FXML
    protected void handleSignOutAction(ActionEvent event) {
        try {
            goToLogin();
        } catch (IOException ex) {
            new FenetreErreur("IO Exception", ex.getMessage());
        }
    }

    @FXML
    protected void handleRefreshAction(ActionEvent event) {
        LoadCatalogue();
    }

    private void goToLogin() throws IOException {
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fr/agriotes/planning/views/Login.fxml"));
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.show();
    }

    @Override
    public Seance addSeance(Date date) {
        StringBuilder log = new StringBuilder();
        Seance result = null;
        boolean error = false;
        int nbSeance = 0;
        if (sessionSelectionnee == null) {
            log.append("Aucune session n'a été choisi.\n");
            error = true;
        }
        if (moduleSelectionne == null) {
            log.append("Aucun module n'a été choisi.\n");
            error = true;
        }
        if (formateurSelectionne == null) {
            log.append("Aucun formateur n'a été choisi.\n");
            error = true;
        }
        for (Seance uneSeance : planning.getLesSeances()) {
            if (uneSeance.getSession() == sessionSelectionnee
                    && uneSeance.getModule() == moduleSelectionne) {
                nbSeance++;
                if (nbSeance >= moduleSelectionne.getNombreJoursTotal()) {
                    log.append("Toutes les journées ont été programmées pour ce module.\n");
                    error = true;
                    break;
                }
            }

        }
        if (error) {
            new FenetreErreur("La seance n'a pu été créée", log.toString());
        } else {
            try {
                List<SeanceRaw> seancesRaw = seanceDao.getSeancesByDate(date);
                if (seancesRaw != null) {
                    for (SeanceRaw seanceRaw : seancesRaw) {
                        if (seanceRaw.getIdFormateur() == formateurSelectionne.getId() && seanceRaw.getIdModule() != moduleSelectionne.getId()) {
                            log.append("Le formateur "
                                    + planning.getCatalogue().getFormateur(seanceRaw.getIdFormateur())
                                    + " a déja une seance planifié le "
                                    + date
                                    + " sur le module "
                                    + planning.getCatalogue().getModule(seanceRaw.getIdModule())
                                    + ".\n");
                            new FenetreErreur("La seance n'a pu été créée", log.toString());
                            error = true;
                            break;
                        } else if (seanceRaw.getIdSession() == sessionSelectionnee.getId() && seanceRaw.getIdModule() != moduleSelectionne.getId()) {
                            log.append("La session "
                                    + planning.getCatalogue().getSession(seanceRaw.getIdSession())
                                    + " a déja une seance planifié le "
                                    + date
                                    + " sur le module "
                                    + planning.getCatalogue().getModule(seanceRaw.getIdModule())
                                    + ".\n");
                            new FenetreErreur("La seance n'a pu été créée", log.toString());
                            error = true;
                            break;
                        }
                    }
                }
                if (!error) {
                    result = seanceDao.addSeance(new Seance(0, sessionSelectionnee, moduleSelectionne, formateurSelectionne, date));
                    planning.addSeance(result);
                }
            } catch (SQLException ex) {
                log.append(ex.getMessage());
                new FenetreErreur("La seance n'a pu été créée", log.toString());
            }
        }
        return result;
    }

    @Override
    public Seance editSeance(Seance seance) {
        Seance result = null;
        CalendrierServices calendrier = calendrierAnnuelPaneController;
        try {
            result = seanceDao.updateSeance(seance);
            planning.editSeance(seance);
            calendrier.editCell(seance);
        } catch (SQLException ex) {
            new FenetreErreur("La seance n'a pu été supprimée", ex.getMessage());
        }
        return result;
    }

    @Override
    public Seance removeSeance(Seance seance) {
        Seance result = null;
        CalendrierServices calendrier = calendrierAnnuelPaneController;
        try {
            result = seanceDao.removeSeance(seance);
            planning.removeSeance(seance);
            detailModuleController.removeSeances(result);
            calendrier.removeCell(seance.getDate());
        } catch (SQLException ex) {
            new FenetreErreur("La seance n'a pu été supprimée", ex.getMessage());
        }
        return result;
    }

    @Override
    public void selectSession(Session sessionSelectionnee) {
        setSessionSelectionnee(sessionSelectionnee);
        setModuleSelectionne(null);
        setFormateurSelectionne(null);
        loadCalendrierAnnuel(sessionSelectionnee);
    }

    @Override
    public void selectModule(Module moduleSelectionne) {
        this.moduleSelectionne = moduleSelectionne;
    }

    @Override
    public void selectFormateur(Formateur formateurSelectionne) {
        this.formateurSelectionne = formateurSelectionne;
    }

    @Override
    public void LoadCatalogue() {
        assert cataloguePaneController != null : "catalogueController null";
        try {
            planning.setCatalogue(catalogueDao.getCatalogue());
            cataloguePaneController.initialize(planning.getCatalogue());
        } catch (SQLException ex) {
            new FenetreErreur("SQL Exception", ex.getMessage());
        }
    }

    @Override
    public void loadCalendrierAnnuel(Session sessionSelectionnee) {
        assert calendrierAnnuelPaneController != null : "calendrierAnnuelPaneController null";
        try {
            planning.setLesSeancesFromRaw(seanceDao.getSeancesByIdSession(sessionSelectionnee.getId()));
            calendrierAnnuelPaneController.setLesSeances(planning.getLesSeances());
            calendrierAnnuelPaneController.initialize(sessionSelectionnee);
        } catch (SQLException ex) {
            new FenetreErreur("SQL Exception", ex.getMessage());
        }
    }

    @Override
    public void loadDetailModule(Module module, Session session) {
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
            detailModuleController.setPlanningServices(this);
            List<Seance> seances = new ArrayList<>();
            if (session.equals(sessionSelectionnee)) {
                seances = planning.getSeancesByModuleSession(module, session);
            } else {
                List<SeanceRaw> seancesRaw = seanceDao.getSeancesByIdSession(session.getId());
                for (SeanceRaw uneSeanceRaw : seancesRaw) {
                    seances.add(planning.convertSeanceRawtoSeance(uneSeanceRaw));
                }
            }
            detailModuleController.initialize(module, session, seances);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (SQLException ex) {
           new FenetreErreur("SQL Exception", ex.getMessage());
        } catch (IOException ex) {
            new FenetreErreur("IO Exception", ex.getMessage());
        }
    }
}
