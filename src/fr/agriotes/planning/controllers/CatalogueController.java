package fr.agriotes.planning.controllers;

import fr.agriotes.planning.services.CatalogueService;
import fr.agriotes.planning.models.Catalogue;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Session;
import fr.agriotes.planning.models.ModuleCell;
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

    private Catalogue catalogue;
    private CatalogueService catalogueService;

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
        initialize();
    }

    public CatalogueService getCatalogueService() {
        return catalogueService;
    }

    public void setCatalogueService(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @FXML
    private Accordion accordion;

    @FXML
    public void initialize() {
        if (catalogueService != null) {
            paint();
        }
    }

    private void paint() {
        assert catalogue != null : "Catalogue null";
        System.out.println("catalogue loading");
        TitledPane titlePaneSelectionnee = accordion.getExpandedPane();
        accordion.getPanes().clear();
        for (Map.Entry<Integer, Session> entry : catalogue.getLesSessions().entrySet()) {
            final int key = entry.getKey();
            final Session laSession = entry.getValue();

            //creation du TitledPane de la session
            final TitledPane titledPane = new TitledPane();
            titledPane.setText(laSession.toString());
            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                //Selectionne la Session à planifier
                @Override
                public void handle(MouseEvent event) {
                    catalogueService.setSessionSelectionnee(laSession);
                }
            });
            //Si pas de selection selectionne le premier
            if (titlePaneSelectionnee == null) {
                titlePaneSelectionnee = titledPane;
                catalogueService.setSessionSelectionnee(laSession);
            }

            //creation de la ListView de moduleCell
            ObservableList<Module> modulesObservables = FXCollections.observableArrayList();
            for (Module unModule : laSession.getLesModules()) {
                modulesObservables.add(unModule);
            }
            ListView<Module> modulesDeLaSession = new ListView<>(modulesObservables);
            modulesDeLaSession.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Module>() {
                //Selectionne le module à planifier
                @Override
                public void changed(ObservableValue<? extends Module> observable, Module oldValue, Module newValue) {
                    CatalogueController.this.catalogueService.setModuleSelectionne(newValue);
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
            if (titledPane.getText().equals(titlePaneSelectionnee.getText())) {
                accordion.setExpandedPane(titledPane);
            }
        }
    }
}
