package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Libro;
import edu.egg.Biblioteca.Exeptions.MyExeptions;

import java.util.List;


public interface LibroService {
    public List<Libro> fetchListLibros();
    public List<Libro> findByAutor(String idAutor);
    public void editLibro(String id, String titulo, Integer ejemplares) throws MyExeptions;
    public Libro getOne(String id);
}
