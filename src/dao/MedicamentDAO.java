package dao;

import database.DBConnection;
import models.Medicament;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles database operations for medicines.
 */
public class MedicamentDAO {
    private Connection connection;

    public MedicamentDAO() {
        this.connection = DBConnection.getConnection();
    }

    public String getMedicamentNomById(int id) {
        String query = "SELECT nom FROM medicaments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Inconnu";
    }

    public List<Medicament> getMedicamentsAvecStock() {
        List<Medicament> medicaments = new ArrayList<>();

        String sql = """
            SELECT
                m.id, m.nom, m.categorie, m.date_entree, m.date_expiration,
                COALESCE(a.total_approvisionnement, 0) - COALESCE(v.total_vente, 0) AS stock
            FROM medicaments m
            LEFT JOIN (
                SELECT medicament_id, SUM(quantite) AS total_approvisionnement
                FROM approvisionnements
                GROUP BY medicament_id
            ) a ON m.id = a.medicament_id
            LEFT JOIN (
                SELECT medicament_id, SUM(quantite) AS total_vente
                FROM ventes
                GROUP BY medicament_id
            ) v ON m.id = v.medicament_id
            ORDER BY m.id
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medicament medicament = new Medicament(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("categorie"),
                    rs.getDate("date_entree").toLocalDate(),
                    rs.getDate("date_expiration").toLocalDate()
                );
                medicament.setStock(rs.getFloat("stock"));
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicaments;
    }

    public Map<Integer, String> getAllMedicamentsWithNames() {
        Map<Integer, String> medicaments = new HashMap<>();
        String query = "SELECT id, nom FROM medicaments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                medicaments.put(rs.getInt("id"), rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }

    public List<Medicament> getAllMedicaments() {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = "SELECT * FROM medicaments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicaments.add(new Medicament(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("categorie"),
                    rs.getDate("date_entree").toLocalDate(),
                    rs.getDate("date_expiration").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicaments;
    }

    public void ajouterMedicament(Medicament medicament) {
        String sql = "INSERT INTO medicaments (nom, categorie, date_entree, date_expiration) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, medicament.getNom());
            pstmt.setString(2, medicament.getCategorie());
            pstmt.setDate(3, Date.valueOf(medicament.getDateEntree()));
            pstmt.setDate(4, Date.valueOf(medicament.getDateExpiration()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerMedicament(int id) {
        String sql = "DELETE FROM medicaments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierMedicament(Medicament medicament) {
        String sql = "UPDATE medicaments SET nom = ?, categorie = ?, date_entree = ?, date_expiration = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, medicament.getNom());
            pstmt.setString(2, medicament.getCategorie());
            pstmt.setDate(3, Date.valueOf(medicament.getDateEntree()));
            pstmt.setDate(4, Date.valueOf(medicament.getDateExpiration()));
            pstmt.setInt(5, medicament.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
