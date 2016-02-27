package fr.agriote.models;

import fr.agriote.dao.PlanningDao;
import java.util.HashMap;
import java.util.Map.Entry;

public class Planning {

    private HashMap<Integer, Session> lesSessions = new HashMap<>();
    private HashMap<Integer, Seance> lesSeances = new HashMap<>();
    private HashMap<Integer, Module> lesModules = new HashMap<>();
    private HashMap<Integer, Personne> lesFormateurs = new HashMap<>();

    public Planning() {
    }

    public HashMap<Integer, Session> getLesSessions() {
        return lesSessions;
    }

    public void setLesSessions(HashMap<Integer, Session> lesSessions) {
        this.lesSessions = lesSessions;
    }

    public HashMap<Integer, Seance> getLesSeances() {
        return lesSeances;
    }

    public void setLesSeances(HashMap<Integer, Seance> lesSeances) {
        this.lesSeances = lesSeances;
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
                    System.out.print(leFormateur.getId() + " : " + leFormateur);
                }
                System.out.println();
            }
        }
    }
}
