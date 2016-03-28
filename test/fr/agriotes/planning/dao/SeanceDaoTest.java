package fr.agriotes.planning.dao;

import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.SeanceRaw;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class SeanceDaoTest  extends DaoTest {
    
    @Test
    public void testGetSeancesByIdSession() throws Exception {
        System.out.println("getSeancesByIdSession");
        int idSession = 4;
        SeanceDao instance = new SeanceDao();
        List<SeanceRaw> expResult = new ArrayList<>();
        expResult.add(new SeanceRaw(1, 4, 9, 14, new Date(2016, 11, 25)));
        expResult.add(new SeanceRaw(2, 4, 9, 15, new Date(2016, 11, 26)));
        for (SeanceRaw seanceRaw : expResult) {
            System.out.println(seanceRaw);
        }
        List<SeanceRaw> result = instance.getSeancesByIdSession(idSession);
        for (SeanceRaw seanceRaw : result) {
            System.out.println(seanceRaw);
        }
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetSeancesByIdSessionFailed() throws Exception {
        System.out.println("getSeancesByIdSessionFailed");
        int idSession = 1;
        SeanceDao instance = new SeanceDao();
        List<SeanceRaw> expResult = null;
        List<SeanceRaw> result = instance.getSeancesByIdSession(idSession);
        assertEquals(expResult, result);
    }
}
