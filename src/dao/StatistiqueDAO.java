package dao;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import models.Statistique;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides aggregated data for dashboard charts.
 */
public class StatistiqueDAO {

    public ObservableList<PieChart.Data> getRepartitionParCategorieIncluantZero() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String sql = """
            SELECT COALESCE(NULLIF(m.categorie, ''), 'Sans categorie') AS categorie,
                   COALESCE(SUM(a.quantite), 0) AS total
            FROM medicaments m
            LEFT JOIN approvisionnements a ON m.id = a.medicament_id
            GROUP BY COALESCE(NULLIF(m.categorie, ''), 'Sans categorie')
            ORDER BY categorie
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                data.add(new PieChart.Data(rs.getString("categorie"), rs.getInt("total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<Statistique> getEvolutionVentesParMois() {
        List<Statistique> list = new ArrayList<>();
        String sql = """
            SELECT MONTH(date_vente) AS mois, SUM(quantite * prix_unitaire) AS total
            FROM ventes
            GROUP BY mois
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Statistique("Mois " + rs.getInt("mois"), rs.getDouble("total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Statistique> getAnciennetePharmaciens() {
        List<Statistique> list = new ArrayList<>();
        String sql = """
            SELECT nom, TIMESTAMPDIFF(YEAR, date_recrutement, CURDATE()) AS anciennete
            FROM pharmaciens
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Statistique(rs.getString("nom"), rs.getInt("anciennete")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Statistique> getQuantiteParPharmacien() {
        List<Statistique> list = new ArrayList<>();
        String sql = """
            SELECT p.nom, SUM(a.quantite) AS total
            FROM approvisionnements a
            JOIN pharmaciens p ON a.pharmacien_id = p.id
            GROUP BY p.nom
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Statistique(rs.getString("nom"), rs.getDouble("total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getNombreMedicaments() {
        String sql = "SELECT COUNT(*) AS total FROM medicaments";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getNombreStockBas(double seuil) {
        String sql = """
            SELECT COUNT(*) AS total
            FROM (
                SELECT
                    m.id,
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
            ) s
            WHERE s.stock < ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, seuil);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getNombreExpirationProche(int jours) {
        String sql = """
            SELECT COUNT(*) AS total
            FROM medicaments
            WHERE date_expiration BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY)
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jours);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getChiffreAffaires() {
        String sql = "SELECT COALESCE(SUM(quantite * prix_unitaire), 0) AS total FROM ventes";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getMedicamentLePlusVendu() {
        String sql = """
            SELECT m.nom, SUM(v.quantite) AS total
            FROM ventes v
            JOIN medicaments m ON v.medicament_id = m.id
            GROUP BY m.nom
            ORDER BY total DESC
            LIMIT 1
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("nom") + " (" + rs.getDouble("total") + ")";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Aucune vente";
    }

    public List<String> getMedicamentsStockBas(double seuil) {
        List<String> list = new ArrayList<>();
        String sql = """
            SELECT nom, stock
            FROM (
                SELECT
                    m.nom,
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
            ) s
            WHERE stock < ?
            ORDER BY stock ASC, nom ASC
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, seuil);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("nom") + " - stock: " + rs.getDouble("stock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getMedicamentsExpirationProche(int jours) {
        List<String> list = new ArrayList<>();
        String sql = """
            SELECT nom, date_expiration, DATEDIFF(date_expiration, CURDATE()) AS jours_restants
            FROM medicaments
            WHERE date_expiration BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY)
            ORDER BY date_expiration ASC, nom ASC
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jours);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("nom") + " - " + rs.getDate("date_expiration") + " (" + rs.getInt("jours_restants") + " jours)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Statistique> getTopMedicamentsVendus(int limite) {
        List<Statistique> list = new ArrayList<>();
        String sql = """
            SELECT m.nom, SUM(v.quantite) AS total
            FROM ventes v
            JOIN medicaments m ON v.medicament_id = m.id
            GROUP BY m.nom
            ORDER BY total DESC
            LIMIT ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Statistique(rs.getString("nom"), rs.getDouble("total")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
