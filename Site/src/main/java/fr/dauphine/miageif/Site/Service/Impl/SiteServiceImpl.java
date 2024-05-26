package fr.dauphine.miageif.Site.Service.Impl;

import fr.dauphine.miageif.Site.Entity.Site;
import fr.dauphine.miageif.Site.Entity.Ville;
import fr.dauphine.miageif.Site.Repository.SiteRepository;
import fr.dauphine.miageif.Site.Repository.VilleRepository;
import fr.dauphine.miageif.Site.Service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    private static final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    private final SiteRepository repository;
    private final VilleRepository villeRepository;

    public SiteServiceImpl(SiteRepository siteRepo, VilleRepository villeRepo){
        this.repository = siteRepo;
        this.villeRepository = villeRepo;
    }

    @Override
    public Site createSite(Site site) {
        Site s = repository.findByName(site.getName());
        if (s != null){
            logger.info("Site {} already exists", site.getName());
            return null;
        }
        Ville v = villeRepository.findByName(site.getSeSitueDans().getName());
        if (v == null) {
            villeRepository.save(site.getSeSitueDans());
            logger.info("Ville {} created", site.getSeSitueDans().getName());
        }
        repository.save(site);
        logger.info("Site created : {}", site.getName());
        return site;
    }

    @Override
    public Site deleteSite(String name) {
        Site site = repository.findByName(name);
        if (site != null) {
            repository.deleteById(name);
            logger.info("Site {} deleted", name);
            deleteSiteInCalendrier(name);
            return site;
        }
        logger.info("No site {} found - nothing to delete", name);
        return null;
    }

    @Override
    public Site updateSite(String oldName, Site updatedSite) {
        Site site = repository.findByName(oldName);
        Ville ville = villeRepository.findByName(updatedSite.getName());
        if (site == null){
            logger.info("No site {} found - nothing to update", oldName);
            return null;
        }
        if (!updatedSite.getName().equals(oldName))
            updateSiteInCalendrier(oldName, updatedSite.getName());

        repository.deleteById(oldName);
        if (ville == null) {
            villeRepository.save(updatedSite.getSeSitueDans());
            logger.info("Ville {} created", updatedSite.getSeSitueDans().getName());
        }
        repository.save(updatedSite);
        logger.info("Site {} updated", updatedSite.getName());

        return updatedSite;
    }

    @Override
    public List<Site> getSiteByVille(String cityName) {
        Ville v = villeRepository.findByName(cityName);
        if (v == null) {
            logger.info("No city found with name {} - no site returned", cityName);
            return null;
        }
        List<Site> ret = new ArrayList<>();
        List<Site> siteList = repository.findAll();
        for(Site s : siteList){
            if(s.getSeSitueDans().equals(v)){
                logger.info("Found site {} returned in {}", s.getName(), cityName);
                ret.add(s);
            }
        }
        return ret;
    }

    @Override
    public List<Site> getAllSites() {
        return repository.findAll();
    }

    @Override
    public Site getSiteByName(String siteName) {
        return repository.findByName(siteName);
    }


    private void updateSiteInCalendrier(String oldName, String newName) {
        String url = "http://localhost:5154/evenement/site/" + oldName + "/" + newName;
        try{
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(url, Object.class);
        }catch(Exception ex){
            logger.warn("Connection impossible with {}", url);
        }
    }

    private void deleteSiteInCalendrier(String name) {
        String url = "http://localhost:5154/evenement/site/" + name;
        try{
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(url);
        }catch(Exception ex){
            logger.warn("Connection impossible with {}", url);
        }
    }
}
