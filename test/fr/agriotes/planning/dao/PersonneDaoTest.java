package fr.agriotes.planning.dao;

import fr.agriotes.planning.dao.LoginDao;
import fr.agriotes.planning.services.LoginDaoServices;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonneDaoTest extends DaoTest {

    @Test
    public void testGetByEmailPassword() throws Exception {
        System.out.println("getByEmailPassword");
        String email = "dovan@agriote.fr";
        String password = "password";
        LoginDaoServices loginDao = new LoginDao();
        assertTrue(loginDao.logAdminByEmailPassword(email, password));
    }

    @Test
    public void testGetByEmailPasswordFailed() throws Exception {
        System.out.println("getByEmailPassword Failed");
        String email = "dovan@agriote.fr";
        String password = "error";
        LoginDaoServices loginDao = new LoginDao();
        assertFalse(loginDao.logAdminByEmailPassword(email, password));
    }

}
