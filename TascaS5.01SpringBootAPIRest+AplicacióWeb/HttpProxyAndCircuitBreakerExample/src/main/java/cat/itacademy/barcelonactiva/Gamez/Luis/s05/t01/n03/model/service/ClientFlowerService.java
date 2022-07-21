package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.service;

import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.dto.ClientFlowerDTO;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n03.model.proxy.HttpProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientFlowerService {

    // Creating a WebClient Instance
    WebClient client = WebClient.create("http://localhost:9001/flower");

    @Autowired
    HttpProxy httpProxy;

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




    public Mono<ResponseEntity<ClientFlowerDTO>> updateClientFlower(String clientFlowerName, ClientFlowerDTO clientFlowerDTO) {

        // Preparing a Request
            // Define the Method
            WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.put();

            // Define the URL
            WebClient.RequestBodySpec bodySpec = uriSpec.uri("/update?flowerName=" + clientFlowerName);

            // Define the body
            WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(clientFlowerDTO);

            // Getting a Response
            Mono<ResponseEntity<ClientFlowerDTO>> response = headersSpec.retrieve().toEntity(ClientFlowerDTO.class).onErrorResume(WebClientResponseException.class,
                    ex -> ex.getRawStatusCode() == 404 ? Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)) : Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)));

            return response;

    }


    public Mono<ResponseEntity<HttpStatus>> deleteClientFlower(Integer id) {

        // Preparing a Request
            // Define the Method
            WebClient.RequestHeadersUriSpec<?> uriSpec = client.delete();

            // Define the URL
            WebClient.RequestHeadersSpec<?> headersSpec =  uriSpec.uri("/delete/"+id);

            // Getting a Response
            Mono<ResponseEntity<HttpStatus>> response = headersSpec.retrieve().toEntity(HttpStatus.class).onErrorResume(WebClientResponseException.class,
                    ex -> ex.getRawStatusCode() == 404 ? Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)) : Mono.error(ex));


            return response;
    }


    public Mono<ResponseEntity<ClientFlowerDTO>> getOneClienteFlower(Integer id) {

        return client.get()
                .uri("/getOne/"+id)
                .retrieve().toEntity(ClientFlowerDTO.class).
                onErrorResume(WebClientResponseException.class,
                        ex -> ex.getRawStatusCode() == 404 ? Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)) : Mono.error(ex));
    }

    public Mono<ResponseEntity<List<ClientFlowerDTO>>> getAllClienteFlower() {

        return client.get()
                .uri("/getAll")
                .retrieve()
                .toEntityList(ClientFlowerDTO.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getRawStatusCode() == 500 ? Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)) : Mono.error(ex));
    }

    public Mono<ResponseEntity<List<ClientFlowerDTO>>> getAllClienteFlowerProxy() throws MalformedURLException {

        return httpProxy.getRequestData(new URL("http://localhost:9001/flower/getAll"), ClientFlowerDTO.class);
    }
}
