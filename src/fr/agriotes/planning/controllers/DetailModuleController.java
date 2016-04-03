package fr.agriotes.planning.controllers;

import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Seance;
import fr.agriotes.planning.models.SeanceCell;
import fr.agriotes.planning.models.Session;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import fr.agriotes.planning.services.PlanningServices;

public class DetailModuleController {

    private PlanningServices planningServices;
    private Module module;
    private Session session;
    private List<Seance> seances;

    public PlanningServices getPlanningServices() {
        return planningServices;
    }

    public void setPlanningServices(PlanningServices planningServices) {
        this.planningServices = planningServices;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
        loadSeances();
    }

    @FXML
    private Label labelModule;
    @FXML
    private Label labelSession;
    @FXML
    private Label labelJour;
    @FXML
    private ListView listeSeances;

    @FXML
    public void initialize(Module module, Session session, List<Seance> seances) {
        this.module = module;
        this.session = session;
        this.seances = seances;
        paint();
    }

    private void paint() {
        labelModule.setText(module.getIntitule());
        labelSession.setText(session.toString());
        loadSeances();
    }

    private void loadSeances() {
        int joursPlanifies = 0;
        ObservableList<Seance> seancesObservables = FXCollections.observableArrayList();
        if (seances != null) {
            joursPlanifies = seances.size();
            for (Seance uneSeance : seances) {
                seancesObservables.add(uneSeance);
            }
            listeSeances.setItems(seancesObservables);
            listeSeances.setCellFactory(new Callback<ListView<Seance>, ListCell<Seance>>() {
                @Override
                public ListCell<Seance> call(ListView<Seance> param) {
                    SeanceCell cell = new SeanceCell();
                    cell.setEvent(planningServices);
                    return cell;
                }
            });
        } else {
            listeSeances.getItems().clear();
        }
        labelJour.setText("Nombre de jours plannifés : " + joursPlanifies + " / " + module.getNombreJoursTotal());
    }

    public void removeSeances(Seance seance) {
        seances.remove(seance);
        listeSeances.getItems().remove(seance);
        labelJour.setText("Nombre de jours plannifés : " + (seances != null ? seances.size() : 0) + " / " + module.getNombreJoursTotal());
    }
}
