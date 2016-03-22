package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Personne;
import fr.agriotes.planning.models.Session;

public interface CatalogueServices {
    Session getSessionById(int id);
    Module getModuleById(int id);
    Personne getFormateurById(int id);
}
