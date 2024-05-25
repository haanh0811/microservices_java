package fr.dauphine.miageif.Sport.repository;

import fr.dauphine.miageif.Sport.entity.Sport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SportRepository extends MongoRepository<Sport, String> {

    Sport findByName(String name);
}
