package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n02.S04T02N02GamezLuis.model.repository;


import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n02.S04T02N02GamezLuis.model.domain.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    Fruit findByName(String name); // returns a fruit which a specific name
}
