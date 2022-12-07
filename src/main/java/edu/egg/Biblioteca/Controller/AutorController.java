package edu.egg.Biblioteca.Controller;

import edu.egg.Biblioteca.Entidad.Autor;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Service.AutorServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AutorController {
    @Autowired
    private AutorServiceImp autorServiceImp;

    @GetMapping("/registrar")
    public String registrar() {
        return "autorForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) {
        try {
            autorServiceImp.createAutor(nombre);
            model.put("success", "El autor se guardo correctamente");
        } catch (MyExeptions e) {
            model.put("fail", "Hubo un problema y no pudo cargase el autor");
            return "autorForm.html";
        }
        return "index.html";
    }

    @GetMapping("/list")
    public String listOfAuthor(ModelMap model) {
        List<Autor> autores = autorServiceImp.fetchListAutor();
        model.addAttribute("autores", autores);
        return "autorList.html";
    }

    //url viaja el id
    @GetMapping("/update/{id}")
    public String update(@PathVariable String id, ModelMap model) {
        //model.put("id", autorServiceImp.findById(id));
        model.put("autor", autorServiceImp.getOne(id));
        return "autorUpdate.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable String id, String nombre, ModelMap model) {
        try {
            autorServiceImp.editAutor(id, nombre);
            //model.put("success","El autor se modifico exitosamente");
            return "redirect:../list";
        } catch (MyExeptions e) {
            model.put("fail", e.getMessage());
            return "autorUpdate.html";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, ModelMap model) {
        model.put("autor", autorServiceImp.getOne(id));
        return "autorDelete.html";
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable String id, ModelMap model) {
        try {
            System.out.println("entro");
            autorServiceImp.delete(id);
            return "redirect:../list";
        } catch (MyExeptions e) {
            model.put("fail", e.getMessage());
            return "autorDelete.html";
        }
    }
}
