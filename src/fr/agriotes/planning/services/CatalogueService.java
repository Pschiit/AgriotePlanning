package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Session;

public interface CatalogueService {

    public void setSessionSelectionnee(Session sessionSelectionnee);

    public void setModuleSelectionne(Module moduleSelectionne);
}
