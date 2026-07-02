package controllers;

import dao.PharmacienDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Pharmacien;

public class PharmacienController {

    @FXML private TableView<Pharmacien> tablePharmaciens;
    @FXML private TableColumn<Pharmacien, Integer> colId;
    @FXML private TableColumn<Pharmacien, String> colNom;
    @FXML private TableColumn<Pharmacien, String> colRole;
    @FXML private TableColumn<Pharmacien, String> colCin;
    @FXML private TableColumn<Pharmacien, String> colTelephone;
    @FXML private TableColumn<Pharmacien, java.time.LocalDate> colDateRecrutement;

    @FXML private TextField txtNom;
    @FXML private TextField txtRole;
    @FXML private TextField txtCin;
    @FXML private TextField txtTelephone;
    @FXML private DatePicker dateRecrutement;

    private final PharmacienDAO pharmacienDAO = new PharmacienDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNom.setCellValueFactory(data -> data.getValue().nomProperty());
        colRole.setCellValueFactory(data -> data.getValue().roleProperty());
        colCin.setCellValueFactory(data -> data.getValue().cinProperty());
        colTelephone.setCellValueFactory(data -> data.getValue().telephoneProperty());
        colDateRecrutement.setCellValueFactory(data -> data.getValue().dateRecrutementProperty());

        loadPharmaciens();

        tablePharmaciens.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtNom.setText(newVal.getNom());
                txtRole.setText(newVal.getRole());
                txtCin.setText(newVal.getCin());
                txtTelephone.setText(newVal.getTelephone());
                dateRecrutement.setValue(newVal.getDateRecrutement());
            }
        });
    }

    private void loadPharmaciens() {
        ObservableList<Pharmacien> pharmaciens = FXCollections.observableArrayList(pharmacienDAO.getAllPharmaciens());
        tablePharmaciens.setItems(pharmaciens);
    }

    @FXML
    private void ajouterPharmacien() {
        Pharmacien pharmacien = new Pharmacien(0, txtNom.getText(), txtRole.getText(), txtCin.getText(), txtTelephone.getText(), dateRecrutement.getValue());
        pharmacienDAO.ajouterPharmacien(pharmacien);
        loadPharmaciens();
        clearFields();
    }

    @FXML
    private void supprimerPharmacien() {
        Pharmacien selected = tablePharmaciens.getSelectionModel().getSelectedItem();
        if (selected != null) {
            pharmacienDAO.supprimerPharmacien(selected.getId());
            loadPharmaciens();
            clearFields();
        }
    }

    @FXML
    private void modifierPharmacien() {
        Pharmacien selected = tablePharmaciens.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(txtNom.getText());
            selected.setRole(txtRole.getText());
            selected.setCin(txtCin.getText());
            selected.setTelephone(txtTelephone.getText());
            selected.setDateRecrutement(dateRecrutement.getValue());
            pharmacienDAO.modifierPharmacien(selected);
            loadPharmaciens();
            clearFields();
        }
    }

    private void clearFields() {
        txtNom.clear();
        txtRole.clear();
        txtCin.clear();
        txtTelephone.clear();
        dateRecrutement.setValue(null);
    }
}
