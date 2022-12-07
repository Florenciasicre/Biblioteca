package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Editorial;
import edu.egg.Biblioteca.Exeptions.MyExeptions;

import java.util.List;

public interface EditorialService {
    public List<Editorial> fetchListEditoriales();
    public void editEditorial(String id, String nombre) throws MyExeptions;
    public Editorial getOne(String id) throws MyExeptions;
    public void delete(String id)throws MyExeptions;

}
