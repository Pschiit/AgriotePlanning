package fr.agriotes.planning.models;

import fr.agriotes.planning.services.CatalogueServices;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class Catalogue implements CatalogueServices {

    private Map<Integer, Session> lesSessions;
    private Map<Integer, Module> lesModules;
    private Map<Integer, Formateur> lesFormateurs;

    public Catalogue() {
    }

    public Map<Integer, Session> getLesSessions() {
        return lesSessions;
    }

    public void setLesSessions(Map<Integer, Session> lesSessions) {
        this.lesSessions = lesSessions;
    }

    public Map<Integer, Module> getLesModules() {
        return lesModules;
    }

    public void setLesModules(Map<Integer, Module> lesModules) {
        this.lesModules = lesModules;
    }

    public Map<Integer, Formateur> getLesFormateurs() {
        return lesFormateurs;
    }

    public void setLesFormateurs(Map<Integer, Formateur> lesFormateurs) {
        this.lesFormateurs = lesFormateurs;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.lesSessions);
        hash = 97 * hash + Objects.hashCode(this.lesModules);
        hash = 97 * hash + Objects.hashCode(this.lesFormateurs);
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
        final Catalogue other = (Catalogue) obj;
        if (!Objects.equals(this.lesSessions, other.lesSessions)) {
            return false;
        }
        if (!Objects.equals(this.lesModules, other.lesModules)) {
            return false;
        }
        if (!Objects.equals(this.lesFormateurs, other.lesFormateurs)) {
            return false;
        }
        return true;
    }

    /**
     * return session from lesSession by id
     *
     * @param id
     * @return Session
     */
    @Override
    public Session getSession(int id) {
        return lesSessions.get(id);
    }

    /**
     * return module from lesModules by id
     *
     * @param id
     * @return Module
     */
    @Override
    public Module getModule(int id) {
        return lesModules.get(id);
    }

    /**
     * @param id
     * @return Module
     */
    @Override
    public Formateur getFormateur(int id) {
        return lesFormateurs.get(id);
    }
}
