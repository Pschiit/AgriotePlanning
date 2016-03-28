package fr.agriotes.planning.models;

import javafx.scene.control.Label;

public class SeanceCell extends Label {

    private Date date;
    private Seance seance;

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SeanceCell() {
        super();
    }

    public SeanceCell(Date date) {
        super();
        this.date = date;
    }

    public SeanceCell(Seance seance) {
        super();
        this.date = seance.getDate();
        this.seance = seance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SeanceCell other = (SeanceCell) obj;
        if (seance == null) {
            if (!this.date.equals(other.date)) {
                return false;
            }
        }
        else{
            if (!this.date.equals(other.date)) {
                return false;
            }
            if (!this.seance.equals(other.seance)) {
                return false;
            }
        }
        return true;
    }
}
