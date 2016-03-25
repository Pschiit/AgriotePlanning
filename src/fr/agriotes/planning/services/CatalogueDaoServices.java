package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Catalogue;
import java.sql.SQLException;

public interface CatalogueDaoServices {

    Catalogue getCatalogue() throws SQLException;
}
