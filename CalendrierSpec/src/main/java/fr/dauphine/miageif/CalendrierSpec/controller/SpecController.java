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
        return specService.getAllSpects();
    }
    @GetMapping("/{id}")
    public Spec getSpecById(@PathVariable String id) {
        return specService.getSpecById(id);
    }

    @PostMapping("/add")
    public Spec addSpectator(@RequestBody Spec spectator) {
        return specService.addSpectator(spectator);
    }

    @PutMapping("/update/{id}")
    public Spec updateSpectator(@PathVariable String id, @RequestBody Spec updatedSpectator) {
        return specService.updateSpectator(id, updatedSpectator);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSpectator(@PathVariable String id) {
        specService.deleteSpectator(id);
    }

    @PostMapping("/{spectatorId}/events/add")
    public Spec addEventToSpectator(@PathVariable String spectatorId, @RequestBody CalendrierSpec event) {
        return specService.addEventToSpectator(spectatorId, event);
    }

    @PutMapping("/{spectatorId}/events/update/{eventId}")
    public Spec updateEventInSpectator(@PathVariable String spectatorId, @PathVariable String eventId, @RequestBody CalendrierSpec updatedEvent) {
        return specService.updateEventInSpectator(spectatorId, eventId, updatedEvent);
    }

    @DeleteMapping("/{spectatorId}/events/delete/{eventId}")
    public Spec deleteEventFromSpectator(@PathVariable String spectatorId, @PathVariable String eventId) {
        return specService.deleteEventFromSpectator(spectatorId, eventId);
    }
}

