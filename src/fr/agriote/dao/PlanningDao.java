package fr.agriote.dao;

import fr.agriote.models.Date;
import fr.agriote.models.Formation;
import fr.agriote.models.Module;
import fr.agriote.models.Personne;
import fr.agriote.models.Planning;
import fr.agriote.models.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class PlanningDao {

    public static Planning getPlanning() throws SQLException {
        Planning result = new Planning();
        HashMap<Integer, Personne> formateurs = null;
        String sql = "SELECT * FROM formateur f INNER JOIN personne p ON f.id_formateur = p.id_personne;";
        Connection connection = Database.getConnection();
        Statement order = connection.createStatement();
        ResultSet rs = order.executeQuery(sql);
        if (rs.next()) {
            formateurs = new HashMap<>();
            formateurs.put(rs.getInt("id_personne"), new Personne(rs.getInt("id_personne"), rs.getString("mail"), rs.getString("nom"), rs.getString("prenom"), rs.getBoolean("est_admin")));
        } else {
            return null;
        }
        while (rs.next()) {
            formateurs.put(rs.getInt("id_personne"), new Personne(rs.getInt("id_personne"), rs.getString("mail"), rs.getString("nom"), rs.getString("prenom"), rs.getBoolean("est_admin")));
        }
        order.close();
        HashMap<Integer, Module> modules = null;
        sql = "SELECT * FROM module;";
        order = connection.createStatement();
        rs = order.executeQuery(sql);
        if (rs.next()) {
            modules = new HashMap<>();
            modules.put(rs.getInt("id_module"), new Module(rs.getInt("id_module"), rs.getString("intitule"), rs.getInt("nb_jours"), getLesFormateursDuModule(rs.getInt("id_module"), formateurs)));
        } else {
            return null;
        }
        while (rs.next()) {
            modules.put(rs.getInt("id_module"), new Module(rs.getInt("id_module"), rs.getString("intitule"), rs.getInt("nb_jours"), getLesFormateursDuModule(rs.getInt("id_module"), formateurs)));
        }
        order.close();
        HashMap<Integer, Session> sessions = null;
        sql = "SELECT s.id_session, s.id_formation, s.date_debut,s.date_fin, f.intitule FROM session s INNER JOIN formation f ON s.id_formation = f.id_formation;";
        order = connection.createStatement();
        rs = order.executeQuery(sql);
        if (rs.next()) {
            sessions = new HashMap<>();
            sessions.put(rs.getInt("id_session"), new Session(rs.getInt("id_session"), rs.getString("intitule"), Date.FromSQLDate(rs.getDate("date_debut")), Date.FromSQLDate(rs.getDate("date_fin")), getLesModulesDeLaFormation(rs.getInt("id_formation"), modules)));
        }
        while (rs.next()) {
            sessions.put(rs.getInt("id_session"), new Session(rs.getInt("id_session"), rs.getString("intitule"), Date.FromSQLDate(rs.getDate("date_debut")), Date.FromSQLDate(rs.getDate("date_fin")), getLesModulesDeLaFormation(rs.getInt("id_formation"), modules)));
        }
        order.close();
        connection.close();
        result.setLesFormateurs(formateurs);
        result.setLesModules(modules);
        result.setLesSessions(sessions);
        return result;
    }

    private static ArrayList<Personne> getLesFormateursDuModule(int idModule, HashMap<Integer, Personne> lesFormateurs) throws SQLException {
        ArrayList<Integer> idFormateurs = new ArrayList<>();
        String sql = "SELECT * FROM personne p INNER JOIN intervenant i ON p.id_personne = i.id_personne WHERE id_module =?;";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setInt(1, idModule);
        ResultSet rs = order.executeQuery();
        if (rs.next()) {
            idFormateurs = new ArrayList<>();
            idFormateurs.add(rs.getInt("id_personne"));
        }
        while (rs.next()) {
            idFormateurs.add(rs.getInt("id_personne"));
        }
        order.close();
        connection.close();
        ArrayList<Personne> result = new ArrayList<>();
        for (Integer id : idFormateurs) {
            result.add(lesFormateurs.get(id));
        }
        return result;
    }

    private static ArrayList<Module> getLesModulesDeLaFormation(int idFormation, HashMap<Integer, Module> lesModules) throws SQLException {
        ArrayList<Integer> idModules = new ArrayList<>();
        String sql = "SELECT * FROM module m INNER JOIN module_formation mf ON m.id_module = mf.id_module WHERE id_formation =?;";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setInt(1, idFormation);
        ResultSet rs = order.executeQuery();
        while (rs.next()) {
            idModules.add(rs.getInt("id_module"));
        }
        order.close();
        connection.close();
        ArrayList<Module> result = new ArrayList<>();
        for (Integer id : idModules) {
            result.add(lesModules.get(id));
        }
        return result;
    }
}
