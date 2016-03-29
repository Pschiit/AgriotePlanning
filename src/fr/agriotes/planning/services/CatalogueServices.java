package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Formateur;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Session;

public interface CatalogueServices {

    public void setSessionSelectionnee(Session sessionSelectionnee);

    public void setModuleSelectionne(Module moduleSelectionne);
    
    public void setFormateurSelectionne(Formateur formateurSelectionne);
}
