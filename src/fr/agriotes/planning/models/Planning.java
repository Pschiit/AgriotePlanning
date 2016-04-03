package fr.agriotes.planning.models;

import java.util.ArrayList;
import java.util.List;

public class Planning {

    private List<Seance> lesSeances;
    private Catalogue catalogue;

    public Planning() {
    }

    public Planning(List<Seance> lesSeances, Catalogue catalogue) {
        this.lesSeances = lesSeances;
        this.catalogue = catalogue;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public List<Seance> getLesSeances() {
        return lesSeances;
    }

    public void setLesSeances(List<Seance> lesSeances) {
        this.lesSeances = lesSeances;
    }

    public void setLesSeancesFromRaw(List<SeanceRaw> lesSeancesRaw) {
        if (lesSeancesRaw == null) {
            this.lesSeances = new ArrayList<>();
        } else {
            assert catalogue != null : "Catalogue manquant";
            List<Seance> lesSeances = new ArrayList();

            for (SeanceRaw laSeanceSQL : lesSeancesRaw) {
                Seance seance = convertSeanceRawtoSeance(laSeanceSQL);
                lesSeances.add(seance);
            }
            this.lesSeances = lesSeances;
        }
    }

    public boolean addSeance(Seance seance) {
        return lesSeances.add(seance);
    }

    public Seance getSeance(int id) {
        for (Seance uneSeance : lesSeances) {
            if (uneSeance.getId() == id) {
                return uneSeance;
            }
        }
        return null;
    }

    public List<Seance> getSeancesByModuleSession(Module module, Session session) {
        List<Seance> result = new ArrayList();;
        for (Seance uneSeance : lesSeances) {
            if (uneSeance.getModule().equals(module) && uneSeance.getSession().equals(session)) {
                result.add(uneSeance);
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public boolean editSeance(Seance seance) {
        for (Seance uneSeance : lesSeances) {
            if (uneSeance.getId() == seance.getId()) {
                uneSeance = seance;
            }
            return true;
        }
        return false;
    }

    public boolean removeSeance(Seance seance) {
        return lesSeances.remove(seance);
    }

    public Seance convertSeanceRawtoSeance(SeanceRaw seanceRaw) {
        assert catalogue != null : "Catalogue manquant";
        return new Seance(seanceRaw.getId(), catalogue.getSession(seanceRaw.getIdSession()), catalogue.getModule(seanceRaw.getIdModule()), catalogue.getFormateur(seanceRaw.getIdFormateur()), seanceRaw.getDate());
    }
}
