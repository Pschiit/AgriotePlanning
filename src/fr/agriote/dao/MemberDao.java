package fr.agriote.dao;

import fr.agriote.models.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MemberDao {

    public static Member getByLoginPassword(String login, String password) throws SQLException {
        Member result = null;
        String sql = "SELECT * FROM member WHERE login=? AND password=?;";
        Connection connection = Database.getConnection();
        PreparedStatement order = connection.prepareStatement(sql);
        order.setString(1, login);
        order.setString(2, password);
        ResultSet rs = order.executeQuery();
        System.out.println(rs.toString());
        if(rs.next()){
            result = new Member(rs.getString("login"),rs.getString("password"));
        }
        order.close();
        connection.close();
        return result;
    }

}

