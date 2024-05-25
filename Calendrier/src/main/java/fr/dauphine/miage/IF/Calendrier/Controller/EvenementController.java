package fr.dauphine.miage.IF.Calendrier.Controller;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;
import fr.dauphine.miage.IF.Calendrier.Service.EvenementService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/evenement")
public class EvenementController {


    private final EvenementService service;

    public EvenementController(EvenementService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Evenement e){
        try{
            service.createEvenement(e);
            return ok(e);
        }
        catch(Exception ex){
            return status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvenementById(@PathVariable int id){
        Evenement e = service.getById(id);
        if (e==null){
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé avec l'id "+ id);
        }
        return ok(e);
    }

    @GetMapping("/sport/{name}")
    public ResponseEntity<?> getEvenementBySport(@PathVariable String name){
        List<Evenement> l = service.getBySport(name);
        if (l.size() == 0)
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le sport "+ name);
        return ok(l);
    }

    @GetMapping("/site/{name}")
    public ResponseEntity<?> getEvenementBySite(@PathVariable String name){
        List<Evenement> l = service.getBySite(name);
        if (l.size() == 0)
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le sport "+ name);
        return ok(l);
    }

    @GetMapping("/date/{annee}-{jour}-{mois}")
    public ResponseEntity<?> getEvenementBySite(@PathVariable int annee, @PathVariable int mois, @PathVariable int jour){
        List<Evenement> l = service.getByDate(annee, mois, jour);
        if (l.size() == 0)
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour la date " + annee + "-" + mois + "-" + jour);
        return ok(l);
    }


}
