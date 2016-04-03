package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Formateur;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Session;

public interface CatalogueServices extends fenetreServices{

    public void selectSession(Session sessionSelectionnee);

    public void selectModule(Module moduleSelectionne);
    
    public void selectFormateur(Formateur formateurSelectionne);
}
