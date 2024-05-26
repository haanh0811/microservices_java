package fr.dauphine.miageif.Sport.service.impl;

import fr.dauphine.miageif.Sport.entity.Sport;
import fr.dauphine.miageif.Sport.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.dauphine.miageif.Sport.repository.SportRepository;
import org.springframework.web.client.RestTemplate;

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
        Sport s = sportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Sport not found with id " + id));
        sportRepository.deleteById(id);
        deleteSportInCalendrier(s.getName());
    }

    public Sport getSportByName(String name) {
        return sportRepository.findByName(name);
    }

    public Sport updateName(String id, String newName) {
        Sport sport = sportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Sport not found with id " + id));
        String oldName = sport.getName();
        sport.setName(newName);
        updateSportInCalendrier(oldName, newName);
        return sportRepository.save(sport);
    }

    private void updateSportInCalendrier(String oldName, String newName) {
        String url = "http://localhost:5154/evenement/sport/" + oldName + "/" + newName;
        try{
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(url, Object.class);
        }catch(Exception ex){
            System.out.println("Connexion impossible à l'url " + url);
        }
    }

    private void deleteSportInCalendrier(String name) {
        String url = "http://localhost:5154/evenement/sport/" + name;
        try{
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(url);
        }catch(Exception ex){
            System.out.println("Connexion impossible à l'url " + url);
        }
    }
}
