package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.controllers;


import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.dto.BranchDTO;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

//@RestController //Con esta no funciona pregunta el porqué
@Controller
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/add")
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO){

        ResponseEntity<BranchDTO> rs;

        BranchDTO branchDTO1 = branchService.createBranch(branchDTO);

        if (branchDTO1 == null){
            rs = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            rs = new ResponseEntity<>(branchDTO1, HttpStatus.CREATED);
        }
        return rs;
    }

    @GetMapping("/create")
    public ModelAndView createBranch1(){

        ModelAndView modelAndView = new ModelAndView();
        BranchDTO branchDTO = new BranchDTO();
        modelAndView.setViewName("/branch/formCreate");
        modelAndView.addObject("title", "Nueva sucursal");
        modelAndView.addObject("branch", branchDTO);


        return modelAndView;
    }

    @PostMapping("/add1") // Recibimos datos de la vista de ahí el método POST
    public ModelAndView saveBranch(@ModelAttribute BranchDTO branchDTO){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/branch/getAllV1");
        branchService.createBranch(branchDTO);
        return modelAndView;
    }

    @PutMapping("/update")
    public ResponseEntity<BranchDTO> updateBranch(@RequestParam String branchName, @RequestBody BranchDTO branchDTO){

        ResponseEntity<BranchDTO> rs;
        BranchDTO branchDTO1 = branchService.updateBranch(branchName, branchDTO);
        if(branchDTO1 != null){
            try {
                rs = new ResponseEntity<>(branchDTO1, HttpStatus.OK);
            }catch (Exception e){
                rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else {
            rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return rs;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id){

        ModelAndView modelAndView = new ModelAndView();
        BranchDTO branchDTO = branchService.getOne(id);
        modelAndView.setViewName("/branch/formUpdate");
        modelAndView.addObject("title", "Editar sucursal");
        modelAndView.addObject("branch", branchDTO);


        return modelAndView;
    }

    @PostMapping("/update") // Recibimos datos de la vista de ahí el método POST
    public ModelAndView updateBranch(@ModelAttribute BranchDTO branchDTO){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/branch/getAllV1");
        branchService.updateBranch(branchDTO);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteBranch(@PathVariable("id") Integer id){

        ModelAndView modelAndView = new ModelAndView();
        branchService.deleteBranch(id);
        modelAndView.setViewName("redirect:/branch/getAllV1");
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}") // Don't work with MVC
    public ResponseEntity<HttpStatus> deleteBranch1(@PathVariable("id") Integer id){

        ResponseEntity<HttpStatus> rs;

        try {

            branchService.deleteBranch(id);
            rs = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e){

            rs = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        return rs;

    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<BranchDTO> getOne(@PathVariable("id") Integer id){

        ResponseEntity<BranchDTO> rs;
        BranchDTO branchDTO;

        try {
            branchDTO = branchService.getOne(id);
            if(branchDTO == null){
                rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }else{
                rs = new ResponseEntity<>(branchDTO, HttpStatus.OK);
            }
        }catch (Exception e){

            rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return rs;
    }

    @GetMapping("/getAll") // Usando ResponseEntity para PostMan
    public ResponseEntity<List<BranchDTO>> getAll(){

        ResponseEntity<List<BranchDTO>> rs;
        List<BranchDTO> branchDTOs;

        try {
            branchDTOs = branchService.getAll();
            if(branchDTOs.isEmpty()){
                rs = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                rs = new ResponseEntity<>(branchDTOs, HttpStatus.OK);
            }
        }catch (Exception e){
            rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return rs;
    }


    @GetMapping("/getAllV1") // Usando la interface model
    public String getAllV1(Model model){

        List<BranchDTO> branchDTOs = branchService.getAll();
        model.addAttribute("title", "List of branches"); // Añadimos el nombre del atributo y el valor para mostrarlo en la vista
        model.addAttribute("listOfBranches", branchDTOs);

        return "/branch/list";
    }

    @GetMapping("/getAllV2") // Usando la clase ModelAndView
    public ModelAndView getAllVX(){

        ModelAndView modelAndView = new ModelAndView();
        List<BranchDTO> branchDTOs = branchService.getAll();
        modelAndView.setViewName("/branch/list");
        modelAndView.addObject("title", "List of branches");
        modelAndView.addObject("listOfBranches", branchDTOs);

        return modelAndView;
    }

    @GetMapping("/home")
    public String home(){

        return "home";
    }

}
