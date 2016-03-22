package fr.agriotes.planning.controllers;

import fr.agriotes.planning.dao.CatalogueDao;
import fr.agriotes.planning.models.Catalogue;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Session;
import java.sql.SQLException;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;

public class CatalogueController {

    @FXML
    private Accordion accordion;

    private Catalogue catalogue;
    private Session sessionSelectionnee;
    private Module moduleSelectionne;

    public Session getSessionSelectionnee() {
        return sessionSelectionnee;
    }

    public void setSessionSelectionnee(Session sessionSelectionnee) {
        this.sessionSelectionnee = sessionSelectionnee;
        System.out.println("Formation selectionné : " + sessionSelectionnee);
    }

    public Module getModuleSelectionne() {
        return moduleSelectionne;
    }

    public void setModuleSelectionne(Module moduleSelectionne) {
        this.moduleSelectionne = moduleSelectionne;
        System.out.println("Formation selectionné : " + moduleSelectionne);
    }

    @FXML
    public void initialize() {
        System.out.println("Planning loading");
        try {
            catalogue = CatalogueDao.getCatalogue();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        assert catalogue != null : "Catalogue null";
        accordion.getPanes().clear();
        for (Map.Entry<Integer, Session> entry : catalogue.getLesSessions().entrySet()) {
            final int key = entry.getKey();
            Session laSession = entry.getValue();

            //creation du TitledPane de la session + selection
            TitledPane titledPane = new TitledPane();
            titledPane.setText(laSession.toString());
            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setSessionSelectionnee(catalogue.getSessionById(key));
                }
            });

            //creation de la ListView de modules
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

            titledPane.setContent(modulesDeLaSession);

            accordion.getPanes().add(titledPane);
        }
    }
}
