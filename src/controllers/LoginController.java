package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import dao.UtilisateurDAO;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtPasswordVisible;
    @FXML private Label errorLabel;
    @FXML private ImageView iconView;

    private boolean passwordVisible = false;

    @FXML
    public void initialize() {
    	Platform.runLater(() -> txtUsername.getParent().requestFocus());
        txtPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!passwordVisible) txtPasswordVisible.setText(newVal);
        });

        txtPasswordVisible.textProperty().addListener((obs, oldVal, newVal) -> {
            if (passwordVisible) txtPassword.setText(newVal);
        });

        txtPasswordVisible.setVisible(false);
        txtPasswordVisible.setManaged(false);

        // 👁️ Mettre l’icône initiale
        setPasswordIcon("eye.png");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = passwordVisible ? txtPasswordVisible.getText() : txtPassword.getText();

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

        if (utilisateurDAO.verifierConnexion(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) txtUsername.getScene().getWindow();
                stage.setScene(scene);
                stage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            errorLabel.setText("Nom d'utilisateur ou mot de passe invalide.");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            txtPasswordVisible.setText(txtPassword.getText());
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);
            setPasswordIcon("eye-off.png");
        } else {
            txtPassword.setText(txtPasswordVisible.getText());
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);
            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);
            setPasswordIcon("eye.png");
        }
    }

    // 📌 C’est ici que tu places la méthode utilitaire
    private void setPasswordIcon(String iconFileName) {
        Image icon = new Image(getClass().getResourceAsStream("/images/" + iconFileName));
        iconView.setImage(icon);
    }
}
