package controller;

import domain.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.SportService;

import java.util.List;

@RestController
@RequestMapping("/api/sports")
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping
    public List<Sport> getAllSports() {
        return sportService.getAllSports();
    }

    @GetMapping("/{name}")
    public Sport getSportByName(@PathVariable String name) {
        return sportService.getSportByName(name);
    }

    @PostMapping
    public Sport createSport(@RequestBody Sport sport) {
        return sportService.saveSport(sport);
    }
}
