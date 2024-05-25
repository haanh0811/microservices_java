package fr.dauphine.miage.IF.Calendrier.Service.Impl;

import fr.dauphine.miage.IF.Calendrier.Entity.Evenement;
import fr.dauphine.miage.IF.Calendrier.Model.SiteModel;
import fr.dauphine.miage.IF.Calendrier.Model.SportModel;
import fr.dauphine.miage.IF.Calendrier.Repository.EvenementRepository;
import fr.dauphine.miage.IF.Calendrier.Service.EvenementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EvenementServiceImpl implements EvenementService {

    private final EvenementRepository repository;

    public EvenementServiceImpl(EvenementRepository repository){
        this.repository = repository;
    }

    @Override
    public Evenement createEvenement(Evenement e) {
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

        repository.save(e);
        return e;
    }

    @Override
    public Evenement getById(int id) {
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

}
