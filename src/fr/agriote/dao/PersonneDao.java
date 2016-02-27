package fr.agriote.dao;

import fr.agriote.models.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class PersonneDao {

    public static Personne getByEmailPassword(String email, String password) throws SQLException {
        Personne result = null;
        String sql = "SELECT * FROM personne WHERE mail=? AND password=?;";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setString(1, email);
        order.setString(2, password);
        ResultSet rs = order.executeQuery();
        if (rs.next()) {
            result = new Personne(rs.getInt("id_personne"), rs.getString("mail"), rs.getString("nom"), rs.getString("prenom"), rs.getBoolean("est_admin"));
        }
        order.close();
        connection.close();
        return result;
    }
}
