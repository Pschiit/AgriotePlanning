package fr.agriotes.planning.dao;

import fr.agriotes.planning.models.Formateur;
import fr.agriotes.planning.services.LoginDaoServices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao implements LoginDaoServices{

    /**
     * @param email
     * @param password
     * @return true if user has admin authorisation
     * @throws SQLException
     */
    @Override
    public boolean logAdminByEmailPassword(String email, String password) throws SQLException {
        boolean result;
        String sql = "SELECT * FROM personne WHERE mail=? AND password=?;";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setString(1, email);
        order.setString(2, password);
        ResultSet rs = order.executeQuery();
        assert rs.next() : "Identifiant incorrect.";
        result = rs.getBoolean("est_admin");
        order.close();
        connection.close();
        return result;
    }
}
