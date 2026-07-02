package controllers;

import dao.MedicamentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Medicament;

import java.time.LocalDate;

public class MedicamentController {

    @FXML private TableView<Medicament> tableMedicaments;
    @FXML private TableColumn<Medicament, Integer> colId;
    @FXML private TableColumn<Medicament, String> colNom;
    @FXML private TableColumn<Medicament, String> colCategorie;
    @FXML private TableColumn<Medicament, LocalDate> colDateEntree;
    @FXML private TableColumn<Medicament, LocalDate> colDateExpiration;
    @FXML private TableColumn<Medicament, Float> stockColumn;

    @FXML private TextField txtNom;
    @FXML private TextField txtCategorie;
    @FXML private DatePicker dateEntree;
    @FXML private DatePicker dateExpiration;

    private final MedicamentDAO medicamentDAO = new MedicamentDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNom.setCellValueFactory(data -> data.getValue().nomProperty());
        colCategorie.setCellValueFactory(data -> data.getValue().categorieProperty());
        colDateEntree.setCellValueFactory(data -> data.getValue().dateEntreeProperty());
        colDateExpiration.setCellValueFactory(data -> data.getValue().dateExpirationProperty());
        stockColumn.setCellValueFactory(data -> data.getValue().stockProperty().asObject());

        chargerMedicamentsAvecStock();

        tableMedicaments.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtNom.setText(newVal.getNom());
                txtCategorie.setText(newVal.getCategorie());
                dateEntree.setValue(newVal.getDateEntree());
                dateExpiration.setValue(newVal.getDateExpiration());
            }
        });
    }

    private void chargerMedicamentsAvecStock() {
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList(medicamentDAO.getMedicamentsAvecStock());
        tableMedicaments.setItems(medicaments);
    }

    @FXML
    private void ajouterMedicament() {
        String nom = txtNom.getText().trim();
        String categorie = txtCategorie.getText().trim();
        LocalDate entree = dateEntree.getValue();
        LocalDate expiration = dateExpiration.getValue();

        if (nom.isEmpty() || categorie.isEmpty() || entree == null || expiration == null) {
            return;
        }

        Medicament medicament = new Medicament(0, nom, categorie, entree, expiration);
        medicamentDAO.ajouterMedicament(medicament);
        chargerMedicamentsAvecStock();
        clearForm();
    }

    @FXML
    private void supprimerMedicament() {
        Medicament selected = tableMedicaments.getSelectionModel().getSelectedItem();
        if (selected != null) {
            medicamentDAO.supprimerMedicament(selected.getId());
            chargerMedicamentsAvecStock();
            clearForm();
        }
    }

    @FXML
    private void modifierMedicament() {
        Medicament selected = tableMedicaments.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(txtNom.getText());
            selected.setCategorie(txtCategorie.getText());
            selected.setDateEntree(dateEntree.getValue());
            selected.setDateExpiration(dateExpiration.getValue());

            medicamentDAO.modifierMedicament(selected);
            chargerMedicamentsAvecStock();
            clearForm();
        }
    }

    private void clearForm() {
        txtNom.clear();
        txtCategorie.clear();
        dateEntree.setValue(null);
        dateExpiration.setValue(null);
    }
}
