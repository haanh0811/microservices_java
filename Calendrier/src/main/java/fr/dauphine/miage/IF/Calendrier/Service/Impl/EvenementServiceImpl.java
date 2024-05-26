package fr.dauphine.miage.IF.Calendrier.Service.Impl;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;
import fr.dauphine.miage.IF.Calendrier.Model.SiteModel;
import fr.dauphine.miage.IF.Calendrier.Model.SportModel;
import fr.dauphine.miage.IF.Calendrier.Repository.EvenementRepository;
import fr.dauphine.miage.IF.Calendrier.Service.EvenementService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EvenementServiceImpl implements EvenementService {

    private final EvenementRepository repository;

    public EvenementServiceImpl(EvenementRepository repository){
        this.repository = repository;
    }

    @Override
    public Evenement createEvenement(Evenement e) {
        checkSiteAndSportExistence(e);
        e.setId(UUID.randomUUID());
        repository.save(e);
        return e;
    }

    @Override
    public Evenement getById(UUID id) {
        Optional<Evenement> optionalEvenement = repository.findById(id);
        return optionalEvenement.orElse(null);
    }

    @Override
    public List<Evenement> getBySport(String name) {
        return repository.findBySport(name);
    }

    @Override
    public List<Evenement> getBySite(String name) {
        return repository.findBySite(name);
    }

    @Override
    public List<Evenement> getByDate(int annee, int mois, int jour) {
        LocalDate jourJ = LocalDate.of(annee, mois, jour);
        return repository.findByDate(jourJ);
    }

    @Override
    public List<Evenement> getByDate(LocalDate dateDebut, LocalDate dateFin) {
        return repository.findByDateBetween(dateDebut, dateFin);
    }

    @Override
    public Evenement save(Evenement oldEvenement, Evenement newEvenement) {
        oldEvenement.setDate(newEvenement.getDate());
        oldEvenement.setSite(newEvenement.getSite());
        oldEvenement.setSport(newEvenement.getSport());
        oldEvenement.setHeure(newEvenement.getHeure());
        checkSiteAndSportExistence(newEvenement);
        repository.save(oldEvenement);
        return oldEvenement;
    }

    @Override
    public List<Evenement> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void updateSportName(Evenement evenement, String newSport) {
        evenement.setSport(newSport);
        repository.save(evenement);
    }

    @Override
    public void updateSiteName(Evenement evenement, String newSite) {
        evenement.setSite(newSite);
        repository.save(evenement);
    }

    private static void checkSiteAndSportExistence(Evenement e) {
        String url1 = "http://localhost:8000/site/" + e.getSite();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SiteModel> response = restTemplate.getForEntity(url1, SiteModel.class);

        if (!response.getStatusCode().isError()){
            if (response.getBody() == null){
                System.out.println(url1);
                throw new IllegalArgumentException("this site doesn't exist");
            }
        }
        else{
            System.out.println("Connexion impossible à" + url1);
        }

        String url2 = "http://localhost:8080/api/sports/" + e.getSport();
        ResponseEntity<SportModel> response2 = restTemplate.getForEntity(url2, SportModel.class);

        if (!response2.getStatusCode().isError()){
            if (response2.getBody() == null){
                throw new IllegalArgumentException("this sport doesn't exist");
            }
        }
        else{
            System.out.println("Connexion impossible à" + url1);
        }
    }
}
