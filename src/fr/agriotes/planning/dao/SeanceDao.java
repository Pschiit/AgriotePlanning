package fr.agriotes.planning.dao;

import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.Seance;
import fr.agriotes.planning.models.SeanceRaw;
import fr.agriotes.planning.services.SeanceDaoServices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SeanceDao implements SeanceDaoServices {

    @Override
    public Seance addSeance(Seance seance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SeanceRaw> getSeancesByIdSession(int idSession) throws SQLException {
        List<SeanceRaw> result = null;
        String sql = "SELECT * FROM seance WHERE id_session = ?";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setInt(1, idSession);
        ResultSet rs = order.executeQuery();
        while (rs.next()) {
            if(result == null)
                result = new ArrayList<>();
            result.add(new SeanceRaw(rs.getInt("id_seance"), rs.getInt("id_session"), rs.getInt("id_module"), rs.getInt("id_formateur"), Date.FromSQLDate(rs.getDate("jour"))));
        }
        order.close();
        connection.close();
        return result;
    }

    @Override
    public List<SeanceRaw> getSeancesByWeek(Date firstDay, Date LastDay) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Seance updateSeance(int id, Seance seance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Seance removeSeance(Seance seance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
