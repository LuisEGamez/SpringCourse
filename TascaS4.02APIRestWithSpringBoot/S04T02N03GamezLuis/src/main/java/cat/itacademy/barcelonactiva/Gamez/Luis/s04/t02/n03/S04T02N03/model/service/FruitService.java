package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n03.S04T02N03.model.service;

import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n03.S04T02N03.model.domain.Fruit;

import java.util.List;

public interface FruitService {

    Fruit createFruit(Fruit fruit);

    Fruit updateFruit(String name, Fruit fruit);

    void deleteFruit(String id);

    Fruit getOne(String id);

    List<Fruit> getAll();


}
