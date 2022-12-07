package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Autor;
import edu.egg.Biblioteca.Exeptions.MyExeptions;

import java.util.List;

public interface AutorService{
    public List<Autor> fetchListAutor();
    public void editAutor(String id, String nombre) throws MyExeptions;
    public Autor getOne(String id);

}
