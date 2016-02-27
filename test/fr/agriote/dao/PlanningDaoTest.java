package fr.agriote.dao;

import fr.agriote.models.Planning;
import org.junit.Test;
import static org.junit.Assert.*;
public class PlanningDaoTest extends DaoTest{
    @Test
    public void testGetPlanning() throws Exception {
        System.out.println("getPlanning");
        Planning result = PlanningDao.getPlanning();
        result.afficheCatalogue();
        assertNotNull(result);
    }
}
