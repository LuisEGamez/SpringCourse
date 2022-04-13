package cat.itacademy.barcelonactiva.cognoms.nom.s04.t02.n01.S04T02N01GamezLuis.model.repository;

import cat.itacademy.barcelonactiva.cognoms.nom.s04.t02.n01.S04T02N01GamezLuis.model.domain.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    Fruit findByName(String name); // returns a fruit which a specific name
}
