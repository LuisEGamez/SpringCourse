package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.controllers;


import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.dto.BranchDTO;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping("/create")
    public ModelAndView createBranch(){

        ModelAndView modelAndView = new ModelAndView();
        BranchDTO branchDTO = new BranchDTO();
        modelAndView.setViewName("/branch/formCreate");
        modelAndView.addObject("title", "Nueva sucursal");
        modelAndView.addObject("branchDTO", branchDTO);


        return modelAndView;
    }

    @PostMapping("/add") // Recibimos datos de la vista de ahí el método POST
    public ModelAndView saveBranch(@Valid @ModelAttribute BranchDTO branchDTO, BindingResult result,
                                   RedirectAttributes attributes){ // Catch error from form with BindingResult and @Valid
        ModelAndView modelAndView = new ModelAndView();

        if(result.hasErrors()){
            modelAndView.setViewName("/branch/formCreate");
            modelAndView.addObject("title", "Nueva sucursal");
            modelAndView.addObject("branchDTO", branchDTO);

        }else {
            modelAndView.setViewName("redirect:/branch/getAll");
            branchService.saveBranch(branchDTO);
            attributes.addFlashAttribute("success", "Cliente guardado con éxito");
        }

        return modelAndView;
    }



    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id, RedirectAttributes attributes){

        ModelAndView modelAndView = new ModelAndView();
        BranchDTO branchDTO;

        if(id > 0){

            branchDTO = branchService.getOne(id);

            if(branchDTO == null){
                modelAndView.setViewName("redirect:/branch/getAll");
                attributes.addFlashAttribute("error", "El ID de la sucursal no existe");

            }else {
                modelAndView.setViewName("/branch/formUpdate");
                modelAndView.addObject("title", "Editar sucursal");
                modelAndView.addObject("branchDTO", branchDTO);
            }

        }else {
            modelAndView.setViewName("redirect:/branch/getAll");
            attributes.addFlashAttribute("error", "ID error");
        }

        return modelAndView;
    }

    @PostMapping("/update") // Recibimos datos de la vista de ahí el método POST
    public ModelAndView updateBranch(@Valid @ModelAttribute BranchDTO branchDTO, BindingResult result,
                                     RedirectAttributes attributes){
        ModelAndView modelAndView = new ModelAndView();

        if(result.hasErrors()){
            modelAndView.setViewName("/branch/formUpdate");
            modelAndView.addObject("title", "Editar sucursal");
            modelAndView.addObject("branchDTO", branchDTO);
        }else {
            modelAndView.setViewName("redirect:/branch/getAll");
            branchService.updateBranch(branchDTO);
            attributes.addFlashAttribute("success", "Cliente actualizado con éxito");

        }

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteBranch(@PathVariable("id") Integer id, RedirectAttributes attributes){
        ModelAndView modelAndView = new ModelAndView();
        BranchDTO branchDTO;

        if(id > 0){

            branchDTO = branchService.getOne(id);

            if(branchDTO == null){
                modelAndView.setViewName("redirect:/branch/getAll");
                attributes.addFlashAttribute("error", "El ID no existe");

            }else {
                branchService.deleteBranch(id);
                modelAndView.setViewName("redirect:/branch/getAll");
                attributes.addFlashAttribute("warning", "Cliente eliminado con éxito");

            }

        }else {
            modelAndView.setViewName("redirect:/branch/getAll");
            attributes.addFlashAttribute("error", "ID error");

        }

        return modelAndView;
    }

    @GetMapping("/getOne/{id}")
    public ModelAndView getOne(@PathVariable("id") Integer id, RedirectAttributes attributes){
        ModelAndView modelAndView = new ModelAndView();
        BranchDTO branchDTO;

        if(id > 0){

            branchDTO = branchService.getOne(id);

            if(branchDTO == null){
                modelAndView.setViewName("redirect:/branch/getAll");
                attributes.addFlashAttribute("error", "El ID no existe");

            }else {
                modelAndView.setViewName("/branch/infoBranch");
                modelAndView.addObject("title", "Información sucursal");
                modelAndView.addObject("branchDTO", branchDTO);

            }

        }else {
            modelAndView.setViewName("redirect:/branch/getAll");
            attributes.addFlashAttribute("error", "ID error");

        }

        return modelAndView;
    }

    @GetMapping("/getAll") // Usando la clase ModelAndView
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