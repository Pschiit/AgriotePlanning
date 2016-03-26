package fr.agriotes.planning.controllers;

import fr.agriotes.planning.dao.CatalogueDao;
import fr.agriotes.planning.models.Catalogue;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Session;
import fr.agriotes.planning.models.ModuleCell;
import fr.agriotes.planning.services.CatalogueDaoServices;
import java.sql.SQLException;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class CatalogueController {

    private final CatalogueDaoServices catalogueDao = new CatalogueDao();
    private Catalogue catalogue;
    private Session sessionSelectionnee;
    private Module moduleSelectionne;

    public Session getSessionSelectionnee() {
        return sessionSelectionnee;
    }

    public void setSessionSelectionnee(Session sessionSelectionnee) {
        this.sessionSelectionnee = sessionSelectionnee;
        System.out.println("Formation selectionnée : " + sessionSelectionnee);
    }

    public Module getModuleSelectionne() {
        return moduleSelectionne;
    }

    public void setModuleSelectionne(Module moduleSelectionne) {
        this.moduleSelectionne = moduleSelectionne;
        System.out.println("Module selectionné : " + moduleSelectionne);
    }

    @FXML
    private Accordion accordion;

    @FXML
    public Accordion getAccordion() {
        return accordion;
    }

    @FXML
    public void initialize() {
        System.out.println("catalogue loading");
        try {
            catalogue = catalogueDao.getCatalogue();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        assert catalogue != null : "Catalogue null";
        accordion.getPanes().clear();
        boolean noSelection = sessionSelectionnee == null;
        for (Map.Entry<Integer, Session> entry : catalogue.getLesSessions().entrySet()) {
            final int key = entry.getKey();
            Session laSession = entry.getValue();

            //creation du TitledPane de la session + affichage si selectionné
            TitledPane titledPane = new TitledPane();
            titledPane.setText(laSession.toString());
            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setSessionSelectionnee(catalogue.getSession(key));
                }
            });
            if (noSelection) {
                accordion.setExpandedPane(titledPane);
                noSelection = false;
            } else if (laSession.equals(sessionSelectionnee)) {
                accordion.setExpandedPane(titledPane);
            }

            //creation de la ListView de moduleCell
            ObservableList<Module> modulesObservables = FXCollections.observableArrayList();
            for (Module unModule : laSession.getLesModules()) {
                modulesObservables.add(unModule);
            }
            ListView<Module> modulesDeLaSession = new ListView<>(modulesObservables);
            modulesDeLaSession.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Module>() {
                @Override
                public void changed(ObservableValue<? extends Module> observable, Module oldValue, Module newValue) {
                    setModuleSelectionne(newValue);
                }
            });
            modulesDeLaSession.setCellFactory(new Callback<ListView<Module>, ListCell<Module>>() {
                @Override
                public ListCell<Module> call(ListView<Module> param) {
                    return new ModuleCell();
                }
            });

            titledPane.setContent(modulesDeLaSession);

            accordion.getPanes().add(titledPane);
        }
    }
}
