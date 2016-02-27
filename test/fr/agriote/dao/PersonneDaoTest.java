package fr.agriote.dao;

import fr.agriote.models.Personne;
import fr.agriote.models.Planning;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonneDaoTest extends DaoTest {

    @Test
    public void testGetByEmailPassword() throws Exception {
        System.out.println("getByEmailPassword");
        String email = "dovan@agriote.fr";
        String password = "password";
        Personne expResult = new Personne(6, email, "ROUGIER", "Dovan", true);
        Personne result = PersonneDao.getByEmailPassword(email, password);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetByEmailPasswordFailed() throws Exception {
        System.out.println("getByEmailPassword Failed");
        String email = "dovan@agriote.fr";
        String password = "error";
        Personne result = PersonneDao.getByEmailPassword(email, password);
        assertNull(result);
    }

}
