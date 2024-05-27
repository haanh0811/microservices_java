package fr.dauphine.miage.IF.Calendrier.Service;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


public interface EvenementService {
    Evenement createEvenement(Evenement e);

    Evenement getById(UUID id);

    List<Evenement> getBySport(String name);

    List<Evenement> getBySite(String name);

    Evenement getBySiteAndSportAndDateAndHeure(String site, String sport, LocalDate date, LocalTime heure);

    List<Evenement> getByDate(int annee, int mois, int jour);

    List<Evenement> getByDate(LocalDate dateDebut, LocalDate dateFin);

    Evenement save(Evenement oldEvenement, Evenement newEvenement);

    List<Evenement> getAll();

    void delete(UUID id);

    void updateSportName(Evenement evenement, String newSport);

    void updateSiteName(Evenement evenement, String newSite);
}
