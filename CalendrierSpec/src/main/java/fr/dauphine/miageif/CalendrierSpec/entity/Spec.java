package fr.dauphine.miageif.CalendrierSpec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "spectateurs")
@Data
@AllArgsConstructor
@Getter
@Setter
//Représente un spectateur avec un identifiant unique, un nom et une liste de plannings d'événements(CalendrierSpec)
public class Spec {
    @Id
    private String id;
    private String name;
    private List<CalendrierSpec> calendrierSpecList;
}
