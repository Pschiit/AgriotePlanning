package fr.agriotes.planning.models;

import java.util.List;
import java.util.Objects;

public class Session {

    private int id;
    private String intituleFormation;
    private Date dateDebut;
    private Date dateFin;
    private List<Module> lesModules;

    public Session() {
    }

    public Session(int id, String intituleFormation, Date dateDebut, Date dateFin, List<Module> lesModules) {
        this.id = id;
        this.intituleFormation = intituleFormation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        setLesModules(lesModules);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntituleFormation() {
        return intituleFormation;
    }

    public void setIntituleFormation(String intituleFormation) {
        this.intituleFormation = intituleFormation;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public List<Module> getLesModules() {
        return lesModules;
    }

    public void setLesModules(List<Module> lesModules) {
        if (lesModules != null) {
            this.lesModules = lesModules;
        }
    }

    @Override
    public String toString() {
        if (dateDebut.getAnnee() == dateFin.getAnnee()) {
            return intituleFormation + " session " + dateDebut.getAnnee();
        }
        return intituleFormation + " session " + dateDebut.getAnnee() + "/" + dateFin.getAnnee();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.intituleFormation);
        hash = 97 * hash + Objects.hashCode(this.dateDebut);
        hash = 97 * hash + Objects.hashCode(this.dateFin);
        hash = 97 * hash + Objects.hashCode(this.lesModules);
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
        final Session other = (Session) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.intituleFormation, other.intituleFormation)) {
            return false;
        }
        if (!Objects.equals(this.dateDebut, other.dateDebut)) {
            return false;
        }
        if (!Objects.equals(this.dateFin, other.dateFin)) {
            return false;
        }
        if (!Objects.equals(this.lesModules, other.lesModules)) {
            return false;
        }
        return true;
    }
}
