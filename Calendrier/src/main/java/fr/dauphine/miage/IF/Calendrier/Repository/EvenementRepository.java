package fr.dauphine.miage.IF.Calendrier.Repository;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    List<Evenement> findAll();

    Optional<Evenement> findById(UUID id);

    List<Evenement> findBySport(String name);

    List<Evenement> findBySite(String name);

    List<Evenement> findByDate(LocalDate jour);

    List<Evenement> findByDateBetween(LocalDate dateDebut, LocalDate dateFin);

    void deleteById(UUID id);

    Evenement getBySiteAndSportAndDateAndHeure(String site, String sport, LocalDate date, LocalTime heure);
}
