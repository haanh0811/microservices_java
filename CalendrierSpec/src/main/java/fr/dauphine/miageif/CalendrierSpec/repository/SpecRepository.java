package fr.dauphine.miageif.CalendrierSpec.repository;

import fr.dauphine.miageif.CalendrierSpec.entity.Spec;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpecRepository extends MongoRepository<Spec,String> {

}
