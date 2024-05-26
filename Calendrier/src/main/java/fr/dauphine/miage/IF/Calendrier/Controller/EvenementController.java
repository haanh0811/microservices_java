package fr.dauphine.miage.IF.Calendrier.Controller;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;
import fr.dauphine.miage.IF.Calendrier.Service.EvenementService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
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

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<Evenement> l = service.getAll();
        if (l.isEmpty())
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé");
        return ok(l);
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
        if (l.isEmpty())
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le sport "+ name);
        return ok(l);
    }

    @GetMapping("/site/{name}")
    public ResponseEntity<?> getEvenementBySite(@PathVariable String name){
        List<Evenement> l = service.getBySite(name);
        if (l.isEmpty())
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour le sport "+ name);
        return ok(l);
    }

    @GetMapping("/date/{annee}-{jour}-{mois}")
    public ResponseEntity<?> getEvenementByDate(@PathVariable int annee, @PathVariable int mois, @PathVariable int jour){
        List<Evenement> l = service.getByDate(annee, mois, jour);
        if (l.isEmpty())
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé pour la date " + annee + "-" + mois + "-" + jour);
        return ok(l);
    }

    @GetMapping("/date/{anneeDebut}-{jourDebut}-{moisDebut}/{anneeFin}-{jourFin}-{moisFin}")
    public ResponseEntity<?> getEvenementByDate(@PathVariable int anneeDebut, @PathVariable int moisDebut, @PathVariable int jourDebut, @PathVariable int anneeFin, @PathVariable int moisFin, @PathVariable int jourFin){
        LocalDate dateDebut = LocalDate.of(anneeDebut, moisDebut, jourDebut);
        LocalDate dateFin = LocalDate.of(anneeFin, moisFin, jourFin);
        if (dateDebut.equals(dateFin) || dateDebut.isAfter(dateFin)){
            return status(HttpStatus.BAD_REQUEST).body("The first date must be strictly before the second");
        }
        List<Evenement> l = service.getByDate(dateDebut, dateFin);
        if (l.isEmpty())
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé entre " + anneeDebut + "-" + moisDebut + "-" + jourDebut + "et " + anneeFin + "-" + moisFin + "-" + jourFin);
        return ok(l);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvenementById(@PathVariable int id, @RequestBody Evenement newEvenement){
        Evenement oldEvenement = service.getById(id);
        if (oldEvenement==null){
            return status(HttpStatus.NOT_FOUND).body("Pas d'évenement trouvé avec l'id "+ id);
        }
        try{
            Evenement updated = service.save(oldEvenement, newEvenement);
            return ok(updated);
        }
        catch(Exception ex){
            return status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvenementById(@PathVariable int id){
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

}
