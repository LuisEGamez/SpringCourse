package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.controllers;

import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.dto.ClientFlowerDTO;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.service.ClientFlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("flower")
public class ClientFlowerController {

    @Autowired
    ClientFlowerService clientFlowerService;

    @RequestMapping("/clientFlowersAdd")
    public Mono<ResponseEntity<ClientFlowerDTO>> saveClientFlower(@RequestBody ClientFlowerDTO clientFlowerDTO){

        return clientFlowerService.saveClientFlower(clientFlowerDTO);
    }

    @RequestMapping("/clientFlowersAdd1")
    public Mono<ClientFlowerDTO> saveClientFlower1(@RequestBody ClientFlowerDTO clientFlowerDTO){

        return clientFlowerService.saveClientFlower1(clientFlowerDTO);
    }

}
