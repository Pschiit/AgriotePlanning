package fr.agriotes.planning.controllers;

import fr.agriotes.planning.models.Constant;
import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Seance;
import fr.agriotes.planning.models.SeanceCell;
import fr.agriotes.planning.models.Session;
import fr.agriotes.planning.services.PlanningServices;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class CalendrierAnnuelController {

    private PlanningServices planning;
    private Map<Integer, Seance> lesSeances;
    private Session sessionSelectionnee;
    private Module moduleSelectionne;

    public PlanningServices getPlanning() {
        return planning;
    }

    public void setPlanning(PlanningServices planning) {
        this.planning = planning;
    }

    public Map<Integer, Seance> getLesSeances() {
        return lesSeances;
    }

    public void setLesSeances(Map<Integer, Seance> lesSeances) {
        this.lesSeances = lesSeances;
    }

    public Session getSessionSelectionnee() {
        return sessionSelectionnee;
    }

    public void setSessionSelectionnee(Session sessionSelectionnee) {
        this.sessionSelectionnee = sessionSelectionnee;
        initialize();
    }

    public Module getModuleSelectionne() {
        return moduleSelectionne;
    }

    public void setModuleSelectionne(Module moduleSelectionne) {
        this.moduleSelectionne = moduleSelectionne;
    }

    @FXML
    private GridPane calendrierGridPane;
    @FXML
    private Label sessionTitle;

    @FXML
    public void initialize() {
        paint();
    }

    private void paint() {
        System.out.println("Calendrier annuel loading");
        calendrierGridPane.getChildren().clear();
        if (sessionSelectionnee == null) {
            sessionTitle.setText("Veuillez choisir une session.");
        } else {
            sessionTitle.setText(sessionSelectionnee.toString());
            int nbMois = sessionSelectionnee.getNombreDeMois();
            int moisDebut = sessionSelectionnee.getDateDebut().getMois();
            for (int j = 0; j < 31; j++) {
                Label headerJour = new Label("" + (j + 1));
                headerJour.setPrefWidth(50.0);
                headerJour.setPrefHeight(20.0);
                headerJour.getStyleClass().add("calendrier-cell");
                calendrierGridPane.add(headerJour, 1, j + 2);
            }
            for (int i = 0; i < nbMois; i++) {
                int mois = (moisDebut + i - 1) % 12 + 1;
                Label headerMois = new Label(Constant.MOIS[mois]);
                headerMois.setPrefWidth(80.0);
                headerMois.setPrefHeight(20.0);
                headerMois.getStyleClass().add("calendrier-cell");
                calendrierGridPane.add(headerMois, i + 2, 1);
                for (int j = 0; j < 31; j++) {
                    //Inactive cell
                    if ((i == 0 && j + 1 < sessionSelectionnee.getDateDebut().getJour())) {//Jour avant le début{
                        calendrierGridPane.add(inactiveCell(), i + 2, j + 2);
                    } else if ((i == nbMois - 1 && j >= sessionSelectionnee.getDateFin().getJour())) {//Jour après la fin
                        calendrierGridPane.add(inactiveCell(), i + 2, j + 2);
                    } else if (mois == 2 && j > 27 && !(j == 28 && (moisDebut + i < 12 && sessionSelectionnee.getDateDebut().isBisextile()) || (moisDebut + i > 12 && sessionSelectionnee.getDateFin().isBisextile()))) {//Fevrier
                        calendrierGridPane.add(inactiveCell(), i + 2, j + 2);
                    } else if (j == 30 && (mois == 4 || mois == 6 || mois == 9 || mois == 11)) {//Mois de 30 jours
                        calendrierGridPane.add(inactiveCell(), i + 2, j + 2);
                    } //Empty cell
                    else {
                        int annee = moisDebut + i < 12 ? sessionSelectionnee.getDateDebut().getAnnee() : sessionSelectionnee.getDateFin().getAnnee();
                        SeanceCell cell = null;
                        if (lesSeances != null) {
                            for (Map.Entry<Integer, Seance> entry : lesSeances.entrySet()) {
                                Seance seance = entry.getValue();

                                Date dateSeance = seance.getDate();
                                if (dateSeance.getAnnee() == annee && dateSeance.getMois() == mois && dateSeance.getJour() == j + 1) {
                                    cell = seanceCell(seance);
                                    break;
                                }

                            }
                        }
                        if (cell == null) {
                            cell = emptyCell(new Date(annee, mois, j + 1));
                        }
                        calendrierGridPane.add(cell, i + 2, j + 2);
                    }
                }
            }
        }
    }

    private Label inactiveCell() {
        Label cell = new Label();
        cell.getStyleClass().addAll("calendrier-cell", "inactive-cell");
        cell.setPrefWidth(80.0);
        cell.setPrefHeight(20.0);
        return cell;
    }

    private SeanceCell emptyCell(final Date date) {
        SeanceCell cell = new SeanceCell(date);
        cell.setPrefWidth(80.0);
        cell.setPrefHeight(20.0);
        cell.getStyleClass().add("calendrier-cell");
        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //Selectionne la Session à planifier
            @Override
            public void handle(MouseEvent event) {
                System.out.println(date);
            }
        });
        return cell;
    }

    private SeanceCell seanceCell(final Seance seance) {
        SeanceCell cell = new SeanceCell(seance);
        cell.setText(seance.toStringShort());
        cell.setPrefWidth(80.0);
        cell.setPrefHeight(20.0);
        cell.getStyleClass().add("calendrier-cell");
        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //Affichage detaillé du module
            @Override
            public void handle(MouseEvent event) {
                System.out.println(seance);
            }
        });
        return cell;
    }
}
