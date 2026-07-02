package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardController {

    @FXML
    private StackPane contenuPane;
    @FXML
    private VBox sidebar;

    @FXML
    public void initialize() {
        afficherStatistiques();
    }

    @FXML
    public void afficherMedicaments() {
        chargerInterface("/fxml/medicaments.fxml");
    }

    @FXML
    public void afficherApprovisionnements() {
        chargerInterface("/fxml/approvisionnements.fxml");
    }

    @FXML
    public void afficherVentes() {
        chargerInterface("/fxml/ventes.fxml");
    }

    @FXML
    private void afficherPharmaciens() {
        chargerInterface("/fxml/pharmaciens.fxml");
    }

    @FXML
    private void afficherStatistiques() {
        chargerInterface("/fxml/statistiques.fxml");
    }

    private void chargerInterface(String cheminFXML) {
        try {
            Parent vue = FXMLLoader.load(getClass().getResource(cheminFXML));
            contenuPane.getChildren().setAll(vue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
