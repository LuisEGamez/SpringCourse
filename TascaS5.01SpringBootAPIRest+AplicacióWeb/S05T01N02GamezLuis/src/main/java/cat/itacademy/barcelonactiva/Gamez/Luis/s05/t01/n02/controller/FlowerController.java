package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.controller;


import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.dto.FlowerDTO;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    The Swagger UI page will then be available at http://localhost:9001/swagger-ui.html
    OpenAPI description will be available at the following url for json format: http://localhost:9001/v3/api-docs
*/

@RestController
@RequestMapping("/flower")
public class FlowerController {


    @Autowired
    private FlowerService flowerService;

    @PostMapping("/add")
    public ResponseEntity<FlowerDTO> saveFlower(@RequestBody FlowerDTO flowerDTO){

        ResponseEntity<FlowerDTO> rs;

        FlowerDTO flowerDTO1 = flowerService.saveFlower(flowerDTO);

        if (flowerDTO1 == null){
            rs = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            rs = new ResponseEntity<>(flowerDTO1, HttpStatus.CREATED);
        }
        return rs;
    }

    @PutMapping("/update")
    public ResponseEntity<FlowerDTO> updateFlower(@RequestParam String flowerName, @RequestBody FlowerDTO flowerDTO){

        ResponseEntity<FlowerDTO> rs;
        FlowerDTO flowerDTO1 = flowerService.updateFlower(flowerName, flowerDTO);
        if(flowerDTO1 != null){
            try {
                rs = new ResponseEntity<>(flowerDTO1, HttpStatus.OK);
            }catch (Exception e){
                rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else {
            rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return rs;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFlower(@PathVariable("id") Integer id){

        ResponseEntity<HttpStatus> rs;

        try {

            flowerService.deleteFlower(id);
            rs = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e){

            rs = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        return rs;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FlowerDTO>> getAll(){

        ResponseEntity<List<FlowerDTO>> rs;
        List<FlowerDTO> flowerDTOs;

        try {
            flowerDTOs = flowerService.getAll();
            if(flowerDTOs.isEmpty()){
                rs = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                rs = new ResponseEntity<>(flowerDTOs, HttpStatus.OK);
            }
        }catch (Exception e){
            rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return rs;
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<FlowerDTO> getOne(@PathVariable("id") Integer id){

        ResponseEntity<FlowerDTO> rs;
        FlowerDTO flowerDTO;

        try {
            flowerDTO = flowerService.getOne(id);
            if(flowerDTO == null){
                rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }else{
                rs = new ResponseEntity<>(flowerDTO, HttpStatus.OK);
            }
        }catch (Exception e){

            rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return rs;
    }

}
