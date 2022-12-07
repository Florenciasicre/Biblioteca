package edu.egg.Biblioteca.Controller;

import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Service.ClienteImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteImp clienteImp;

    @GetMapping("/registrar")
    public String clienteForm(){
        return"clienteForm.html";
    }

    @PostMapping("/registrado")
    public String cliente(@RequestParam Long documento, String nombre, String apellido, String telefono, boolean alta, ModelMap model){
        try {
            clienteImp.create(documento,nombre,apellido,telefono,alta);
            model.put("success", "El cliente se guardo correctamente");
        } catch (MyExeptions e) {
            model.put("fail",e.getMessage());
            return"clienteForm.html";
        }
        return "index.html";
    }
    @GetMapping("/list")
    public String list(ModelMap model){
        model.put("list",clienteImp.listCliente());
        return "clienteList.html";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable String id, ModelMap model){
        model.put("cliente",clienteImp.getOne(id));
        return "clienteUpdate.html";
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable String id, Long documento,String nombre, String apellido, String telefono, ModelMap model){
        try {
            clienteImp.editCliente(id,documento,nombre,apellido,telefono);
            return "redirect:../list";
        } catch (MyExeptions e) {
           model.put("fail",e.getMessage());
           return "clienteUpdate.html";
        }
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, ModelMap model){
        model.put("cliente", clienteImp.getOne(id));
        return "clienteDelete.html";
    }

    @PostMapping("/delete/{id}")
    public String deleteC(@PathVariable String id, ModelMap model){
        try {
            clienteImp.delete(id);
            return "redirect:../list";
        } catch (MyExeptions e) {
            model.put("fail",e.getMessage());
            return "clienteDelete.html";
        }
    }

}
