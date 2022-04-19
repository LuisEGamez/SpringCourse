package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n01.S04T02N01GamezLuis.controllers;

import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n01.S04T02N01GamezLuis.model.domain.Fruit;
import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n01.S04T02N01GamezLuis.model.repository.FruitRepository;
import cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n01.S04T02N01GamezLuis.model.service.FruitServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// Open H2 console with url: http://localhost:8080/h2-ui

@RestController // annotation is used to define a controller and to indicate that the return value of the methods should be bound to the web response body.
@RequestMapping("/fruit")
public class FruitController {

    @Autowired // to inject TutorialRepository bean to local variable.
    private FruitServiceImp fruitServiceImp;

    @Autowired
    private FruitRepository fruitRepository;

    @PostMapping("/add")
    public ResponseEntity<Fruit> createFruit(@RequestBody Fruit fruit){

        ResponseEntity<Fruit> rs;

        Fruit fruit1 = fruitServiceImp.createFruit(fruit);

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
        Fruit fruit1 = fruitServiceImp.updateFruit(name, fruit);
        if(fruit1 != null){
            try {
                rs = new ResponseEntity<>(fruit1, HttpStatus.OK);
            }catch (Exception e){
                rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else {
            rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return rs;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFruit(@PathVariable("id") Long id){

        ResponseEntity<HttpStatus> rs;

        try {
            fruitServiceImp.deleteFruit(id);
            rs = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            rs = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return rs;
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Fruit> getAFruit(@PathVariable("id") Long id){
        ResponseEntity<Fruit> rs;
        Fruit fruit;
        try {
            fruit = fruitServiceImp.getOne(id);
            if(fruit != null){
                rs = new ResponseEntity<>(fruit, HttpStatus.OK);
            }else {
                rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return rs;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Fruit>> getAllFruits(){
        ResponseEntity<List<Fruit>> rs;
        List<Fruit> fruits = new ArrayList<>();
        try{
            fruits = fruitServiceImp.getAll();
            if(fruits.isEmpty()){
                rs = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                rs = new ResponseEntity<>(fruits, HttpStatus.OK);
            }
        }catch (Exception e){
            rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return rs;
    }
}
