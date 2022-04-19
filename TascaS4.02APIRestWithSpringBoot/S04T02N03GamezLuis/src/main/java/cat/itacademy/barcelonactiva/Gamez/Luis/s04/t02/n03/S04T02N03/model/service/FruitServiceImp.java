package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n03.S04T02N03.model.service;

import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n03.S04T02N03.model.domain.Fruit;
import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n03.S04T02N03.model.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FruitServiceImp implements FruitService{


    @Autowired
    FruitRepository fruitRepository;

    @Override
    public Fruit createFruit(Fruit fruit) {

        return fruitRepository.save(new Fruit(fruit.getName(),fruit.getQuantityKg()));
    }

    @Override
    public Fruit updateFruit(String name, Fruit fruit) {

        Fruit fruit1 = fruitRepository.findByName(name);

        if(fruit1 != null) {
            fruit1.setName(fruit.getName());
            fruit1.setQuantityKg(fruit.getQuantityKg());
            fruit1 = fruitRepository.save(fruit1);
        }

        return fruit1;
    }

    @Override
    public void deleteFruit(String id) {

        fruitRepository.deleteById(id);

    }

    @Override
    public Fruit getOne(String id) {

        Fruit fruit = null;
        Optional<Fruit> fruitData = fruitRepository.findById(id);

        if(fruitData.isPresent()){

            fruit = fruitData.get();

        }

        return fruit;
    }

    @Override
    public List<Fruit> getAll() {
        List<Fruit> fruits = new ArrayList<>();

        fruitRepository.findAll().forEach(x -> fruits.add(x));

        return fruits;
    }
}
