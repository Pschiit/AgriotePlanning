package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Formateur;
import fr.agriotes.planning.models.Session;

public interface CatalogueServices {

    Session getSession(int id);

    Module getModule(int id);

    Formateur getFormateur(int id);
}
