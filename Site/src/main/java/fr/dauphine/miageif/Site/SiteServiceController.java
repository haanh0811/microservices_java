package fr.dauphine.miageif.Site;

import fr.dauphine.miageif.Site.Entity.Site;
import fr.dauphine.miageif.Site.Entity.Ville;
import fr.dauphine.miageif.Site.Repository.SiteRepository;
import fr.dauphine.miageif.Site.Repository.VilleRepository;
import fr.dauphine.miageif.Site.Service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class SiteServiceController {

    private static final Logger logger = LoggerFactory.getLogger(SiteServiceController.class);

    private final SiteService service;

    public SiteServiceController(SiteService service) {
        this.service = service;
    }


    @PostMapping("/site")
    public ResponseEntity<?> site (@RequestBody Site site){
        logger.info("Received request to create site: {}", site.getName());
        Site s =  service.createSite(site);
        if (s==null){
            return status(HttpStatus.BAD_REQUEST).body("Site with name " + site.getName() + " already exists");
        }
        return ok(site);
    }

    @DeleteMapping("/site/{name}")
    public ResponseEntity<?> deleteSite(@PathVariable String name) {
        logger.info("Received request to delete site: {}", name);
        Site site = service.deleteSite(name);
        if (site == null){
            return status(HttpStatus.NOT_FOUND).body("No site found for name " + name);
        }
        return ok(site);
    }

    @PutMapping("/site/{oldName}")
    public ResponseEntity<?> updateSite(@PathVariable String oldName, @RequestBody Site updatedSite) {
        logger.info("Received request to update site: {} with name {} and city {}", oldName, updatedSite.getName(), updatedSite.getSeSitueDans().getName());
        Site site = service.updateSite(oldName, updatedSite);
        if (site == null){
            return status(HttpStatus.NOT_FOUND).body("No site found for name " + oldName);
        }
        return ok(site);
    }

    @GetMapping("site/{siteName}")
    public ResponseEntity<?> getSiteByName(@PathVariable String siteName){
        logger.info("Received request to get site with name {} ", siteName);
        Site site = service.getSiteByName(siteName);
        if (site == null){
            logger.info("No site found with name {} ", siteName);
            return status(HttpStatus.NOT_FOUND).body("No site found for name " + siteName);
        }
        logger.info("Found site with name {} ", siteName);
        return ok(site);
    }

    @GetMapping("site/ville/{cityName}")
    public ResponseEntity<?> getSitesByVille(@PathVariable String cityName){
        logger.info("Received request to get site with city {}", cityName);
        List<Site> sites = service.getSiteByVille(cityName);
        if (sites.isEmpty()){
            logger.info("No site found in {}", cityName);
            return status(HttpStatus.NOT_FOUND).body("No site found for city " + cityName);
        }
        return ok(sites);
    }

    @GetMapping("site")
    public List<Site> getSite(){
        logger.info("Received request to get all sites");
        return service.getAllSites();
    }
}
