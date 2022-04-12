package cat.itacademy.barcelonactiva.cognoms.nom.s04.t02.n01.S04T02N01GamezLuis.controllers;

import cat.itacademy.barcelonactiva.cognoms.nom.s04.t02.n01.S04T02N01GamezLuis.model.domain.Fruit;
import cat.itacademy.barcelonactiva.cognoms.nom.s04.t02.n01.S04T02N01GamezLuis.model.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // annotation is used to define a controller and to indicate that the return value of the methods should be bound to the web response body.
@RequestMapping("/fruit")
public class FruitController {

    @Autowired // to inject TutorialRepository bean to local variable.
    private FruitRepository fruitRepository;

    @PostMapping("/add")
    public ResponseEntity<Fruit> createFruit(@RequestBody Fruit fruit){

        ResponseEntity<Fruit> rs;

        Fruit fruit1 = fruitRepository.save(new Fruit(fruit.getName(), fruit.getQuantityKg()));

        if (fruit1 == null){
            rs = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            rs = new ResponseEntity<>(fruit1, HttpStatus.CREATED);
        }
        return rs;
    }
}
