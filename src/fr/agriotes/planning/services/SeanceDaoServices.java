package fr.agriotes.planning.services;

import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.SeanceRaw;
import fr.agriotes.planning.models.Seance;
import java.sql.SQLException;
import java.util.List;

public interface SeanceDaoServices {

    Seance addSeance(Seance seance);

    List<SeanceRaw> getSeancesByIdSession(int idSession) throws SQLException;

    List<SeanceRaw> getSeancesByWeek(Date firstDay, Date LastDay) throws SQLException;

    Seance updateSeance(int id, Seance seance);

    Seance removeSeance(Seance seance);
}
