package fr.dauphine.miageif.Sport.service.impl;

import fr.dauphine.miageif.Sport.entity.Sport;
import fr.dauphine.miageif.Sport.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.dauphine.miageif.Sport.repository.SportRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SportServiceImpl implements SportService {

    @Autowired
    private SportRepository sportRepository;

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }


    public Sport addSport(Sport sport) {
        return sportRepository.save(sport);
    }
    public Sport updateLatitudeMin(String id, double latitudeMin) {
        Sport sport = sportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Sport not found with id " + id));
        sport.setLatitudeMin(latitudeMin);
        return sportRepository.save(sport);
    }

    public Sport updateId(String oldId, String newId) {
        Sport sport = sportRepository.findById(oldId).orElseThrow(() -> new NoSuchElementException("Sport not found with id " + oldId));
        sport.set_id(newId);
        sportRepository.deleteById(oldId);
        return sportRepository.save(sport);
    }

    public void deleteSport(String id) {
        sportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Sport not found with id " + id));
        sportRepository.deleteById(id);
    }

    public Sport getSportByName(String name) {
        return sportRepository.findByName(name);
    }
}
