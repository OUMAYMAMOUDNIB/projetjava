package dao;

import database.DBConnection;

import java.sql.*;

/**
 * Handles authentication queries.
 */
public class UtilisateurDAO {

    public boolean verifierConnexion(String username, String password) {
        String sql = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // existe = true

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
