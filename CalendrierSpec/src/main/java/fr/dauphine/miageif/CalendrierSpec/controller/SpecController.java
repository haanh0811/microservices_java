package fr.dauphine.miageif.CalendrierSpec.controller;


import fr.dauphine.miageif.CalendrierSpec.entity.CalendrierSpec;
import fr.dauphine.miageif.CalendrierSpec.entity.Spec;
import fr.dauphine.miageif.CalendrierSpec.service.impl.SpecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/spec")
public class SpecController {

    @Autowired
    private SpecServiceImpl specService;

    @GetMapping("/all")
    public List<Spec> getAllSpecs(){
        return specService.getAllSpecs();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSpecById(@PathVariable String id) {
        try{
            return ok(specService.getSpecById(id));
        }
        catch(NoSuchElementException nsee){
            System.out.println(nsee.getMessage());
            System.out.println(nsee.getStackTrace());
            if (nsee.getMessage().contains("Spectator not found"))
                return status(HttpStatus.NOT_FOUND).body("No event found for spectator id "+ id);
            throw nsee;
        }
    }

    @PostMapping("/add")
    public Spec addSpec(@RequestBody Spec spectator) {
        return specService.addSpec(spectator);
    }

    @PutMapping("/update/{id}")
    public Spec updateSpec(@PathVariable String id, @RequestBody Spec updatedSpectator) {
        return specService.updateSpec(id, updatedSpectator);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSpec(@PathVariable String id) {
        specService.deleteSpec(id);
    }

    @PostMapping("/{spectatorId}/events/add")
    public ResponseEntity<?> addEventToSpec(@PathVariable String spectatorId, @RequestBody CalendrierSpec event) {
        Spec s =  specService.addEventToSpec(spectatorId, event);
        if (s==null)
            return status(HttpStatus.BAD_REQUEST).body("Could not retrieve either the spectator or the event");
        return ok(s);
    }

    @DeleteMapping("/{spectatorId}/events/delete/{eventId}")
    public Spec deleteEventFromSpec(@PathVariable String spectatorId, @PathVariable String eventId) {
        return specService.deleteEventFromSpec(spectatorId, eventId);
    }
}

