package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Seance;
import fr.agriotes.planning.models.SeanceRaw;

public interface PlanningServices {

    Seance addSeance(Seance seance);

    Seance getSeance(int id);

    Seance editSeance(int id, Seance nouvelleSeance);

    Seance removeSeance(int id);
    
    Seance convertSeanceRawtoSeance(SeanceRaw seance);
}
