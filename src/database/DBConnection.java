package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides reusable MySQL connections for the DAO layer.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gestionpharmacie";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion a la base de donnees : " + e.getMessage());
            return null;
        }
    }
}
