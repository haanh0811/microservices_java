package fr.dauphine.miage.IF.Calendrier.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VilleModel {

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonProperty("name")
    private String nom;
}
