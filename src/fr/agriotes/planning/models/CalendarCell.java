package fr.agriotes.planning.models;

import javafx.scene.control.Label;
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
        this.seance = seance;
        labelSeance.setText(seance.toStringShort());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        labelNumero.setText(String.valueOf(date.getJour()));
        labelJour.setText(Date.JOUR[date.getDay() + 1].charAt(0) + "");
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
        this.getChildren().addAll(labelNumero,labelJour,labelSeance);
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
}
