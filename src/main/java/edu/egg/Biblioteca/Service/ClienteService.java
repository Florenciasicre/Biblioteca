package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Cliente;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {
    public List<Cliente> listCliente();
    public void editCliente(String id,Long document, String nombre,String apellido,String telefono) throws MyExeptions;
    public Cliente getOne(String id);
    public void create(Long documento, String nombre, String apellido, String telefono, boolean alta)throws MyExeptions;
    public void delete(String id) throws MyExeptions;



}
