package fr.agriotes.planning.dao;

import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Formateur;
import fr.agriotes.planning.models.Catalogue;
import fr.agriotes.planning.models.Session;
import fr.agriotes.planning.services.CatalogueDaoServices;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CatalogueDao implements CatalogueDaoServices {

    /**
     * Get all current session, their modules and teacher from a MySQL database
     *
     * @return catalogue
     * @throws SQLException
     */
    @Override
    public Catalogue getCatalogue() throws SQLException {
        Catalogue result = new Catalogue();

        //get formateur table
        Map<Integer, Formateur> formateurs = null;
        String sql = "SELECT id_personne, nom, prenom "
                + "FROM personne  "
                + "WHERE id_personne IN ( "
                + "SELECT id_personne "
                + "FROM intervenant "
                + "GROUP BY id_personne);";
        Connection connection = Database.getConnection();
        Statement order = connection.createStatement();
        ResultSet rs = order.executeQuery(sql);
        while (rs.next()) {
            if (formateurs == null) {
                formateurs = new HashMap<>();
            }
            formateurs.put(rs.getInt("id_personne"), new Formateur(rs.getInt("id_personne"), rs.getString("nom"), rs.getString("prenom")));
        }
        order.close();

        //get intervenant table
        Map<Integer, ArrayList<Formateur>> intervenants = null;
        sql = "SELECT * FROM intervenant;";
        order = connection.createStatement();
        rs = order.executeQuery(sql);
        while (rs.next()) {
            if (intervenants == null) {
                intervenants = new HashMap<>();
            }
            if (!intervenants.containsKey(rs.getInt("id_module"))) {
                intervenants.put(rs.getInt("id_module"), new ArrayList<Formateur>());
            }
            intervenants.get(rs.getInt("id_module")).add(formateurs.get(rs.getInt("id_personne")));

        }
        order.close();

        //get module table
        Map<Integer, Module> modules = null;
        sql = "SELECT id_module, intitule, nb_jours "
                + "FROM module m "
                + "WHERE id_module IN ( "
                + "SELECT id_module "
                + "FROM module_formation "
                + "GROUP BY id_module);";
        order = connection.createStatement();
        rs = order.executeQuery(sql);
        while (rs.next()) {
            if (modules == null) {
                modules = new HashMap<>();
            }
            modules.put(rs.getInt("id_module"), new Module(rs.getInt("id_module"), rs.getString("intitule"), rs.getInt("nb_jours"), intervenants.get(rs.getInt("id_module"))));
        }
        order.close();

        //get module_formation table
        Map<Integer, ArrayList<Module>> moduleFormation = null;
        sql = "SELECT * FROM module_formation;";
        order = connection.createStatement();
        rs = order.executeQuery(sql);
        while (rs.next()) {
            if (moduleFormation == null) {
                moduleFormation = new HashMap<>();
            }
            if (!moduleFormation.containsKey(rs.getInt("id_formation"))) {
                moduleFormation.put(rs.getInt("id_formation"), new ArrayList<Module>());
            }
            moduleFormation.get(rs.getInt("id_formation")).add(modules.get(rs.getInt("id_module")));

        }
        order.close();

        //get session table
        Map<Integer, Session> sessions = null;
        sql = "SELECT s.id_session, s.id_formation, s.date_debut,s.date_fin, f.intitule "
                + "FROM session s "
                + "INNER JOIN formation f "
                + "ON s.id_formation = f.id_formation "
                + "WHERE date_fin > SUBDATE(CURRENT_DATE(), INTERVAL 1 YEAR);";
        order = connection.createStatement();
        rs = order.executeQuery(sql);
        while (rs.next()) {
            if (sessions == null) {
                sessions = new HashMap<>();
            }
            sessions.put(rs.getInt("id_session"), new Session(rs.getInt("id_session"), rs.getString("intitule"), Date.FromSQLDate(rs.getDate("date_debut")), Date.FromSQLDate(rs.getDate("date_fin")), moduleFormation.get(rs.getInt("id_formation"))));
        }
        order.close();
        connection.close();

        result.setLesFormateurs(formateurs);
        result.setLesModules(modules);
        result.setLesSessions(sessions);
        return result;
    }
}
