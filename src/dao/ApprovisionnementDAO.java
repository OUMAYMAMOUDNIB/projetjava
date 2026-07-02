package dao;

import database.DBConnection;
import models.Approvisionnement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations for stock supplies.
 */
public class ApprovisionnementDAO {
    private Connection connection;

    public ApprovisionnementDAO() {
        this.connection = DBConnection.getConnection();
    }

    public boolean ajouterApprovisionnement(Approvisionnement approvisionnement) {
        String sql = "INSERT INTO approvisionnements (medicament_id, pharmacien_id, quantite, date_approvisionnement) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, approvisionnement.getMedicamentId());
            stmt.setInt(2, approvisionnement.getPharmacienId());
            stmt.setDouble(3, approvisionnement.getQuantite());
            stmt.setDate(4, Date.valueOf(approvisionnement.getDateApprovisionnement()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return false;

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    approvisionnement.setId(generatedKeys.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Approvisionnement> getAllApprovisionnements() {
        List<Approvisionnement> approvisionnements = new ArrayList<>();
        String sql = "SELECT * FROM approvisionnements";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                approvisionnements.add(new Approvisionnement(
                    rs.getInt("id"),
                    rs.getInt("medicament_id"),
                    rs.getInt("pharmacien_id"),
                    rs.getDouble("quantite"),
                    rs.getDate("date_approvisionnement").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return approvisionnements;
    }

    public boolean modifierApprovisionnement(Approvisionnement approvisionnement) {
        String sql = "UPDATE approvisionnements SET medicament_id = ?, pharmacien_id = ?, quantite = ?, date_approvisionnement = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, approvisionnement.getMedicamentId());
            stmt.setInt(2, approvisionnement.getPharmacienId());
            stmt.setDouble(3, approvisionnement.getQuantite());
            stmt.setDate(4, Date.valueOf(approvisionnement.getDateApprovisionnement()));
            stmt.setInt(5, approvisionnement.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean supprimerApprovisionnement(int id) {
        String sql = "DELETE FROM approvisionnements WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
