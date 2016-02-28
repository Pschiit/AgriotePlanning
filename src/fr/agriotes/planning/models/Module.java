package fr.agriotes.planning.models;

import java.util.ArrayList;
import java.util.Objects;

public class Module {

    private int id;
    private String intitule;
    private int nombreJours;
    private ArrayList<Personne> formateurs = new ArrayList<>();

    public Module() {
    }

    public Module(int id, String intitule, int nombreJours, ArrayList<Personne> formateurs) {
        this.id = id;
        this.intitule = intitule;
        this.nombreJours = nombreJours;
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

    public int getNombreJours() {
        return nombreJours;
    }

    public void setNombreJours(int nombreJours) {
        this.nombreJours = nombreJours;
    }

    public ArrayList<Personne> getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(ArrayList<Personne> formateurs) {
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
        hash = 71 * hash + this.nombreJours;
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
        if (this.nombreJours != other.nombreJours) {
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
