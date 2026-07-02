package controllers;

import dao.StatistiqueDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import models.Statistique;

import java.util.List;

public class StatistiqueController {
    private static final double SEUIL_STOCK_BAS = 10;
    private static final int JOURS_EXPIRATION_PROCHE = 90;

    @FXML private Label labelTotalMedicaments;
    @FXML private Label labelStockBas;
    @FXML private Label labelExpirationProche;
    @FXML private Label labelChiffreAffaires;
    @FXML private Label labelTopMedicament;
    @FXML private ListView<String> listStockBas;
    @FXML private ListView<String> listExpirationProche;
    @FXML private LineChart<String, Number> lineChartVentes;
    @FXML private VBox topMedicamentsBox;

    private final StatistiqueDAO statistiqueDAO = new StatistiqueDAO();

    @FXML
    public void initialize() {
        chargerIndicateurs();
        chargerAlertes();
        chargerEvolutionVentes();
        chargerTopMedicaments();
    }

    private void chargerIndicateurs() {
        labelTotalMedicaments.setText(String.valueOf(statistiqueDAO.getNombreMedicaments()));
        labelStockBas.setText(String.valueOf(statistiqueDAO.getNombreStockBas(SEUIL_STOCK_BAS)));
        labelExpirationProche.setText(String.valueOf(statistiqueDAO.getNombreExpirationProche(JOURS_EXPIRATION_PROCHE)));
        labelChiffreAffaires.setText(String.format("%.2f DH", statistiqueDAO.getChiffreAffaires()));
        labelTopMedicament.setText(statistiqueDAO.getMedicamentLePlusVendu());
    }

    private void chargerAlertes() {
        listStockBas.setItems(FXCollections.observableArrayList(
                statistiqueDAO.getMedicamentsStockBas(SEUIL_STOCK_BAS)
        ));
        listExpirationProche.setItems(FXCollections.observableArrayList(
                statistiqueDAO.getMedicamentsExpirationProche(JOURS_EXPIRATION_PROCHE)
        ));

        if (listStockBas.getItems().isEmpty()) {
            listStockBas.setItems(FXCollections.observableArrayList("Aucun medicament sous le seuil."));
        }
        if (listExpirationProche.getItems().isEmpty()) {
            listExpirationProche.setItems(FXCollections.observableArrayList("Aucune expiration proche."));
        }
    }

    private void chargerEvolutionVentes() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        statistiqueDAO.getEvolutionVentesParMois().forEach(stat ->
                series.getData().add(new XYChart.Data<>(stat.getNom(), stat.getValeur()))
        );
        lineChartVentes.getData().setAll(series);
    }

    private void chargerTopMedicaments() {
        topMedicamentsBox.getChildren().clear();
        List<Statistique> topMedicaments = statistiqueDAO.getTopMedicamentsVendus(5);

        if (topMedicaments.isEmpty()) {
            Label empty = new Label("Aucune vente enregistree.");
            empty.getStyleClass().add("ranking-empty");
            topMedicamentsBox.getChildren().add(empty);
            return;
        }

        double max = topMedicaments.stream()
                .mapToDouble(Statistique::getValeur)
                .max()
                .orElse(1);

        int rank = 1;
        for (Statistique stat : topMedicaments) {
            Label rankLabel = new Label(String.valueOf(rank));
            rankLabel.getStyleClass().add("ranking-rank");

            Label nameLabel = new Label(stat.getNom());
            nameLabel.getStyleClass().add("ranking-name");
            nameLabel.setMinWidth(120);

            ProgressBar bar = new ProgressBar(max == 0 ? 0 : stat.getValeur() / max);
            bar.getStyleClass().add("ranking-progress");
            bar.setPrefWidth(190);

            Label valueLabel = new Label(String.format("%.0f", stat.getValeur()));
            valueLabel.getStyleClass().add("ranking-value");

            HBox row = new HBox(12, rankLabel, nameLabel, bar, valueLabel);
            row.setAlignment(Pos.CENTER_LEFT);
            row.getStyleClass().add("ranking-row");
            topMedicamentsBox.getChildren().add(row);
            rank++;
        }
    }
}
