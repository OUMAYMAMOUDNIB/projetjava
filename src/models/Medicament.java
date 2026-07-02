package models;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Represents a medicine managed by the pharmacy.
 */
public class Medicament {
    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty categorie;
    private final ObjectProperty<LocalDate> dateEntree;
    private final ObjectProperty<LocalDate> dateExpiration;
    private final FloatProperty stock;

    public Medicament(int id, String nom, String categorie, LocalDate dateEntree, LocalDate dateExpiration) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.categorie = new SimpleStringProperty(categorie);
        this.dateEntree = new SimpleObjectProperty<>(dateEntree);
        this.dateExpiration = new SimpleObjectProperty<>(dateExpiration);
        this.stock = new SimpleFloatProperty(0f);
    }

    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getCategorie() { return categorie.get(); }
    public LocalDate getDateEntree() { return dateEntree.get(); }
    public LocalDate getDateExpiration() { return dateExpiration.get(); }
    public float getStock() { return stock.get(); }

    public void setNom(String nom) { this.nom.set(nom); }
    public void setCategorie(String categorie) { this.categorie.set(categorie); }
    public void setDateEntree(LocalDate date) { this.dateEntree.set(date); }
    public void setDateExpiration(LocalDate date) { this.dateExpiration.set(date); }
    public void setStock(float stock) { this.stock.set(stock); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty categorieProperty() { return categorie; }
    public ObjectProperty<LocalDate> dateEntreeProperty() { return dateEntree; }
    public ObjectProperty<LocalDate> dateExpirationProperty() { return dateExpiration; }
    public FloatProperty stockProperty() { return stock; }

    @Override
    public String toString() {
        return getNom();
    }
}
