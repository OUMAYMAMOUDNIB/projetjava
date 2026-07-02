package dao;

import database.DBConnection;
import models.Pharmacien;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles database operations for pharmacists.
 */
public class PharmacienDAO {
    private Connection connection;

    public PharmacienDAO() {
        this.connection = DBConnection.getConnection();
    }

    public String getPharmacienNomById(int id) {
        String query = "SELECT nom FROM pharmaciens WHERE id = ?";
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

    public Map<Integer, String> getAllPharmaciensWithNames() {
        Map<Integer, String> pharmaciens = new HashMap<>();
        String query = "SELECT id, nom FROM pharmaciens";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                pharmaciens.put(rs.getInt("id"), rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pharmaciens;
    }

    public List<Pharmacien> getAllPharmaciens() {
        List<Pharmacien> pharmaciens = new ArrayList<>();
        String sql = "SELECT * FROM pharmaciens";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pharmaciens.add(new Pharmacien(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("role"),
                    rs.getString("cin"),
                    rs.getString("telephone"),
                    rs.getDate("date_recrutement").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pharmaciens;
    }

    public void ajouterPharmacien(Pharmacien pharmacien) {
        String sql = "INSERT INTO pharmaciens (nom, role, cin, telephone, date_recrutement) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pharmacien.getNom());
            pstmt.setString(2, pharmacien.getRole());
            pstmt.setString(3, pharmacien.getCin());
            pstmt.setString(4, pharmacien.getTelephone());
            pstmt.setDate(5, Date.valueOf(pharmacien.getDateRecrutement()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerPharmacien(int id) {
        String sql = "DELETE FROM pharmaciens WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierPharmacien(Pharmacien pharmacien) {
        String sql = "UPDATE pharmaciens SET nom = ?, role = ?, cin = ?, telephone = ?, date_recrutement = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pharmacien.getNom());
            pstmt.setString(2, pharmacien.getRole());
            pstmt.setString(3, pharmacien.getCin());
            pstmt.setString(4, pharmacien.getTelephone());
            pstmt.setDate(5, Date.valueOf(pharmacien.getDateRecrutement()));
            pstmt.setInt(6, pharmacien.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
