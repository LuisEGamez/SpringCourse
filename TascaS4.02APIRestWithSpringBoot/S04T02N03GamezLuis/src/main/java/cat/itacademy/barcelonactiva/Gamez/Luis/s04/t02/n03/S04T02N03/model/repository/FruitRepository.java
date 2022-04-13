package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n03.S04T02N03.model.repository;


import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n03.S04T02N03.model.domain.Fruit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FruitRepository extends MongoRepository<Fruit, String> {

    Fruit findByName(String name); // returns a fruit which a specific name
}
