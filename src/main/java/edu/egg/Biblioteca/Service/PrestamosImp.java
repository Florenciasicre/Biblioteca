package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Cliente;
import edu.egg.Biblioteca.Entidad.Libro;
import edu.egg.Biblioteca.Entidad.Prestamo;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Repository.ClienteRepository;
import edu.egg.Biblioteca.Repository.LibroRepository;
import edu.egg.Biblioteca.Repository.PrestamosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamosImp implements PrestamoService{

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PrestamosRepository prestamosRepository;

    @Override
    public List<Prestamo> listPrestamo() {
        return prestamosRepository.findAll();
    }

    @Override
    public void editPrestamo(String id, boolean alta, Date date, Date devolucion, String idCliente, String isbn) throws MyExeptions {
           validateDate(date);validateDate(devolucion);
           validate(alta,idCliente,isbn);

           validateId(id);
           Optional<Prestamo> rta = prestamosRepository.findById(id);
           Cliente cliente = clienteRepository.getOne(idCliente);
           Libro libro = libroRepository.getOne(isbn);
           if(rta.isPresent()){
               Prestamo prestamo = rta.get();
               prestamo.setAlta(alta);
               prestamo.setDate(date);
               prestamo.setDevolucion(devolucion);
               if(cliente.getId() != null){
                   prestamo.setCliente(cliente);
               }
               if(libro.getIsbn() != null){
                   prestamo.setLibro(libro);
               }
               prestamosRepository.save(prestamo);
           }
    }
    @Override
    public Prestamo getOne(String id) {
        return prestamosRepository.getReferenceById(id);
    }

    @Override
    @Transactional
    public void createPrestamo(Boolean alta, String isbn, String idCliente, Date devolucion)throws MyExeptions {
        validate(alta,isbn,idCliente);
        validateDate(devolucion);
        Libro libro = libroRepository.findById(isbn).get();
        Cliente cliente = clienteRepository.findById(idCliente).get();
        if(libro.getEjemplares()>0){
            libro.setEjemplares(libro.getEjemplares()-1);
            prestamosRepository.save(new Prestamo(new Date(),devolucion,alta,libro,cliente));
        }else{
            throw new MyExeptions("No hay ejemplar disponible");
        }
    }

    @Override
    public void delete(String id) throws MyExeptions {
        validateId(id);
        Optional<Prestamo> rta = prestamosRepository.findById(id);
        if(rta.isPresent()){
            Prestamo prestamo = rta.get();
            prestamosRepository.delete(prestamo);
        }
    }



    @Override
    public void updateRestore(String id, boolean alta) throws MyExeptions {
        validateId(id);
        Optional<Prestamo> rta = prestamosRepository.findById(id);
        if(rta.isPresent()){
            Prestamo prestamo = rta.get();
            Libro libro = libroRepository.findById(prestamo.getLibro().getIsbn()).get();
            prestamo.setAlta(alta);
            libro.setEjemplares(libro.getEjemplares()+1);
            prestamosRepository.save(prestamo);
        }

    }

    @Override
    public List<Prestamo> findPrestamos(String nombre) {
        Cliente cliente = clienteRepository.findBynombreAllIgnoreCase(nombre);
        return prestamosRepository.findByCliente(cliente);
    }

    private void validateId(String id)throws MyExeptions{
        if(id == null){
            throw new MyExeptions("Hubo un problema con el id");
        }
    }
    private void validateDate(Date date)throws MyExeptions{
        if(date == null){
            throw new MyExeptions("Hubo un problema con la fecha");
        }
    }
    private void validate(Boolean alta, String idCliente, String idLibro )throws MyExeptions{
        if(alta == null){
            throw new MyExeptions("Hubo un problema con el alta");
        }
        if(idCliente == null|| idCliente.isEmpty()){
            throw new MyExeptions("Hubo un problema con el id del cliente");
        }
        if(idLibro == null|| idLibro.isEmpty()){
            throw new MyExeptions("Hubo un problema con el Id del libro");
        }
    }
}
