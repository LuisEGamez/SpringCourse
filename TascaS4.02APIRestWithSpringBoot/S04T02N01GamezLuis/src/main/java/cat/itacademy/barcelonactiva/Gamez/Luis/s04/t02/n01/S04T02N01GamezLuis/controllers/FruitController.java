package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n01.S04T02N01GamezLuis.controllers;

import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n01.S04T02N01GamezLuis.model.domain.Fruit;
import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n01.S04T02N01GamezLuis.model.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Open H2 console with url: http://localhost:8080/h2-ui

@RestController // annotation is used to define a controller and to indicate that the return value of the methods should be bound to the web response body.
@RequestMapping("/fruit")
public class FruitController {

    @Autowired // to inject TutorialRepository bean to local variable.
    private FruitRepository fruitRepository;

    @PostMapping("/add")
    public ResponseEntity<Fruit> createFruit(@RequestBody Fruit fruit){

        ResponseEntity<Fruit> rs;

        Fruit fruit1 = fruitRepository.save(new Fruit(fruit.getName(),
                                                        fruit.getQuantityKg()));

        if (fruit1 == null){
            rs = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            rs = new ResponseEntity<>(fruit1, HttpStatus.CREATED);
        }
        return rs;
    }

    @PutMapping("/update")
    public ResponseEntity<Fruit> updateFruit(@RequestParam String name, @RequestBody Fruit fruit){

        ResponseEntity<Fruit> rs;
        Fruit fruitData = fruitRepository.findByName(name);

        if(fruitData != null){
            fruitData.setName(fruit.getName());
            fruitData.setQuantityKg(fruit.getQuantityKg());
            rs = new ResponseEntity<>(fruitRepository.save(fruitData), HttpStatus.OK);

        }else {
            rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return rs;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFruit(@PathVariable("id") Long id){
        try {
            fruitRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Fruit> getAFruit(@PathVariable("id") Long id){
        ResponseEntity<Fruit> rs;
        Optional<Fruit> fruitData = fruitRepository.findById(id);
        if(fruitData.isPresent()){
            Fruit fruit = fruitData.get();
            rs = new ResponseEntity<>(fruit, HttpStatus.OK);
        }else {
            rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return rs;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Fruit>> getAllFruits(){
        ResponseEntity<List<Fruit>> result = null;
        List<Fruit> fruits = new ArrayList<>();
        try{
            fruitRepository.findAll().forEach(x -> fruits.add(x));
            if(fruits.isEmpty()){

                result = new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }else {

                result = new ResponseEntity<>(fruits, HttpStatus.OK);

            }
        }catch (Exception e){
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            return result;
        }
    }
}
