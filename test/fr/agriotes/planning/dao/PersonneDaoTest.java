package fr.agriotes.planning.dao;

import fr.agriotes.planning.dao.PersonneDao;
import fr.agriotes.planning.models.Personne;
import fr.agriotes.planning.models.Catalogue;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonneDaoTest extends DaoTest {

    @Test
    public void testGetByEmailPassword() throws Exception {
        System.out.println("getByEmailPassword");
        String email = "dovan@agriote.fr";
        String password = "password";
        Personne expResult = new Personne(7, email, "ROUGIER", "Dovan", true);
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
