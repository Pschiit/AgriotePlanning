package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Catalogue;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Session;

public interface fenetreServices {

    public void LoadCatalogue();

    public void loadCalendrierAnnuel(Session sessionSelectionnee);

    public void loadDetailModule(Module module, Session session);
}
