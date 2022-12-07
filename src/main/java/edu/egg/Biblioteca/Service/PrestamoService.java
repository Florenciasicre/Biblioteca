package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Prestamo;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface PrestamoService {
    public List<Prestamo> listPrestamo();
    public void editPrestamo(String id,boolean alta, Date date, Date devolucion, String idCliente, String isbn) throws MyExeptions;
    public Prestamo getOne(String id);
    public void createPrestamo(Boolean alta, String isbn, String idCliente, Date devolucion) throws MyExeptions;
    public void delete(String id) throws MyExeptions;
    public void updateRestore(String id, boolean alta) throws MyExeptions;
    List<Prestamo> findPrestamos(String nombre);

}
