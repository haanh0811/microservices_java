package fr.dauphine.miageif.CalendrierSpec.controller;


import fr.dauphine.miageif.CalendrierSpec.entity.CalendrierSpec;
import fr.dauphine.miageif.CalendrierSpec.entity.Spec;
import fr.dauphine.miageif.CalendrierSpec.service.impl.SpecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Spec getSpecById(@PathVariable String id) {
        return specService.getSpecById(id);
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
    public Spec addEventToSpec(@PathVariable String spectatorId, @RequestBody CalendrierSpec event) {
        return specService.addEventToSpec(spectatorId, event);
    }

    @DeleteMapping("/{spectatorId}/events/delete/{eventId}")
    public Spec deleteEventFromSpec(@PathVariable String spectatorId, @PathVariable String eventId) {
        return specService.deleteEventFromSpec(spectatorId, eventId);
    }
}

