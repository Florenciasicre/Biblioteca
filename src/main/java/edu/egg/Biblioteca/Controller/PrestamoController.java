package edu.egg.Biblioteca.Controller;

import edu.egg.Biblioteca.Entidad.Cliente;
import edu.egg.Biblioteca.Entidad.Libro;
import edu.egg.Biblioteca.Entidad.Prestamo;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Repository.PrestamosRepository;
import edu.egg.Biblioteca.Service.ClienteImp;
import edu.egg.Biblioteca.Service.LibroServiceImp;
import edu.egg.Biblioteca.Service.PrestamosImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/load")
public class PrestamoController {
    @Autowired
    private ClienteImp clienteImp;
    @Autowired
    private LibroServiceImp libroServiceImp;
    @Autowired
    private PrestamosImp prestamosImp;
    @Autowired
    private PrestamosRepository prestamosRepository;

    @GetMapping("/list")
    public String list(ModelMap model) {
        List<Prestamo> prestamo = prestamosImp.listPrestamo();
        model.put("prestamo", prestamo);
        return "prestamoList.html";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        List<Cliente> cliente = clienteImp.listCliente();
        List<Libro> libros = libroServiceImp.fetchListLibros();
        model.addAttribute("cliente", cliente);
        model.addAttribute("libro", libros);
        return "prestamoForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam boolean alta, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date devolucion, String isbn, String idCliente, ModelMap model) {
        try {
            prestamosImp.createPrestamo(alta, isbn, idCliente, devolucion);
            return "index.html";
        } catch (MyExeptions e) {
            model.put("fail", e.getMessage());
            return "prestamoForm.html";
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable String id, ModelMap model) {
        List<Cliente> cliente = clienteImp.listCliente();
        List<Libro> libros = libroServiceImp.fetchListLibros();
        model.addAttribute("cliente", cliente);
        model.addAttribute("libro", libros);
        model.put("prestamo", prestamosRepository.findById(id).get());
        return "prestamoUpdate.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable String id, @RequestParam boolean alta, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date devolucion, String isbn, String idCliente, ModelMap model) {
        try {
            prestamosImp.editPrestamo(id, alta, date, devolucion, idCliente, isbn);
            return "redirect:../list";
        } catch (MyExeptions e) {
            model.put("fail", e.getMessage());
            model.put("prestamo", prestamosImp.getOne(id));
            return "prestamoUpdate.html";
        }

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, ModelMap model){
        model.put("prestamo",prestamosImp.getOne(id));
        return "prestamoDelete.html";
    }
    @PostMapping("/delete/{id}")
    public String deletePrestamo(@PathVariable String id, ModelMap model){
        try {
            prestamosImp.delete(id);
            return "redirect:../list";
        } catch (MyExeptions e) {
           model.put("fail",e.getMessage());
           return "prestamoDelete.html";
        }
    }
    @GetMapping("/restore/{id}")
    public String restore(@PathVariable String id, ModelMap model){
        boolean alta = false;
        try {
            prestamosImp.updateRestore(id,alta);
            model.put("success","Se devolvió el libro correctamente");
        } catch (MyExeptions e) {
            model.put("fail",e.getMessage());
        }
        return "index.html";
    }
    @GetMapping("/search")
    public String list(@RequestParam String nombre, ModelMap model) {
        model.put("prestamo",prestamosImp.findPrestamos(nombre));
        return"prestamoClienteList.html";
    }
}
