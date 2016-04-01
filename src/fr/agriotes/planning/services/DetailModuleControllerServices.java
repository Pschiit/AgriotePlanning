package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Seance;

public interface DetailModuleControllerServices {

    Seance removeSeance(Seance seance);
    Seance editSeance(Seance seance);
}
