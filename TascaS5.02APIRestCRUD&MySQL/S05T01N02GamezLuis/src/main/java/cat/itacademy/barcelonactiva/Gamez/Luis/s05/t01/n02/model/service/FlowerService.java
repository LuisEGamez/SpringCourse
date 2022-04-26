package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.service;



import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.dto.FlowerDTO;

import java.util.List;


public interface FlowerService {

    FlowerDTO saveFlower(FlowerDTO flowerDTO);

    FlowerDTO updateFlower(String name, FlowerDTO flowerDTO);

    void deleteFlower(Integer id);

    FlowerDTO getOne(Integer id);

    List<FlowerDTO> getAll();


}
