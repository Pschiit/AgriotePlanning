package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.Seance;

public interface PlanningServices extends fenetreServices {
    Seance addSeance(Date date);
    Seance removeSeance(Seance seance);
    Seance editSeance(Seance seance);
}
