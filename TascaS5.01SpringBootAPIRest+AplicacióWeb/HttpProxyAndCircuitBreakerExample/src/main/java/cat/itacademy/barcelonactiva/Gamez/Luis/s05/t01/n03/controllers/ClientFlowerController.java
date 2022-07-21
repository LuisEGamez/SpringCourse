package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.controllers;

import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.dto.ClientFlowerDTO;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.service.ClientFlowerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/*

    The Swagger UI page will then be available at http://localhost:9002/swagger-ui.html
    OpenAPI description will be available at the following url for json format: http://localhost:9002/v3/api-docs

 */

@RestController
@RequestMapping("flower")
public class ClientFlowerController {

    @Autowired
    ClientFlowerService clientFlowerService;

    @PostMapping("/clientFlowersAdd")
    public Mono<ResponseEntity<ClientFlowerDTO>> saveClientFlower(@RequestBody ClientFlowerDTO clientFlowerDTO){

        return clientFlowerService.saveClientFlower(clientFlowerDTO);
    }
    @PutMapping("/clientFlowersUpdate")
    public Mono<ResponseEntity<ClientFlowerDTO>> updateClientFlower(@RequestParam String clientFlowerName,
                                                                    @RequestBody ClientFlowerDTO clientFlowerDTO){

        return clientFlowerService.updateClientFlower(clientFlowerName, clientFlowerDTO);
    }

    @DeleteMapping("/clientFlowersDelete/{id}")
    public Mono<ResponseEntity<HttpStatus>> deleteClientFlower(@PathVariable("id") Integer id){

        return clientFlowerService.deleteClientFlower(id);

    }


    @GetMapping("clientFlowersGetOne/{id}")
    public Mono<ResponseEntity<ClientFlowerDTO>> getOneClienteFlower(@PathVariable("id") Integer id){

        return clientFlowerService.getOneClienteFlower(id);

    }

    @CircuitBreaker(name = "FLOWERS", fallbackMethod = "fallBack")
    @GetMapping("clientFlowersGetAll")
    public Mono<ResponseEntity<List<ClientFlowerDTO>>> getAllClienteFlower(){

        return clientFlowerService.getAllClienteFlower();

    }

    private Mono<ResponseEntity<List<ClientFlowerDTO>>> fallBack( Exception e) {
        List<ClientFlowerDTO> list = new ArrayList<>();
        return Mono.empty();
    }


}
