package fr.dauphine.miage.IF.Calendrier.Service;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;

import java.time.LocalDate;
import java.util.List;


public interface EvenementService {
    Evenement createEvenement(Evenement e);

    Evenement getById(int id);

    List<Evenement> getBySport(String name);

    List<Evenement> getBySite(String name);

    List<Evenement> getByDate(int annee, int mois, int jour);

    List<Evenement> getByDate(LocalDate dateDebut, LocalDate dateFin);

    Evenement save(Evenement oldEvenement, Evenement newEvenement);

    List<Evenement> getAll();

    void delete(int id);
}
