package fr.agriotes.planning.dao;

import fr.agriotes.planning.dao.CatalogueDao;
import fr.agriotes.planning.models.Catalogue;
import org.junit.Test;
import static org.junit.Assert.*;
public class CatalogueDaoTest extends DaoTest{
    @Test
    public void testGetCatalogue() throws Exception {
        System.out.println("getCatalogue");
        Catalogue result = CatalogueDao.getCatalogue();
        result.afficheCatalogue();
        assertNotNull(result);
        assertEquals(11, result.getLesModules().size());
        assertEquals(6, result.getLesSessions().size());
        assertEquals(5, result.getLesFormateurs().size());
    }
}
