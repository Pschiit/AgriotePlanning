package fr.agriotes.planning.dao;

import fr.agriotes.planning.models.Date;
import fr.agriotes.planning.models.Formateur;
import fr.agriotes.planning.models.Module;
import fr.agriotes.planning.models.Seance;
import fr.agriotes.planning.models.SeanceRaw;
import fr.agriotes.planning.models.Session;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class SeanceDaoTest  extends DaoTest {
    
    @Test
    public void testUpdateSeance()throws Exception{
        System.out.println("updateSeance");
        Session session = new Session();
        session.setId(4);
        Module module = new Module();
        module.setId(9);
        Formateur formateur = new Formateur();
        formateur.setId(15);
        Date date = new Date(2016, 10, 30);
        Seance seance = new Seance(1, session, module, formateur, date);
        SeanceDao instance = new SeanceDao();
        Seance result = instance.updateSeance(seance);
        assertEquals(seance, result);
    }
    
    @Test
    public void testDeleteSeance()throws Exception{
        System.out.println("deleteSeance");
        Session session = new Session();
        session.setId(4);
        Module module = new Module();
        module.setId(9);
        Formateur formateur = new Formateur();
        formateur.setId(15);
        Date date = new Date(2016, 11, 28);
        Seance seance = new Seance(2, session, module, formateur, date);
        SeanceDao instance = new SeanceDao();
        Seance result = instance.removeSeance(seance);
        assertEquals(seance, result);
    }
}
