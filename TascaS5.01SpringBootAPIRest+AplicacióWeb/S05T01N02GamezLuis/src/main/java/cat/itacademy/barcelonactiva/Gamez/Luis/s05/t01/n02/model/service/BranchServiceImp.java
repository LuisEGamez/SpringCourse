package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.service;

import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.domain.FlowerEntity;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.dto.FlowerDTO;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImp implements FlowerService{

    @Autowired
    private FlowerRepository flowerRepository;

    @Override
    public FlowerDTO saveFlower(FlowerDTO flowerDTO) {

        FlowerEntity flower = flowerRepository.save(new FlowerEntity(flowerDTO.getFlowerName(), flowerDTO.getFlowerCountry()));

        return new FlowerDTO(flower.getPk_FlowerID(), flower.getFlowerName(), flower.getFlowerCountry());
    }

    @Override
    public FlowerDTO updateFlower(String flowerName, FlowerDTO flowerDTO) {

        FlowerDTO flowerDTO1 = null;

        FlowerEntity flower = flowerRepository.findByFlowerName(flowerName);

        if(flower!= null){
            flower.setFlowerName(flowerDTO.getFlowerName());
            flower.setFlowerCountry(flowerDTO.getFlowerCountry());
            flower = flowerRepository.save(flower);
            flowerDTO1 = new FlowerDTO(flower.getPk_FlowerID(), flower.getFlowerName(), flower.getFlowerCountry());
        }

        return flowerDTO1;
    }


    @Override
    public void deleteFlower(Integer id) {

        flowerRepository.deleteById(id);

    }

    @Override
    public FlowerDTO getOne(Integer id) {

        FlowerDTO flowerDTO = null;
        FlowerEntity flower;
        Optional<FlowerEntity> branchData = flowerRepository.findById(id);

        if(branchData.isPresent()){

            flower = branchData.get();
            flowerDTO = new FlowerDTO(flower.getPk_FlowerID(), flower.getFlowerName(), flower.getFlowerCountry());

        }

        return flowerDTO;
    }

    @Override
    public List<FlowerDTO> getAll() {

        List<FlowerEntity> flowerEntities = new ArrayList<>();
        List<FlowerDTO> flowerDTOs = new ArrayList<>();

        flowerRepository.findAll().forEach(flowerEntity -> flowerEntities.add(flowerEntity));

            for (FlowerEntity flower : flowerEntities){

                flowerDTOs.add(new FlowerDTO(flower.getPk_FlowerID(), flower.getFlowerName(), flower.getFlowerCountry()));

            }

        return flowerDTOs;
    }
}
