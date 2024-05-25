package fr.dauphine.miage.IF.Calendrier.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteModel {
    @JsonProperty("name")
    private String nom;

    @JsonProperty("city")
    private VilleModel ville;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public VilleModel getVille() {
        return ville;
    }

    public void setVille(VilleModel ville) {
        this.ville = ville;
    }
}
