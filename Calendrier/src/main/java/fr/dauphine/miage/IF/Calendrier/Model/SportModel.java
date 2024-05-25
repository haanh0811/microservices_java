package fr.dauphine.miage.IF.Calendrier.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SportModel {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sites")
    private Object site;

    @JsonProperty("latitudeMin")
    private double latitude;
}
