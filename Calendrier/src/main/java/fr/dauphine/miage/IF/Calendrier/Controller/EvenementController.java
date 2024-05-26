package fr.dauphine.miage.IF.Calendrier.Controller;

import fr.dauphine.miage.IF.Calendrier.CalendrierApplication;
import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;
import fr.dauphine.miage.IF.Calendrier.Service.EvenementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/evenement")
public class EvenementController {

    private static final Logger logger = LoggerFactory.getLogger(EvenementController.class);

    private final EvenementService service;

    public EvenementController(EvenementService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Evenement e){
        logger.info("Received request to create event: ", e);
        try{
            service.createEvenement(e);
            logger.info("event created: ", e);
            return ok(e);
        }
        catch(Exception ex){
            logger.warn("Creation failed - {}", ex.getMessage());
            return status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        logger.info("Received request to get all events");
        List<Evenement> l = service.getAll();
        if (l.isEmpty()){
            logger.info("No event found");
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé");
        }
        logger.info("Found {} elements in Calendrier", l.size());
        return ok(l);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvenementById(@PathVariable UUID id){
        logger.info("Received request to get event {}", id);
        Evenement e = service.getById(id);
        if (e==null){
            logger.info("No event found for id {}", id);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé avec l'id "+ id);
        }
        logger.info("Event {} found for id {}", e, id);
        return ok(e);
    }

    @GetMapping("/sport/{name}")
    public ResponseEntity<?> getEvenementBySport(@PathVariable String name){
        logger.info("Received request to get event with sport {}", name);
        List<Evenement> l = service.getBySport(name);
        if (l.isEmpty()){
            logger.info("No event found for sport {}", name);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le sport "+ name);
        }
        logger.info("Found {} elements with sport {}", l.size(),name);
        return ok(l);
    }

    @GetMapping("/site/{name}")
    public ResponseEntity<?> getEvenementBySite(@PathVariable String name){
        logger.info("Received request to get event with site {}", name);
        List<Evenement> l = service.getBySite(name);
        if (l.isEmpty()){
            logger.info("No event found for site {}", name);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le site "+ name);
        }
        logger.info("Found {} elements with site {}", l.size(),name);
        return ok(l);
    }

    @GetMapping("/date/{annee}-{jour}-{mois}")
    public ResponseEntity<?> getEvenementByDate(@PathVariable int annee, @PathVariable int mois, @PathVariable int jour){
        logger.info("Received request to get event with date {}-{}-{}", annee, mois, jour);
        List<Evenement> l = service.getByDate(annee, mois, jour);
        if (l.isEmpty()){
            logger.info("No event with date {}-{}-{}", annee, mois, jour);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour la date " + annee + "-" + mois + "-" + jour);
        }
        logger.info("Found {} event with date {}-{}-{}", l.size(), annee, mois, jour);
        return ok(l);
    }

    @GetMapping("/date/{anneeDebut}-{jourDebut}-{moisDebut}/{anneeFin}-{jourFin}-{moisFin}")
    public ResponseEntity<?> getEvenementByDate(@PathVariable int anneeDebut, @PathVariable int moisDebut, @PathVariable int jourDebut, @PathVariable int anneeFin, @PathVariable int moisFin, @PathVariable int jourFin){
        LocalDate dateDebut = LocalDate.of(anneeDebut, moisDebut, jourDebut);
        LocalDate dateFin = LocalDate.of(anneeFin, moisFin, jourFin);
        logger.info("Received request to get event between {} and {}", dateDebut, dateFin);
        if (dateDebut.equals(dateFin) || dateDebut.isAfter(dateFin)){
            logger.warn("Start date is not striclty before end date");
            return status(HttpStatus.BAD_REQUEST).body("The first date must be strictly before the second");
        }
        List<Evenement> l = service.getByDate(dateDebut, dateFin);
        if (l.isEmpty()){
            logger.info("No event found between {} and {}", dateDebut, dateFin);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé entre " + anneeDebut + "-" + moisDebut + "-" + jourDebut + "et " + anneeFin + "-" + moisFin + "-" + jourFin);
        }
        logger.info("Found {} events betwenn {} and {}",l.size(), dateDebut, dateFin);
        return ok(l);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvenementById(@PathVariable UUID id, @RequestBody Evenement newEvenement){
        logger.info("Received request to update event {}", id);
        Evenement oldEvenement = service.getById(id);
        if (oldEvenement==null){
            logger.info("No event found for id {}", id);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé avec l'id "+ id);
        }
        try{
            Evenement updated = service.save(oldEvenement, newEvenement);
            logger.info("event updated: ", updated);
            return ok(updated);
        }
        catch(Exception ex){
            logger.warn("Update failed - {}", ex.getMessage());
            return status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/sport/{oldName}/{newName}")
    public ResponseEntity<?> updateSportName(@PathVariable String oldName, @PathVariable String newName){
        logger.info("Received request to update sport {} to {}", oldName, newName);
        List<Evenement> l = service.getBySport(oldName);
        if (l.isEmpty()){
            logger.info("No event found with sport {}",oldName);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le sport "+ oldName);
        }
        for (Evenement evenement : l){
            service.updateSportName(evenement, newName);
            logger.info("event {} updated: {}",evenement.getId(), evenement);
        }
        return ok(null);
    }

    @PutMapping("/site/{oldName}/{newName}")
    public ResponseEntity<?> updateSiteName(@PathVariable String oldName, @PathVariable String newName){
        logger.info("Received request to update sport {} to {}", oldName, newName);
        List<Evenement> l = service.getBySite(oldName);
        if (l.isEmpty()){
            logger.info("No event found with site {}",oldName);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le site "+ oldName);
        }
        for (Evenement evenement : l){
            service.updateSiteName(evenement, newName);
            logger.info("event {} updated: {}",evenement.getId(), evenement);
        }
        return ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvenementById(@PathVariable UUID id){
        Evenement e = service.getById(id);
        if (e==null){
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé avec l'id "+ id);
        }
        service.delete(id);
        return ok(null);
    }

    @DeleteMapping("/sport/{name}")
    public ResponseEntity<?> deleteEvenementBySport(@PathVariable String name){
        List<Evenement> l = service.getBySport(name);
        if (l.isEmpty()){
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le sport "+ name);
        }
        for (Evenement evenement: l){
            service.delete(evenement.getId());
        }
        return ok(null);
    }

    @DeleteMapping("/site/{name}")
    public ResponseEntity<?> deleteEvenementBySite(@PathVariable String name){
        List<Evenement> l = service.getBySite(name);
        if (l.isEmpty()){
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le site "+ name);
        }
        for (Evenement evenement: l){
            service.delete(evenement.getId());
        }
        return ok(null);
    }

}
