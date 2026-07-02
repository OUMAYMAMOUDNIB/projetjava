package controllers;

import dao.MedicamentDAO;
import dao.VenteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Medicament;
import models.Vente;

import java.time.LocalDate;

public class VenteController {

    @FXML private TableView<Vente> tableVentes;
    @FXML private TableColumn<Vente, Integer> colId;
    @FXML private TableColumn<Vente, String> colMedicament;
    @FXML private TableColumn<Vente, Double> colQuantite;
    @FXML private TableColumn<Vente, Double> colPrixUnitaire;
    @FXML private TableColumn<Vente, String> colUnite;
    @FXML private TableColumn<Vente, LocalDate> colDateVente;
    @FXML private TableColumn<Vente, Double> colMontant;

    @FXML private ComboBox<Medicament> comboMedicament;
    @FXML private TextField txtQuantite;
    @FXML private TextField txtPrixUnitaire;
    @FXML private TextField txtUnite;
    @FXML private DatePicker dateVente;

    private final VenteDAO venteDAO = new VenteDAO();
    private final MedicamentDAO medicamentDAO = new MedicamentDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colMedicament.setCellValueFactory(data -> data.getValue().medicamentNomProperty());
        colQuantite.setCellValueFactory(data -> data.getValue().quantiteProperty().asObject());
        colPrixUnitaire.setCellValueFactory(data -> data.getValue().prixUnitaireProperty().asObject());
        colUnite.setCellValueFactory(data -> data.getValue().uniteProperty());
        colDateVente.setCellValueFactory(data -> data.getValue().dateVenteProperty());
        colMontant.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getMontant()).asObject());

        comboMedicament.setItems(FXCollections.observableArrayList(medicamentDAO.getAllMedicaments()));
        loadVentes();

        tableVentes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Medicament selectedMedicament = comboMedicament.getItems().stream()
                        .filter(m -> m.getId() == newVal.getMedicamentId())
                        .findFirst().orElse(null);

                comboMedicament.setValue(selectedMedicament);
                txtQuantite.setText(String.valueOf(newVal.getQuantite()));
                txtPrixUnitaire.setText(String.valueOf(newVal.getPrixUnitaire()));
                txtUnite.setText(newVal.getUnite());
                dateVente.setValue(newVal.getDateVente());
            }
        });
    }

    private void loadVentes() {
        ObservableList<Vente> ventes = FXCollections.observableArrayList(venteDAO.getAllVentes());
        tableVentes.setItems(ventes);
    }

    @FXML
    private void ajouterVente() {
        try {
            Medicament medicament = comboMedicament.getValue();
            if (medicament == null) return;

            double quantite = Double.parseDouble(txtQuantite.getText());
            double prixUnitaire = Double.parseDouble(txtPrixUnitaire.getText());
            String unite = txtUnite.getText();
            LocalDate date = dateVente.getValue();

            Vente vente = new Vente(0, medicament.getId(), quantite, prixUnitaire, unite, date);
            vente.setMedicamentNom(medicament.getNom());
            venteDAO.ajouterVente(vente);
            loadVentes();

            comboMedicament.setValue(null);
            txtQuantite.clear();
            txtPrixUnitaire.clear();
            txtUnite.clear();
            dateVente.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerVente() {
        Vente selected = tableVentes.getSelectionModel().getSelectedItem();
        if (selected != null) {
            venteDAO.supprimerVente(selected.getId());
            loadVentes();
        }
    }

    @FXML
    private void modifierVente() {
        Vente selected = tableVentes.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Medicament medicament = comboMedicament.getValue();
                if (medicament == null) return;

                selected.setMedicamentId(medicament.getId());
                selected.setMedicamentNom(medicament.getNom());
                selected.setQuantite(Double.parseDouble(txtQuantite.getText()));
                selected.setPrixUnitaire(Double.parseDouble(txtPrixUnitaire.getText()));
                selected.setUnite(txtUnite.getText());
                selected.setDateVente(dateVente.getValue());

                venteDAO.modifierVente(selected);
                loadVentes();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
