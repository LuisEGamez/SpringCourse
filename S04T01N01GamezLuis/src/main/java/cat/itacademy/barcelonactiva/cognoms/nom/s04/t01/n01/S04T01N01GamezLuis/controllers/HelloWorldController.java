package cat.itacademy.barcelonactiva.cognoms.nom.s04.t01.n01.S04T01N01GamezLuis.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // Indicamos que esta clase será un controlador
public class HelloWorldController {

    @RequestMapping(value = "HelloWorld", method = RequestMethod.GET) //Indicamos la URL que debe devolver este contenido
    public String saluda(@RequestParam(defaultValue = "UNKNOWN") String name){

        return "Hola " + name + ". Estas ejecutando un proyecto Maven";
    }

    @RequestMapping(value = {"/HelloWorld2" , "/HelloWorld2/{name}"}, method = RequestMethod.GET) //Indicamos las URL que debe devolver este contenido
    public String saluda2(@PathVariable Optional<String> name){ // @PathVariable indica el parámetro que variará

        String result;

        if (name.isPresent()){
            result = "Hola " + name.get() + ". Estas ejecutando un proyecto Maven";
        }else {
            result = "Hola. Estas ejecutando un proyecto Maven";
        }

        return result;
    }


}
