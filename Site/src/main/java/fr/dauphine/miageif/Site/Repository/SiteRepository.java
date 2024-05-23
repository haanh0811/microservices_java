package fr.dauphine.miageif.Site.Repository;

import fr.dauphine.miageif.Site.Entity.Site;
import fr.dauphine.miageif.Site.Entity.Ville;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface SiteRepository extends Neo4jRepository<Site, String> {
    Site findByName(String name);

    List<Site> findAll();

    List<Site> findBySeSitueDans(Ville v);

    void deleteById(String name);
}
