package fr.cepi.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe proposant des services transverses pour l'applicaition
 */
public class DatabaseService {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:apache:commons:dbcp:MyPool");
    }
}
