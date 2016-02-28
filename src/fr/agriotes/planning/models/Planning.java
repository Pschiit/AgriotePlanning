package fr.agriotes.planning.models;

import java.util.HashMap;

public class Planning {
    private HashMap<Integer, Seance> lesSeances = new HashMap<>();
    private Catalogue catalogue;

    public Planning() {
    }

    public Planning(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public HashMap<Integer, Seance> getLesSeances() {
        return lesSeances;
    }

    public void setLesSeances(HashMap<Integer, Seance> lesSeances) {
        this.lesSeances = lesSeances;
    }
}
