package fr.agriotes.planning.models;

import fr.agriotes.planning.services.CatalogueServices;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

public class Catalogue implements CatalogueServices{

    private HashMap<Integer, Session> lesSessions = new HashMap<>();
    private HashMap<Integer, Module> lesModules = new HashMap<>();
    private HashMap<Integer, Personne> lesFormateurs = new HashMap<>();

    public Catalogue() {
    }

    public HashMap<Integer, Session> getLesSessions() {
        return lesSessions;
    }

    public void setLesSessions(HashMap<Integer, Session> lesSessions) {
        this.lesSessions = lesSessions;
    }

    public HashMap<Integer, Module> getLesModules() {
        return lesModules;
    }

    public void setLesModules(HashMap<Integer, Module> lesModules) {
        this.lesModules = lesModules;
    }

    public HashMap<Integer, Personne> getLesFormateurs() {
        return lesFormateurs;
    }

    public void setLesFormateurs(HashMap<Integer, Personne> lesFormateurs) {
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
     * @param id
     * @return Session
     */
    @Override
    public Session getSessionById(int id){
        return lesSessions.get(id);
    }
    
    /**
     * @param id
     * @return Module
     */
    @Override
    public Module getModuleById(int id){
        return lesModules.get(id);
    }
    
    /**
     * @param id
     * @return Module
     */
    @Override
    public Personne getFormateurById(int id){
        return lesFormateurs.get(id);
    }

    public void afficheCatalogue() {
        System.out.println("Liste des sessions :");
        for (Entry<Integer, Session> entry : lesSessions.entrySet()) {
            int key = entry.getKey();
            Session value = entry.getValue();
            System.out.println(key + " : " + value);
            System.out.println("\tModules : ");
            for (Module leModule : value.getLesModules()) {
                System.out.print("\t" + leModule.getId() + " : " + leModule);
                System.out.print(" Formateur(s) : ");
                for (Personne leFormateur : leModule.getFormateurs()) {
                    System.out.print(leFormateur.getId() + "-" + leFormateur + " ");
                }
                System.out.println();
            }
        }
    }
}
