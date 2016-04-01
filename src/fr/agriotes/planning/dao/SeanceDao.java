package fr.agriotes.planning.dao;

import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.Seance;
import fr.agriotes.planning.models.SeanceRaw;
import fr.agriotes.planning.services.SeanceDaoServices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeanceDao implements SeanceDaoServices {

    @Override
    public Seance addSeance(Seance seance) throws SQLException {
        Seance result = null;
        String sql = "INSERT INTO seance(id_module, id_session, id_formateur, jour) "
                + "VAlUES(?,?,?,?);";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setInt(1, seance.getModule().getId());
        order.setInt(2, seance.getSession().getId());
        order.setInt(3, seance.getFormateur().getId());
        order.setDate(4, seance.getDate().toSQLDate());
        int rsUpdate = order.executeUpdate();

        sql = "SELECT id_seance FROM seance "
                + "WHERE (id_formateur = ? AND id_session = ? ) AND jour = ?;";
        order = connection.prepareStatement(sql);
        order.setInt(1, seance.getFormateur().getId());
        order.setInt(2, seance.getSession().getId());
        order.setDate(3, seance.getDate().toSQLDate());
        ResultSet rs = order.executeQuery();
        if (rs.next()) {
            result = new Seance(rs.getInt("id_seance"), seance.getSession(), seance.getModule(), seance.getFormateur(), seance.getDate());
        }
        order.close();
        connection.close();
        return result;
    }

    @Override
    public List<SeanceRaw> getSeancesByIdSession(int idSession) throws SQLException {
        List<SeanceRaw> result = null;
        String sql = "SELECT * "
                + "FROM seance "
                + "WHERE id_session = ?";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setInt(1, idSession);
        ResultSet rs = order.executeQuery();
        while (rs.next()) {
            if (result == null) {
                result = new ArrayList<>();
            }
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
    public List<SeanceRaw> getSeancesByModuleSession(int idModule, int idSession) throws SQLException {
        List<SeanceRaw> result = null;
        String sql = "SELECT * "
                + "FROM seance "
                + "WHERE id_session = ? AND id_module= ?";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setInt(1, idSession);
        order.setInt(2, idModule);
        ResultSet rs = order.executeQuery();
        while (rs.next()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(new SeanceRaw(rs.getInt("id_seance"), rs.getInt("id_session"), rs.getInt("id_module"), rs.getInt("id_formateur"), Date.FromSQLDate(rs.getDate("jour"))));
        }
        order.close();
        connection.close();
        return result;
    }

    @Override
    public List<SeanceRaw> getSeancesByDate(Date date) throws SQLException {
        List<SeanceRaw> result = null;
        String sql = "SELECT * "
                + "FROM seance "
                + "WHERE jour = ?";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setDate(1, date.toSQLDate());
        ResultSet rs = order.executeQuery();
        while (rs.next()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(new SeanceRaw(rs.getInt("id_seance"), rs.getInt("id_session"), rs.getInt("id_module"), rs.getInt("id_formateur"), Date.FromSQLDate(rs.getDate("jour"))));
        }
        order.close();
        connection.close();
        return result;
    }

    @Override
    public Seance updateSeance(Seance seance) throws SQLException {
        Seance result = null;
        String sql = "UPDATE seance "
                + "SET id_session = ? ,id_module = ? , id_formateur = ?, jour = ? "
                + "WHERE id_seance = ?;";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setInt(1, seance.getSession().getId());
        order.setInt(2, seance.getModule().getId());
        order.setInt(3, seance.getFormateur().getId());
        order.setDate(4, seance.getDate().toSQLDate());
        order.setInt(5, seance.getId());
        int rs = order.executeUpdate();
        result = seance;
        order.close();
        connection.close();
        return result;
    }

    @Override
    public Seance removeSeance(Seance seance) throws SQLException {
        Seance result = null;
        String sql = "DELETE FROM seance "
                + "WHERE id_seance = ?;";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setInt(1, seance.getId());
        int rs = order.executeUpdate();
        result = seance;
        order.close();
        connection.close();
        return result;
    }

}
