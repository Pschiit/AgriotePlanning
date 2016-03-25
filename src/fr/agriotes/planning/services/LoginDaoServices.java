package fr.agriotes.planning.services;

import java.sql.SQLException;

public interface LoginDaoServices {
    boolean logAdminByEmailPassword(String email, String password) throws SQLException;
}
