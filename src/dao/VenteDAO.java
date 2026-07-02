package dao;

import database.DBConnection;
import models.Vente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations for sales.
 */
public class VenteDAO {

    public List<Vente> getAllVentes() {
        List<Vente> ventes = new ArrayList<>();
        String sql = """
            SELECT v.*, m.nom AS medicament_nom
            FROM ventes v
            JOIN medicaments m ON v.medicament_id = m.id
            """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vente vente = new Vente(
                    rs.getInt("id"),
                    rs.getInt("medicament_id"),
                    rs.getDouble("quantite"),
                    rs.getDouble("prix_unitaire"),
                    rs.getString("unite"),
                    rs.getDate("date_vente").toLocalDate()
                );
                vente.setMedicamentNom(rs.getString("medicament_nom"));
                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventes;
    }

    public void ajouterVente(Vente vente) {
        String sql = "INSERT INTO ventes (medicament_id, quantite, prix_unitaire, unite, date_vente) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vente.getMedicamentId());
            pstmt.setDouble(2, vente.getQuantite());
            pstmt.setDouble(3, vente.getPrixUnitaire());
            pstmt.setString(4, vente.getUnite());
            pstmt.setDate(5, Date.valueOf(vente.getDateVente()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerVente(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ventes WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierVente(Vente vente) {
        String sql = "UPDATE ventes SET medicament_id = ?, quantite = ?, prix_unitaire = ?, unite = ?, date_vente = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vente.getMedicamentId());
            pstmt.setDouble(2, vente.getQuantite());
            pstmt.setDouble(3, vente.getPrixUnitaire());
            pstmt.setString(4, vente.getUnite());
            pstmt.setDate(5, Date.valueOf(vente.getDateVente()));
            pstmt.setInt(6, vente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
