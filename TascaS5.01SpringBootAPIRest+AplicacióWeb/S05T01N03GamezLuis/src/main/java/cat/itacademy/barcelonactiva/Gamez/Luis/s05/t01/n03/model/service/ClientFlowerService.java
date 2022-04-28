package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.service;

import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.dto.ClientFlowerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClientFlowerService {

    // Creating a WebClient Instance
    WebClient client = WebClient.create("http://localhost:9001/flower");

    public Mono<ResponseEntity<ClientFlowerDTO>> saveClientFlower(ClientFlowerDTO clientFlowerDTO){
        // Preparing a Request
            // Define the Method
            WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.post();

            // Define the URL
            WebClient.RequestBodySpec bodySpec = uriSpec.uri("/add");

            // Define the body
            WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(clientFlowerDTO);

            // Getting a Response
            Mono<ResponseEntity<ClientFlowerDTO>> response = headersSpec.retrieve().toEntity(ClientFlowerDTO.class);

            return response;
    }

    public Mono<ClientFlowerDTO> saveClientFlower1(ClientFlowerDTO clientFlowerDTO){
        // Preparing a Request
        // Define the Method
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.post();

        // Define the URL
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/add");

        // Define the body
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(clientFlowerDTO);

        // Getting a Response
        Mono<ClientFlowerDTO> response = headersSpec.exchangeToMono(clientResponse -> {

            if (clientResponse.statusCode().equals(HttpStatus.CREATED)){

                return clientResponse.bodyToMono(ClientFlowerDTO.class);

            }else {
                System.out.println("Error");
                return clientResponse.createException().flatMap(Mono :: error);

            }

        });

        return response;
    }




}
