package fr.dauphine.miageif.Site;

import fr.dauphine.miageif.Site.Entity.Site;
import fr.dauphine.miageif.Site.Entity.Ville;
import fr.dauphine.miageif.Site.Repository.SiteRepository;
import fr.dauphine.miageif.Site.Repository.VilleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SiteServiceController {

    private static final Logger logger = LoggerFactory.getLogger(SiteServiceController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private SiteRepository repository;
    @Autowired
    private VilleRepository villeRepository;


    @PostMapping("/site")
    public Site site (@RequestBody Site site){
        logger.info("Received request to create site: {}", site.getName());
        Ville v = villeRepository.findByName(site.getSeSitueDans().getName());
        if (v == null) {
            villeRepository.save(site.getSeSitueDans());
            logger.info("Ville {} created", site.getSeSitueDans().getName());
        }
        repository.save(site);
        logger.info("Site created : {}", site.getName());
        return site;
    }

    @DeleteMapping("/site/{name}")
    public Site deleteSite(@PathVariable String name) {
        logger.info("Received request to delete site: {}", name);
        Site site = repository.findByName(name);
        if (site != null) {
            repository.deleteById(name);
            logger.info("Site {} deleted", name);
            return site;
        }
        logger.info("No site {} found - nothing to delete", name);
        return null;
    }

    @PutMapping("/site/{oldName}")
    public Site updateSite(@PathVariable String oldName, @RequestBody Site updatedSite) {
        logger.info("Received request to update site: {} with name {} and city {}", oldName, updatedSite.getName(), updatedSite.getSeSitueDans().getName());
        Site site = repository.findByName(oldName);
        Ville ville = villeRepository.findByName(updatedSite.getName());
        if (site != null) {
            repository.deleteById(oldName);
            if (ville == null) {
                villeRepository.save(updatedSite.getSeSitueDans());
                logger.info("Ville {} created", updatedSite.getSeSitueDans().getName());
            }
            repository.save(updatedSite);
            logger.info("Site {} updated", updatedSite.getName());
            return updatedSite;
        }
        logger.info("No site {} found - nothing to update", oldName);
        return null;
    }

    @GetMapping("site/{siteName}")
    public Site getSiteByName(@PathVariable String siteName){
        logger.info("Received request to get site with name {} ", siteName);
        Site ret = repository.findByName(siteName);
        logger.info("Found site with name {} ", siteName);
        return ret;
    }

    @GetMapping("site/ville/{cityName}")
    public List<Site> getSitesByVille(@PathVariable String cityName){
        logger.info("Received request to get site with city {}", cityName);
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
        if (ret.isEmpty())
            logger.info("No site found in {}", cityName);
        return ret;
    }

    @GetMapping("site")
    public List<Site> getSite(){
        logger.info("Received request to get all sites");
        return repository.findAll();
    }
}
