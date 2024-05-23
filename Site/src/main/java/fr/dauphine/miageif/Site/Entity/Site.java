package fr.dauphine.miageif.Site.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Reference;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Site {

    //julien.haderer@dauphine.eu
    // Microservices%2024%

    // mdp instance : vuPOlsR9s9Vofen_GpzkQVejGBrVe9IWnYiXcAI3AZU

    @Id
    @JsonProperty("name")
    private String name;

    @Relationship(type = "SE_SITUE_DANS")
    @JsonProperty("city")
    private Ville seSitueDans;

    public Site(){}

    public Site(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ville getSeSitueDans() {
        return seSitueDans;
    }

    public void setSeSitueDans(Ville seSitueDans) {
        this.seSitueDans = seSitueDans;
    }
}
