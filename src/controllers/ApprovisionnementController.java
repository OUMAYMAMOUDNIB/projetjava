package controllers;

import dao.PharmacienDAO;
import dao.MedicamentDAO;
import dao.ApprovisionnementDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Pharmacien;
import models.Medicament;
import models.Approvisionnement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApprovisionnementController {

    @FXML private TableView<Approvisionnement> tableApprovisionnements;
    @FXML private TableColumn<Approvisionnement, Integer> colId;
    @FXML private TableColumn<Approvisionnement, String> colMedicamentNom;
    @FXML private TableColumn<Approvisionnement, String> colPharmacienNom;
    @FXML private TableColumn<Approvisionnement, Double> colQuantite;
    @FXML private TableColumn<Approvisionnement, LocalDate> colDateApprovisionnement;

    @FXML private ComboBox<Medicament> comboMedicamentId;
    @FXML private ComboBox<Pharmacien> comboPharmacienId;
    @FXML private TextField txtQuantite;
    @FXML private DatePicker dateApprovisionnement;

    private final ApprovisionnementDAO approvisionnementDAO = new ApprovisionnementDAO();
    private final MedicamentDAO medicamentDAO = new MedicamentDAO();
    private final PharmacienDAO pharmacienDAO = new PharmacienDAO();

    private ObservableList<Approvisionnement> approvisionnementList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadApprovisionnements();
        loadComboBoxData();
        setupSelectionListener();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colMedicamentNom.setCellValueFactory(cellData -> cellData.getValue().medicamentNomProperty());
        colPharmacienNom.setCellValueFactory(cellData -> cellData.getValue().pharmacienNomProperty());
        colQuantite.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty().asObject());
        colDateApprovisionnement.setCellValueFactory(cellData -> cellData.getValue().dateApprovisionnementProperty());
    }

    private void loadApprovisionnements() {
        approvisionnementList.clear();
        List<Approvisionnement> approvisionnements = approvisionnementDAO.getAllApprovisionnements();

        for (Approvisionnement approvisionnement : approvisionnements) {
            approvisionnement.setMedicamentNom(medicamentDAO.getMedicamentNomById(approvisionnement.getMedicamentId()));
            approvisionnement.setPharmacienNom(pharmacienDAO.getPharmacienNomById(approvisionnement.getPharmacienId()));
            approvisionnementList.add(approvisionnement);
        }

        tableApprovisionnements.setItems(approvisionnementList);
    }

    private void loadComboBoxData() {
        List<Medicament> allMedicaments = medicamentDAO.getAllMedicaments();
        List<Pharmacien> allPharmaciens = pharmacienDAO.getAllPharmaciens();

        List<Medicament> medicamentsUniques = allMedicaments.stream()
            .collect(java.util.stream.Collectors.collectingAndThen(
                java.util.stream.Collectors.toMap(
                    Medicament::getNom,
                    m -> m,
                    (existing, replacement) -> existing
                ),
                map -> new ArrayList<>(map.values())
            ));

        List<Pharmacien> pharmaciensUniques = allPharmaciens.stream()
            .collect(java.util.stream.Collectors.collectingAndThen(
                java.util.stream.Collectors.toMap(
                    Pharmacien::getNom,
                    p -> p,
                    (existing, replacement) -> existing
                ),
                map -> new ArrayList<>(map.values())
            ));

        comboMedicamentId.setItems(FXCollections.observableArrayList(medicamentsUniques));
        comboPharmacienId.setItems(FXCollections.observableArrayList(pharmaciensUniques));

        comboMedicamentId.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Medicament item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNom());
            }
        });
        comboMedicamentId.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Medicament item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNom());
            }
        });

        comboPharmacienId.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Pharmacien item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNom());
            }
        });
        comboPharmacienId.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Pharmacien item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNom());
            }
        });
    }

    private void setupSelectionListener() {
        tableApprovisionnements.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> populateForm(newValue));
    }

    private void populateForm(Approvisionnement approvisionnement) {
        if (approvisionnement != null) {
            Medicament medicament = comboMedicamentId.getItems().stream()
                    .filter(m -> m.getId() == approvisionnement.getMedicamentId())
                    .findFirst().orElse(null);

            Pharmacien pharmacien = comboPharmacienId.getItems().stream()
                    .filter(p -> p.getId() == approvisionnement.getPharmacienId())
                    .findFirst().orElse(null);

            comboMedicamentId.setValue(medicament);
            comboPharmacienId.setValue(pharmacien);
            txtQuantite.setText(String.valueOf(approvisionnement.getQuantite()));
            dateApprovisionnement.setValue(approvisionnement.getDateApprovisionnement());
        }
    }

    @FXML
    private void ajouterApprovisionnement() {
        if (validateInput()) {
            Approvisionnement newApprovisionnement = new Approvisionnement(
                0,
                comboMedicamentId.getValue().getId(),
                comboPharmacienId.getValue().getId(),
                Double.parseDouble(txtQuantite.getText()),
                dateApprovisionnement.getValue()
            );

            if (approvisionnementDAO.ajouterApprovisionnement(newApprovisionnement)) {
                loadApprovisionnements();
                clearForm();
            } else {
                showAlert("Erreur", "Echec de l'ajout de l'approvisionnement");
            }
        }
    }

    @FXML
    private void modifierApprovisionnement() {
        Approvisionnement selected = tableApprovisionnements.getSelectionModel().getSelectedItem();
        if (selected != null && validateInput()) {
            selected.setMedicamentId(comboMedicamentId.getValue().getId());
            selected.setPharmacienId(comboPharmacienId.getValue().getId());
            selected.setQuantite(Double.parseDouble(txtQuantite.getText()));
            selected.setDateApprovisionnement(dateApprovisionnement.getValue());

            if (approvisionnementDAO.modifierApprovisionnement(selected)) {
                loadApprovisionnements();
                clearForm();
            } else {
                showAlert("Erreur", "Echec de la modification de l'approvisionnement");
            }
        } else {
            showAlert("Aucune selection", "Veuillez selectionner un approvisionnement a modifier");
        }
    }

    @FXML
    private void supprimerApprovisionnement() {
        Approvisionnement selected = tableApprovisionnements.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous supprimer cet approvisionnement ?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                if (approvisionnementDAO.supprimerApprovisionnement(selected.getId())) {
                    loadApprovisionnements();
                    clearForm();
                } else {
                    showAlert("Erreur", "Echec de la suppression de l'approvisionnement");
                }
            }
        } else {
            showAlert("Aucune selection", "Veuillez selectionner un approvisionnement a supprimer");
        }
    }

    private void clearForm() {
        comboMedicamentId.setValue(null);
        comboPharmacienId.setValue(null);
        txtQuantite.clear();
        dateApprovisionnement.setValue(null);
        tableApprovisionnements.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (comboMedicamentId.getValue() == null) {
            errorMessage += "Veuillez selectionner un medicament!\n";
        }
        if (comboPharmacienId.getValue() == null) {
            errorMessage += "Veuillez selectionner un pharmacien!\n";
        }
        if (txtQuantite.getText() == null || txtQuantite.getText().isEmpty()) {
            errorMessage += "Veuillez entrer une quantite!\n";
        } else {
            try {
                Double.parseDouble(txtQuantite.getText());
            } catch (NumberFormatException e) {
                errorMessage += "La quantite doit etre un nombre valide!\n";
            }
        }
        if (dateApprovisionnement.getValue() == null) {
            errorMessage += "Veuillez selectionner une date d'approvisionnement!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert("Erreur de validation", errorMessage);
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
