package edu.egg.Biblioteca.Controller;

import edu.egg.Biblioteca.Entidad.Editorial;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Service.EditorialServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/editorial")
public class EditorialController {
    @Autowired
    private EditorialServiceImp editorialServiceImp;

    @GetMapping("/registrar")
    public String registrar(){
        return "editorialForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) {
        try {
            editorialServiceImp.createEditorial(nombre);
        }catch (MyExeptions e){
            model.put("fail","Hubo un problema y no pudo cargase la editorial");
            return "editorialForm.html";
        }
        model.put("success","La editorial se cargo correctamente");
        return "index.html";
    }

    @GetMapping("/list")
    public String list(ModelMap model){
        List<Editorial> editoriales = editorialServiceImp.fetchListEditoriales();
        model.put("lista",editoriales);
        return "EditorialList.html";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable String id, ModelMap model){
        model.put("editorial",editorialServiceImp.getOne(id));
        return "editorialUpdate.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable String id, String nombre, ModelMap model){
        try {
            editorialServiceImp.editEditorial(id,nombre);
            return "redirect:../list";
        } catch (MyExeptions e) {
            model.put("fail",e.getMessage());
            return "EditorialUpdate.html";
        }

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, ModelMap model){
        model.put("editorial",editorialServiceImp.getOne(id));
        return "EditorialDelete.html";
    }
    @PostMapping("delete/{id}")
    public String EditorialDelete(@PathVariable String id, ModelMap model){
        try {
            editorialServiceImp.delete(id);
            return "redirect:../list";
        } catch (MyExeptions e) {
            model.put("fail",e.getMessage());
            return "EditorialDelete.html";
        }
    }

}
