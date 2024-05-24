package repository;

import domain.Sport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SportRepository extends MongoRepository<Sport, String> {
    List<Sport> findByName(String name);

}
