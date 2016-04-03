package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.Seance;

public interface CalendrierServices {

    public void editCell(Seance seance);

    public void removeCell(Date date);
}
