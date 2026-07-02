package models;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Represents a medicine sale.
 */
public class Vente {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty medicamentId = new SimpleIntegerProperty();
    private final DoubleProperty quantite = new SimpleDoubleProperty();
    private final DoubleProperty prixUnitaire = new SimpleDoubleProperty();
    private final StringProperty unite = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateVente = new SimpleObjectProperty<>();
    private final StringProperty medicamentNom = new SimpleStringProperty();

    public Vente() {}

    public Vente(int id, int medicamentId, double quantite, double prixUnitaire, String unite, LocalDate dateVente) {
        this.id.set(id);
        this.medicamentId.set(medicamentId);
        this.quantite.set(quantite);
        this.prixUnitaire.set(prixUnitaire);
        this.unite.set(unite);
        this.dateVente.set(dateVente);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public int getMedicamentId() { return medicamentId.get(); }
    public void setMedicamentId(int value) { medicamentId.set(value); }
    public IntegerProperty medicamentIdProperty() { return medicamentId; }

    public double getQuantite() { return quantite.get(); }
    public void setQuantite(double value) { quantite.set(value); }
    public DoubleProperty quantiteProperty() { return quantite; }

    public double getPrixUnitaire() { return prixUnitaire.get(); }
    public void setPrixUnitaire(double value) { prixUnitaire.set(value); }
    public DoubleProperty prixUnitaireProperty() { return prixUnitaire; }

    public String getUnite() { return unite.get(); }
    public void setUnite(String value) { unite.set(value); }
    public StringProperty uniteProperty() { return unite; }

    public LocalDate getDateVente() { return dateVente.get(); }
    public void setDateVente(LocalDate value) { dateVente.set(value); }
    public ObjectProperty<LocalDate> dateVenteProperty() { return dateVente; }

    public double getMontant() {
        return getQuantite() * getPrixUnitaire();
    }

    public String getMedicamentNom() { return medicamentNom.get(); }
    public void setMedicamentNom(String value) { medicamentNom.set(value); }
    public StringProperty medicamentNomProperty() { return medicamentNom; }
}
