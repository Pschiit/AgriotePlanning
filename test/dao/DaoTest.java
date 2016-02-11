package dao;

import fr.agriote.dao.Database;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Ignore;

@Ignore
public class DaoTest {
    @Before
    public  void resetDb() throws SQLException{
        System.out.println("Reseting database.");
        Connection connection = Database.getConnection();
        CallableStatement order  = connection.prepareCall("{CALL reset_db()}");
        order.execute();
        order.close();
        connection.close();
    }
}
