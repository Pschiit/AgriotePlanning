package fr.agriotes.planning.models;

import java.util.Objects;

class Seance {

    private int id;
    private Module module;
    private Session session;
    private Personne formateur;
    private Date date;

    public Seance() {
    }

    public Seance(int id, Module module, Session session, Personne formateur, Date date) {
        this.id = id;
        this.module = module;
        this.session = session;
        this.formateur = formateur;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Personne getFormateur() {
        return formateur;
    }

    public void setFormateur(Personne formateur) {
        this.formateur = formateur;
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
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.module);
        hash = 89 * hash + Objects.hashCode(this.session);
        hash = 89 * hash + Objects.hashCode(this.formateur);
        hash = 89 * hash + Objects.hashCode(this.date);
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
        final Seance other = (Seance) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.module, other.module)) {
            return false;
        }
        if (!Objects.equals(this.session, other.session)) {
            return false;
        }
        if (!Objects.equals(this.formateur, other.formateur)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return module + "\n" + formateur + "\n" + date;
    }

}
