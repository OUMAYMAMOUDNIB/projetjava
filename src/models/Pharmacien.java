package models;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Represents a pharmacist user involved in stock operations.
 */
public class Pharmacien {
    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty role;
    private final StringProperty cin;
    private final StringProperty telephone;
    private final ObjectProperty<LocalDate> dateRecrutement;

    public Pharmacien(int id, String nom, String role, String cin, String telephone, LocalDate dateRecrutement) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.role = new SimpleStringProperty(role);
        this.cin = new SimpleStringProperty(cin);
        this.telephone = new SimpleStringProperty(telephone);
        this.dateRecrutement = new SimpleObjectProperty<>(dateRecrutement);
    }

    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getRole() { return role.get(); }
    public String getCin() { return cin.get(); }
    public String getTelephone() { return telephone.get(); }
    public LocalDate getDateRecrutement() { return dateRecrutement.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty roleProperty() { return role; }
    public StringProperty cinProperty() { return cin; }
    public StringProperty telephoneProperty() { return telephone; }
    public ObjectProperty<LocalDate> dateRecrutementProperty() { return dateRecrutement; }

    public void setNom(String nom) { this.nom.set(nom); }
    public void setRole(String role) { this.role.set(role); }
    public void setCin(String cin) { this.cin.set(cin); }
    public void setTelephone(String telephone) { this.telephone.set(telephone); }
    public void setDateRecrutement(LocalDate dateRecrutement) { this.dateRecrutement.set(dateRecrutement); }

    @Override
    public String toString() {
        return getNom();
    }
}
