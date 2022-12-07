package edu.egg.Biblioteca.Controller;

import edu.egg.Biblioteca.Entidad.Autor;
import edu.egg.Biblioteca.Entidad.Editorial;
import edu.egg.Biblioteca.Entidad.Libro;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Service.AutorServiceImp;
import edu.egg.Biblioteca.Service.EditorialServiceImp;
import edu.egg.Biblioteca.Service.LibroServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class LibroController {
    @Autowired
    private LibroServiceImp libroServiceImp;
    @Autowired
    private AutorServiceImp autorServiceImp;
    @Autowired
    private EditorialServiceImp editorialServiceImp;

    @GetMapping("/list")
    public String BookList(ModelMap model) {
        List<Libro> libros = libroServiceImp.fetchListLibros();
        model.put("libros", libros);
        return "bookList.html";
    }

    @GetMapping("/registrar")
    public String register(ModelMap model) {
        List<Autor> autores = autorServiceImp.fetchListAutor();
        List<Editorial> editorial = editorialServiceImp.fetchListEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editorial);
        return "libroForm.html";
    }

    //required false, para que entre y largue la exepcion desde serviceImp
    //Model insert información mostrar por pantalla
    @PostMapping("/registro")
    public String saveBook(@RequestParam String isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap model) {
        try {
            libroServiceImp.saveLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            model.put("success", "El libro fue creado correctamente");
        } catch (MyExeptions e) {
            model.put("fail", e.getMessage());
            List<Autor> autores = autorServiceImp.fetchListAutor();
            List<Editorial> editorial = editorialServiceImp.fetchListEditoriales();
            model.addAttribute("autores", autores);
            model.addAttribute("editoriales", editorial);
            return "libroForm.html";
        }
        return "index.html";
    }
    @GetMapping("/update/{isbn}")
    public String updateBook(@PathVariable String isbn, ModelMap model){
        model.put("libro",libroServiceImp.getOne(isbn));
        return "libroUpdate.html";
    }
    @PostMapping("/update/{isbn}")
    public String updateBook(@PathVariable String isbn, String titulo, Integer ejemplares, ModelMap model){
        try {
            libroServiceImp.editLibro(isbn, titulo, ejemplares);
            return "redirect:../list";
        } catch (MyExeptions e) {
            model.put("fail",e.getMessage());
            return "libroUpdate.html";
        }
    }
    @GetMapping("/delete/{isbn}")
    public String delete(@PathVariable String isbn, ModelMap model) {
        model.put("libro", libroServiceImp.getOne(isbn));
        return "libroDelete.html";
    }

    @PostMapping("/delete/{isbn}")
    public String deleteAuthor(@PathVariable String isbn, ModelMap model) {
        try {
            libroServiceImp.delete(isbn);
            return "redirect:../list";
        } catch (MyExeptions e) {
            model.put("fail", e.getMessage());
            return "libroDelete.html";
        }
    }

}
