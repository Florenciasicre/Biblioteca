package edu.egg.Biblioteca.Service;
import edu.egg.Biblioteca.Entidad.Autor;
import edu.egg.Biblioteca.Entidad.Editorial;
import edu.egg.Biblioteca.Entidad.Libro;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Repository.AutorRepository;
import edu.egg.Biblioteca.Repository.EditoriaRepository;
import edu.egg.Biblioteca.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImp implements LibroService{
    @Autowired
    LibroRepository libroRepository;
    @Autowired
    AutorRepository autorRepository;
    @Autowired
    EditoriaRepository editoriaRepository;

    @Transactional
    public void saveLibro(String isbn, String titulo, Integer ejemplares,String idAutor, String idEditorial)throws MyExeptions {
        validar(isbn, titulo, ejemplares,idAutor,idEditorial);
        Autor autor = autorRepository.findById(idAutor).get();
        Editorial editorial = editoriaRepository.findById(idEditorial).get();
        Libro libro = new Libro(isbn,titulo,ejemplares, new Date(),autor,editorial);
        libroRepository.save(libro);
    }
    @Override
    public List<Libro> fetchListLibros() {
        return (List<Libro>) libroRepository.findAll() ;
    }

    @Override
    public List<Libro> findByAutor(String idAutor) {
        return null;
    }

    @Override
    public void editLibro(String isbn, String titulo, Integer ejemplares)throws MyExeptions {
        validateShort(isbn,titulo,ejemplares);
        Optional<Libro> rs =libroRepository.findById(isbn);
        if(rs.isPresent()){
            Libro libro = rs.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libroRepository.save(libro);
        }
    }
    @Transactional
    public void delete(String id) throws MyExeptions {
        if(id == null || id.isEmpty()){
            throw new MyExeptions("El id no puede ser nulo");
        }
        Optional<Libro> rs = libroRepository.findById(id);
        if (rs.isPresent()) {
            Libro libro = rs.get();
            libroRepository.delete(libro);
        }
    }
    @Override
    public Libro getOne(String id) {
        return libroRepository.getOne(id);
    }

    private void validar(String isbn, String titulo, Integer ejemplares,String idAutor, String idEditorial)throws MyExeptions{
        if(isbn == null|| isbn.isEmpty()){
            throw new MyExeptions("El id no puede ser null");
        }
        if(idAutor == null || idAutor.isEmpty()){
            throw new MyExeptions("El id del autor no puede ser null");
        }
        if(idEditorial == null || idEditorial.isEmpty()){
            throw new MyExeptions("El id de la editorial no puede ser null");
        }
        if(titulo == null || titulo.isEmpty() ){
            throw new MyExeptions("El título no puede ser vacío, ni nulo");
        }
        if(ejemplares== null){
            throw new MyExeptions("El ejemplar no puede ser nulo");
        }
    }
    private void validateShort(String isbn, String titulo, Integer ejemplares) throws MyExeptions {
        if(isbn == null|| isbn.isEmpty()){
            throw new MyExeptions("Hubo problemas con el id. No puede ser nullo");
        }
        if(titulo == null || titulo.isEmpty() ){
            throw new MyExeptions("El título no puede ser vacío, ni nulo");
        }
        if(ejemplares== null){
            throw new MyExeptions("El ejemplar no puede ser nulo");
        }
    }
}
