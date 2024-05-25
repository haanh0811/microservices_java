package fr.dauphine.miage.IF.Calendrier.Service;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;

import java.util.List;


public interface EvenementService {
    Evenement createEvenement(Evenement e);

    Evenement getById(int id);

    List<Evenement> getBySport(String name);

    List<Evenement> getBySite(String name);

    List<Evenement> getByDate(int annee, int mois, int jour);
}
