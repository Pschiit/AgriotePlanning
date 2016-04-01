package fr.agriotes.planning.models;

import fr.agriotes.planning.services.CalendrierControllerServices;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class CalendarCell extends HBox {

    private Date date;
    private Seance seance;
    private Label labelNumero;
    private Label labelJour;
    private Label labelSeance;

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        if (seance != null) {
            this.seance = seance;
            labelSeance.setText(seance.toStringShort());
            Color[] colors = Color.values();
            this.setStyle("-fx-background-color:" + colors[seance.getModule().getId() % colors.length]);
        }else{
            this.seance = null;
            labelSeance.setText("");
            this.setStyle(null);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        labelNumero.setText(String.valueOf(date.getJour()));
        labelJour.setText(Date.JOUR.values()[date.getDay()].toString().charAt(0) + "");
    }

    public Label getLabelJour() {
        return labelJour;
    }

    public void setLabelJour(Label labelJour) {
        this.labelJour = labelJour;
    }

    public Label getLabelSeance() {
        return labelSeance;
    }

    public void setLabelSeance(Label labelSeance) {
        this.labelSeance = labelSeance;
    }

    public Label getLabelNumero() {
        return labelNumero;
    }

    public void setLabelNumero(Label labelNumero) {
        this.labelNumero = labelNumero;
    }

    public CalendarCell() {
        super();
        this.getStyleClass().add("calendrier-cell");
        labelNumero = new Label();
        labelJour = new Label();
        labelSeance = new Label();
        labelNumero.setMinWidth(15);
        labelJour.setMinWidth(15);
        labelSeance.setMinWidth(40);
        this.getChildren().addAll(labelNumero, labelJour, labelSeance);
        labelNumero.getStyleClass().add("jour-cell");
        labelJour.getStyleClass().add("jour-cell");
        labelSeance.getStyleClass().add("seance-cell");
    }

    public CalendarCell(Date date) {
        this();
        setDate(date);
    }

    public CalendarCell(Seance seance) {
        this();
        setDate(seance.getDate());
        setSeance(seance);
    }

    public void setEvent(final CalendrierControllerServices calendrierControllerServices) {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //Selectionne la Session à planifier
            @Override
            public void handle(MouseEvent event) {
                if (seance != null) {
                    calendrierControllerServices.fenetreModule(seance.getModule(), seance.getSession());
                }
            }
        });
    }
}
