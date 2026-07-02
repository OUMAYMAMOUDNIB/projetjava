package models;

/**
 * Represents a named numeric value used by dashboard charts.
 */
public class Statistique {
    private String nom;
    private double valeur;

    public Statistique(String nom, double valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() {
        return nom;
    }

    public double getValeur() {
        return valeur;
    }
}
