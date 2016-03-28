package fr.agriotes.planning.dao;

import fr.agriotes.planning.models.Catalogue;
import fr.agriotes.planning.services.CatalogueDaoServices;
import org.junit.Test;
import static org.junit.Assert.*;

public class CatalogueDaoTest extends DaoTest {

    @Test
    public void testGetCatalogue() throws Exception {
        System.out.println("getCatalogue");
        CatalogueDaoServices catalogueDao = new CatalogueDao();
        Catalogue result = catalogueDao.getCatalogue();
        assertNotNull(result);
        assertEquals(11, result.getLesModules().size());
        assertEquals(6, result.getLesSessions().size());
        assertEquals(5, result.getLesFormateurs().size());
    }
}
