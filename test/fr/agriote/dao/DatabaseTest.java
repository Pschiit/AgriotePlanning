package fr.agriote.dao;

import fr.agriote.dao.Database;
import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {

    public DatabaseTest() {
    }

    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        Connection result = Database.getConnection();
        assertNotNull(result);
    }

}
