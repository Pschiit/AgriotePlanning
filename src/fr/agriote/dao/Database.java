package fr.agriote.dao;

import java.sql.*;

public class Database {
  /** Code erreur MySQL quand le serveur est inaccessible */
  public static final int SERVER_OFF = 0;

  /** Code erreur MySQL pour "duplicate entry" */
  public static final int DOUBLON = 1062;

  /** Code erreur MySQL pour
   * "Cannot delete or update a parent row: a foreign key
   * constraint fails " */
  public static final int ROW_IS_REFERENCED = 1451;

 /** Code erreur MySQL pour "Cannot add or update
  * a child row: a foreign key constraint fails" */
  public static final int FOREIGN_KEY_NOT_FOUND = 1452;

  protected static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
  protected static final String DB_NAME = "agriote_db";
  protected static final String URL = "jdbc:mysql://eu-cdbr-azure-north-d.cloudapp.net/" + DB_NAME;
  protected static final String USER = "bcf40c5be4b0b9";
  protected static final String PASSWORD = "f5df28e6";

  public enum SortOrder {

    ASC, DESC;
  }


  static {
    // Chargement du pilote
    // Ne doit avoir lieu qu'une seule fois
    try {
      Class.forName(DRIVER_NAME).newInstance();
      System.out.println("*** Driver loaded.");
    }
    catch (ClassNotFoundException e) {
      System.err.println("*** ERROR: Driver " + DRIVER_NAME + " not found");
    }
    catch (InstantiationException e) {
      System.err.println("*** ERROR: Impossible to create an instance of " + DRIVER_NAME);
      System.err.println(e.getMessage());
    }
    catch (IllegalAccessException e) {
      System.err.println("*** ERROR: Impossible to create an instance of " + DRIVER_NAME);
      System.err.println(e.getMessage());
    }
  }

  /** Fournit une connexion à la base de données.
   * Ne fait pas appel à un pool de connexion, mâme si cela est envisageable plus tard
   * (ne changerait rien à l'appel de la méthode)
   * @throws java.sql.SQLException
   */
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

}

