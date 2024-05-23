package fr.dauphine.miageif.Site.Repository;

import fr.dauphine.miageif.Site.Entity.Site;
import fr.dauphine.miageif.Site.Entity.Ville;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface VilleRepository extends Neo4jRepository<Ville, String> {
    Ville findByName(String name);

    List<Ville> findAll();
}
