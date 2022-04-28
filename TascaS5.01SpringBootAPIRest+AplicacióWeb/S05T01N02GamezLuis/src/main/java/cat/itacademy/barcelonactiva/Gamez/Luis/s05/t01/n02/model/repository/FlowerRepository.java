package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.repository;

import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.domain.FlowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerRepository extends JpaRepository<FlowerEntity, Integer> {

    FlowerEntity findByFlowerName(String flowerName);

}
