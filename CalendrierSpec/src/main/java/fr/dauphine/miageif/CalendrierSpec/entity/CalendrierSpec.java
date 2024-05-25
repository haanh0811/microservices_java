package fr.dauphine.miageif.CalendrierSpec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

//Contient les détails de chaque événement auquel le spectateur prévoit d'assister
public class CalendrierSpec {
    private String eventId;
    private String sportName;
    private String site;
    private LocalDateTime eventDateTime;
}
