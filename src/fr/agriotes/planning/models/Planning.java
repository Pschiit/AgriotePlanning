package fr.agriotes.planning.models;

import fr.agriotes.planning.services.PlanningServices;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planning implements PlanningServices {

    private Map<Integer, Seance> lesSeances;
    private Catalogue catalogue;

    public Planning() {
    }

    public Planning(Map<Integer, Seance> lesSeances, Catalogue catalogue) {
        this.lesSeances = lesSeances;
        this.catalogue = catalogue;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Map<Integer, Seance> getLesSeances() {
        return lesSeances;
    }

    public void setLesSeances(Map<Integer, Seance> lesSeances) {
        this.lesSeances = lesSeances;
    }

    public void setLesSeances(List<SeanceRaw> lesSeancesRaw) {
        if (lesSeancesRaw == null) {
            this.lesSeances = new HashMap<>();
        } else {
            assert catalogue != null : "Catalogue manquant";
            Map<Integer, Seance> lesSeances = new HashMap<>();

            for (SeanceRaw laSeanceSQL : lesSeancesRaw) {
                Seance seance = convertSeanceRawtoSeance(laSeanceSQL);
                lesSeances.put(seance.getId(), seance);
            }
            this.lesSeances = lesSeances;
        }
    }

    @Override
    public Seance addSeance(Seance seance) {
        return lesSeances.put(seance.getId(), seance);
    }

    @Override
    public Seance getSeance(int id) {
        return lesSeances.get(id);
    }

    @Override
    public Seance editSeance(int id, Seance nouvelleSeance) {
        return lesSeances.replace(id, nouvelleSeance);
    }

    @Override
    public Seance removeSeance(int id) {
        return lesSeances.remove(id);
    }

    @Override
    public Seance convertSeanceRawtoSeance(SeanceRaw seanceRaw) {
        assert catalogue != null : "Catalogue manquant";
        return new Seance(seanceRaw.getId(), catalogue.getSession(seanceRaw.getIdSession()), catalogue.getModule(seanceRaw.getIdModule()), catalogue.getFormateur(seanceRaw.getIdFormateur()), seanceRaw.getDate());
    }
}
