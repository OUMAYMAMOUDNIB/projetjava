package models;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Represents a medicine stock supply operation.
 */
public class Approvisionnement {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty medicamentId = new SimpleIntegerProperty();
    private final IntegerProperty pharmacienId = new SimpleIntegerProperty();
    private final StringProperty medicamentNom = new SimpleStringProperty();
    private final StringProperty pharmacienNom = new SimpleStringProperty();
    private final DoubleProperty quantite = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDate> dateApprovisionnement = new SimpleObjectProperty<>();

    public Approvisionnement() {}

    public Approvisionnement(int id, int medicamentId, int pharmacienId, double quantite, LocalDate dateApprovisionnement) {
        this.id.set(id);
        this.medicamentId.set(medicamentId);
        this.pharmacienId.set(pharmacienId);
        this.quantite.set(quantite);
        this.dateApprovisionnement.set(dateApprovisionnement);
    }

    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty idProperty() { return id; }

    public int getMedicamentId() { return medicamentId.get(); }
    public void setMedicamentId(int value) { medicamentId.set(value); }
    public IntegerProperty medicamentIdProperty() { return medicamentId; }

    public int getPharmacienId() { return pharmacienId.get(); }
    public void setPharmacienId(int value) { pharmacienId.set(value); }
    public IntegerProperty pharmacienIdProperty() { return pharmacienId; }

    public String getMedicamentNom() { return medicamentNom.get(); }
    public void setMedicamentNom(String value) { medicamentNom.set(value); }
    public StringProperty medicamentNomProperty() { return medicamentNom; }

    public String getPharmacienNom() { return pharmacienNom.get(); }
    public void setPharmacienNom(String value) { pharmacienNom.set(value); }
    public StringProperty pharmacienNomProperty() { return pharmacienNom; }

    public double getQuantite() { return quantite.get(); }
    public void setQuantite(double value) { quantite.set(value); }
    public DoubleProperty quantiteProperty() { return quantite; }

    public LocalDate getDateApprovisionnement() { return dateApprovisionnement.get(); }
    public void setDateApprovisionnement(LocalDate value) { dateApprovisionnement.set(value); }
    public ObjectProperty<LocalDate> dateApprovisionnementProperty() { return dateApprovisionnement; }
}
