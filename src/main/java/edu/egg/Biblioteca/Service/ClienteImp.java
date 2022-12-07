package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Cliente;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteImp implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listCliente(){
        return clienteRepository.findAll();
    }

    @Override
    public void editCliente(String id, Long document, String nombre, String apellido, String telefono) throws MyExeptions {
        validateId(id);
        validate(document,nombre,apellido,telefono);
        Optional<Cliente> rs = clienteRepository.findById(id);
        if (rs.isPresent()){
            Cliente cliente = rs.get();
            cliente.setDocumento(document);
            cliente.setApellido(apellido);
            cliente.setNombre(nombre);
            cliente.setTelefono(telefono);
            clienteRepository.save(cliente);
        }
    }
    @Override
    public Cliente getOne(String id) {
        return clienteRepository.getReferenceById(id);
    }
    @Transactional
    @Override
    public void create(Long documento, String nombre, String apellido, String telefono, boolean alta) throws MyExeptions {
        validate(documento,nombre,apellido,telefono);
        clienteRepository.save(new Cliente(documento,nombre,apellido,telefono,alta));
    }

    @Override
    public void delete(String id) throws MyExeptions {
        validateId(id);
        Optional<Cliente> rst = clienteRepository.findById(id);
        if(rst.isPresent()){
            Cliente cliente = rst.get();
            clienteRepository.delete(cliente);
        }
    }
    public Cliente searchByName(String nombre){
        return clienteRepository.findBynombreAllIgnoreCase(nombre);
    }

    public void validate(Long documento, String nombre, String apellido, String telefono) throws MyExeptions {
        if(documento == null){
            throw new MyExeptions("Hubo un problema con el documento");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new MyExeptions("Hubo un problema con el nombre");
        }
        if(apellido == null || apellido.isEmpty()){
            throw new MyExeptions("Hubo un problema con el apellido");
        }
        if(telefono == null || telefono.isEmpty()){
            throw new MyExeptions("Hubo un problema con el documento");
        }

    }
    public void validateId(String id) throws MyExeptions {
        if(id == null || id.isEmpty()){
            throw new MyExeptions("Hubo un problema con el id");
        }
    }
}
