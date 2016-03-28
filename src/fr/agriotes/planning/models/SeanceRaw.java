package fr.agriotes.planning.models;

import java.util.Objects;

public class SeanceRaw {

    private int id;
    private int idModule;
    private int idSession;
    private int idFormateur;
    private Date date;

    public SeanceRaw() {
    }

    public SeanceRaw(int id, int idSession, int idModule, int idFormateur, Date date) {
        this.id = id;
        this.idModule = idModule;
        this.idSession = idSession;
        this.idFormateur = idFormateur;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdModule() {
        return idModule;
    }

    public void setIdModule(int idModule) {
        this.idModule = idModule;
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    public int getIdFormateur() {
        return idFormateur;
    }

    public void setIdFormateur(int idFormateur) {
        this.idFormateur = idFormateur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        hash = 71 * hash + this.idModule;
        hash = 71 * hash + this.idSession;
        hash = 71 * hash + this.idFormateur;
        hash = 71 * hash + Objects.hashCode(this.date);
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
        final SeanceRaw other = (SeanceRaw) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.idModule != other.idModule) {
            return false;
        }
        if (this.idSession != other.idSession) {
            return false;
        }
        if (this.idFormateur != other.idFormateur) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SeanceRaw{" + "id=" + id + ", idModule=" + idModule + ", idSession=" + idSession + ", idFormateur=" + idFormateur + ", date=" + date + '}';
    }

}
