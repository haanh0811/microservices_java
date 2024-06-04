package fr.dauphine.miage.IF.Calendrier.Controller;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;
import fr.dauphine.miage.IF.Calendrier.Model.SiteModel;
import fr.dauphine.miage.IF.Calendrier.Model.SportModel;
import fr.dauphine.miage.IF.Calendrier.Service.EvenementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @GetMapping("/date/sport/{sport}/site/{site}/date/{annee}-{jour}-{mois}/heure/{heure}:{minute}")
    public ResponseEntity<?> getEvenementFromDetails(@PathVariable String sport, @PathVariable String site, @PathVariable int annee, @PathVariable int mois, @PathVariable int jour, @PathVariable int heure, @PathVariable int minute) {
        logger.info("Received request to get event with sport {} - site {} on {}/{}/{} {}:{}", sport, site, annee, mois, jour, heure, minute);

        LocalDate jourDate;
        LocalTime heureDate;
        try {
            jourDate = LocalDate.of(annee, mois, jour);
            heureDate = LocalTime.of(heure, minute);
        } catch (Exception ex) {
            logger.info("Date and Hour convesion failed {}", ex.getMessage());
            return status(HttpStatus.BAD_REQUEST).body("Les dates et l'heure doivent corresponde à des valeurs possibles");
        }

        Evenement e = service.getBySiteAndSportAndDateAndHeure(site, sport, jourDate, heureDate);
        if (e == null) {
            logger.info("No event found for {} - {} - {} - {}", site, sport, jourDate, heureDate);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé");
        }
        logger.info("Event found {}", e.toString());
        return ok(e);
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
        return ok(l);
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
        return ok(l);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvenementById(@PathVariable UUID id){
        Evenement e = service.getById(id);
        if (e==null){
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé avec l'id "+ id);
        }
        service.delete(id);
        return ok(e);
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
        return ok(l);
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
        return ok(l);
    }


    @GetMapping("/sport/site/{site}")
    public ResponseEntity<?> getSportFromSite(@PathVariable String site) {
        logger.info("Received request to get sport in site {}", site);
        List<Evenement> listEvent = service.getBySite(site);

        if (listEvent == null) {
            logger.info("No event found in {}", site);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé");
        }

        List<SportModel> listSport = new ArrayList<>();
        List<String> listStringSport = new ArrayList<>();
        for (Evenement e : listEvent){
            if (!listStringSport.contains(e.getSport()))
                listStringSport.add(e.getSport());
        }
        for (String s : listStringSport){
            String url = "http://localhost:5155/api/sports/" + s;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SportModel> response = restTemplate.getForEntity(url, SportModel.class);

            if (!response.getStatusCode().isError()){
                if (response.getBody() == null){
                    System.out.println(url);
                    throw new IllegalArgumentException("this site doesn't exist");
                }
            }
            else{
                System.out.println("Connexion impossible à" + url);
                status(HttpStatus.FAILED_DEPENDENCY).body("Connexion impossible à" + url);
            }
            listSport.add(response.getBody());
        }
        return ok(listSport);
    }

    @GetMapping("/sport/date/{annee}-{jour}-{mois}")
    public ResponseEntity<?> getSportFromDate(@PathVariable int annee, @PathVariable int mois, @PathVariable int jour) {
        logger.info("Received request to get sport at date {}-{}-{}", annee,jour, mois);

        LocalDate jourDate;
        try {
            jourDate = LocalDate.of(annee, mois, jour);
        } catch (Exception ex) {
            logger.info("Date convesion failed {}", ex.getMessage());
            return status(HttpStatus.BAD_REQUEST).body("Date conversion failed");
        }
        List<Evenement> listEvent = service.getByDate(annee,mois,jour);
        if (listEvent == null) {
            logger.info("No event at date {}-{}-{}", annee,jour, mois);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé à cette date");
        }

        List<SportModel> listSport = new ArrayList<>();
        List<String> listStringSport = new ArrayList<>();
        for (Evenement e : listEvent){
            if (!listStringSport.contains(e.getSport()))
                listStringSport.add(e.getSport());
        }
        for (String s : listStringSport){
            String url = "http://localhost:5155/api/sports/" + s;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SportModel> response = restTemplate.getForEntity(url, SportModel.class);

            if (!response.getStatusCode().isError()){
                if (response.getBody() == null){
                    System.out.println(url);
                    throw new IllegalArgumentException("this site doesn't exist");
                }
            }
            else{
                System.out.println("Connexion impossible à" + url);
                status(HttpStatus.FAILED_DEPENDENCY).body("Connexion impossible à" + url);
            }
            listSport.add(response.getBody());
        }
        return ok(listSport);
    }

    @GetMapping("/site/sport/{sport}")
    public ResponseEntity<?> getSiteFromSport(@PathVariable String sport) {
        logger.info("Received request to get site hosting sport {}", sport);
        List<Evenement> listEvent = service.getBySport(sport);

        if (listEvent == null) {
            logger.info("No event found  {}", sport);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé");
        }

        List<SiteModel> listSites = new ArrayList<>();
        List<String> listStringSite = new ArrayList<>();
        for (Evenement e : listEvent){
            if (!listStringSite.contains(e.getSite()))
                listStringSite.add(e.getSite());
        }
        for (String s : listStringSite){
            String url = "http://localhost:5153/site/" + s;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SiteModel> response = restTemplate.getForEntity(url, SiteModel.class);

            if (!response.getStatusCode().isError()){
                if (response.getBody() == null){
                    System.out.println(url);
                    throw new IllegalArgumentException("this site doesn't exist");
                }
            }
            else{
                System.out.println("Connexion impossible à" + url);
                status(HttpStatus.FAILED_DEPENDENCY).body("Connexion impossible à" + url);
            }
            listSites.add(response.getBody());
        }
        return ok(listSites);
    }

    @GetMapping("/site/date/{annee}-{jour}-{mois}")
    public ResponseEntity<?> getSiteFromDate(@PathVariable int annee, @PathVariable int mois, @PathVariable int jour) {
        logger.info("Received request to get event with sport at date {}-{}-{}", annee,jour, mois);
        List<Evenement> listEvent = service.getByDate(annee,mois,jour);
        if (listEvent == null) {
            logger.info("No event at date {}-{}-{}", annee,jour, mois);
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé à cette date");
        }

        List<SiteModel> listSites = new ArrayList<>();
        List<String> listStringSite = new ArrayList<>();
        for (Evenement e : listEvent){
            if (!listStringSite.contains(e.getSite()))
                listStringSite.add(e.getSite());
        }
        for (String s : listStringSite){
            String url = "http://localhost:5153/site/" + s;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SiteModel> response = restTemplate.getForEntity(url, SiteModel.class);

            if (!response.getStatusCode().isError()){
                if (response.getBody() == null){
                    System.out.println(url);
                    throw new IllegalArgumentException("this site doesn't exist");
                }
            }
            else{
                System.out.println("Connexion impossible à" + url);
                status(HttpStatus.FAILED_DEPENDENCY).body("Connexion impossible à" + url);
            }
            listSites.add(response.getBody());
        }
        return ok(listSites);
    }

}
