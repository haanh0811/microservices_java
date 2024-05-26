package fr.dauphine.miageif.Sport.controller;

import fr.dauphine.miageif.Sport.entity.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import fr.dauphine.miageif.Sport.service.impl.SportServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/sports")
public class SportController {

    @Autowired
    private SportServiceImpl sportService;

    public SportController(SportServiceImpl sportService) {
        this.sportService = sportService;
    }

    @GetMapping("/all")
    public List<Sport> getAllSports() {
        return sportService.getAllSports();
    }

    @GetMapping("/{name}")
    public Sport getSportByName(@PathVariable String name) {
        return sportService.getSportByName(name);
    }

    @PostMapping("/insert")
    public Sport createSport(@RequestBody Sport sport) {
        return sportService.addSport(sport);
    }
    @PutMapping("/updateLatitudeMin/{id}")
    public Sport updateLatitudeMin(@PathVariable String id, @RequestBody double latitudeMin) {
        return sportService.updateLatitudeMin(id, latitudeMin);
    }
    @PutMapping("/updateId/{oldId}")
    public Sport updateId(@PathVariable String oldId, @RequestBody String newId) {
        return sportService.updateId(oldId, newId);
    }

    @PutMapping("/updateName/{id}")
    public Sport updateName(@PathVariable String id, @RequestBody String newName) {
        return sportService.updateName(id, newName);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSport(@PathVariable String id) {
        sportService.deleteSport(id);
    }

}
