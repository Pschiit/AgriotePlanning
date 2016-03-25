package fr.agriotes.planning.models;

import java.util.List;
import java.util.Objects;

public class Module {

    private int id;
    private String intitule;
    private int nombreJoursPlanifies;
    private int nombreJoursTotal;
    private List<Formateur> formateurs;

    public Module() {
    }

    public Module(int id, String intitule, int nombreJours, List<Formateur> formateurs) {
        this.id = id;
        this.intitule = intitule;
        this.nombreJoursTotal = nombreJours;
        setFormateurs(formateurs);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getNombreJoursTotal() {
        return nombreJoursTotal;
    }

    public void setNombreJoursTotal(int nombreJoursTotal) {
        this.nombreJoursTotal = nombreJoursTotal;
    }

    public List<Formateur> getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(List<Formateur> formateurs) {
        if (formateurs != null) {
            this.formateurs = formateurs;
        }
    }

    @Override
    public String toString() {
        return intitule;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.intitule);
        hash = 71 * hash + this.nombreJoursTotal;
        hash = 71 * hash + Objects.hashCode(this.formateurs);
        return hash;
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
        final Module other = (Module) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.nombreJoursTotal != other.nombreJoursTotal) {
            return false;
        }
        if (!Objects.equals(this.intitule, other.intitule)) {
            return false;
        }
        if (!Objects.equals(this.formateurs, other.formateurs)) {
            return false;
        }
        return true;
    }
}
