/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import fr.agriote.dao.MemberDao;
import fr.agriote.models.Member;
import org.junit.Test;
import static org.junit.Assert.*;

public class MemberDaoTest extends DaoTest {

    @Test
    public void testGetByLoginPassword() throws Exception {
        System.out.println("getByLoginPassword");
        String login = "titi";
        String password = "grosMinet";
        Member expResult = new Member(login, password);
        Member result = MemberDao.getByLoginPassword(login, password);
        assertEquals(expResult, result);
    }
}
