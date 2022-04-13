package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t01.n02.S04T01N02GamezLuis.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // Indicamos que esta clase será un controlador
public class HelloWorldController {

    @RequestMapping(value = "HelloWorld", method = RequestMethod.GET) //Indicamos la URL que debe devolver este contenido
    public String saluda(@RequestParam(defaultValue = "UNKNOWN") String name){

        return "Hola " + name + ". Estas ejecutando un proyecto Gradle";
    }

    @RequestMapping(value = {"HelloWorld2" , "HelloWorld2/{name}"}, method = RequestMethod.GET) //Indicamos las URL que debe devolver este contenido
    public String saluda2(@PathVariable Optional<String> name){ // @PathVariable indica el parámetro que variará

        String result;

        if (name.isPresent()){
            result = "Hola " + name.get() + ". Estas ejecutando un proyecto Gradle";
        }else {
            result = "Hola. Estas ejecutando un proyecto Gradle";
        }

        return result;
    }


}
