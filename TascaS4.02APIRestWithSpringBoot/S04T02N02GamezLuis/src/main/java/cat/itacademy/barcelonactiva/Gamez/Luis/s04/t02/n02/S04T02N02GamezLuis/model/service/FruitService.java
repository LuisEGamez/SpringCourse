package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n02.S04T02N02GamezLuis.model.service;

import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n02.S04T02N02GamezLuis.model.domain.Fruit;

import java.util.List;


public interface FruitService {

    Fruit createFruit(Fruit fruit);

    Fruit updateFruit(String name, Fruit fruit);

    void deleteFruit(Long id);

    Fruit getOne(Long id);

    List<Fruit> getAll();


}
