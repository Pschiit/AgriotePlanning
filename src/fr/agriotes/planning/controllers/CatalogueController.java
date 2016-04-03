package fr.agriotes.planning.controllers;

import fr.agriotes.planning.models.Catalogue;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Session;
import fr.agriotes.planning.models.ModuleCell;
import java.util.Map;
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
import fr.agriotes.planning.services.CatalogueServices;

public class CatalogueController {

    private Catalogue catalogue;
    private CatalogueServices catalogueControllerServices;

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public CatalogueServices getCatalogueControllerService() {
        return catalogueControllerServices;
    }

    public void setCatalogueControllerService(CatalogueServices catalogueControllerService) {
        this.catalogueControllerServices = catalogueControllerService;
    }

    @FXML
    private Accordion accordion;

    @FXML
    public void initialize(Catalogue catalogue) {
        this.catalogue = catalogue;
        if (catalogueControllerServices != null) {
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
                    catalogueControllerServices.selectSession(laSession);
                }
            });
            //Si pas de selection selectionne le premier
            if (titlePaneSelectionnee == null) {
                titlePaneSelectionnee = titledPane;
                catalogueControllerServices.selectSession(laSession);
            }

            //creation de la ListView de moduleCell
            ObservableList<Module> modulesObservables = FXCollections.observableArrayList();
            for (Module unModule : laSession.getLesModules()) {
                modulesObservables.add(unModule);
            }
            ListView<Module> modulesDeLaSession = new ListView<>(modulesObservables);
            modulesDeLaSession.setCellFactory(new Callback<ListView<Module>, ListCell<Module>>() {
                @Override
                public ListCell<Module> call(ListView<Module> param) {
                    ModuleCell cell = new ModuleCell();
                    cell.setEvent(catalogueControllerServices, laSession);
                    return cell;
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
