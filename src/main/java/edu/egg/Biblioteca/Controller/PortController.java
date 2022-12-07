package edu.egg.Biblioteca.Controller;

import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Service.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortController{
    @Autowired
    UsuarioServiceImp usuarioServiceImp;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/home")
    public String index(){
        return "index.html";
    }

    @GetMapping("/SingUp")
    public String singUp(){
        return "singUp.html";
    }

    @PostMapping("/SingUp")
    public String singUp(@RequestParam String nombre, String email, String password, String password2, ModelMap model){
        try {
            usuarioServiceImp.singUp(nombre,email,password,password2);
            model.put("success","El usuario se registró correctamente");
            return "logIn.html";
        } catch (MyExeptions e) {
            model.put("fail",e.getMessage());
            return "singUp.html";
        }
    }
    @GetMapping("/logIn")
    public String logIn(@RequestParam(required = false) String fail, ModelMap model){
        if(fail != null){
            model.put("fail","Hubo un problema al logiarse");
        }
        return "logIn.html";
    }


}
