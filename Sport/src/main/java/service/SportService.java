package service;

import domain.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SportRepository;

import java.util.List;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    public Sport getSportByName(String name) {
        return sportRepository.findByName(name).stream().findFirst().orElse(null);
    }

    public Sport addSport(Sport sport) {
        return sportRepository.save(sport);
    }
}
