package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Autor;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServiceImp implements AutorService{
    @Autowired
    AutorRepository autorRepository;

    @Transactional
    public void createAutor(String nombre) throws MyExeptions {
        validateName(nombre);
        autorRepository.save(new Autor(nombre));
    }
    @Override
    public Autor getOne(String id) {
        return autorRepository.getOne(id);
    }
    @Override
    public List<Autor> fetchListAutor() {
        return autorRepository.findAll();
    }

    @Transactional
    @Override
    public void editAutor(String id, String nombre) throws MyExeptions {
        validateName(nombre);validateID(id);
        Optional<Autor> rs = autorRepository.findById(id);
        if (rs.isPresent()) {
            Autor autor = rs.get();
            autor.setNombre(nombre);
            autorRepository.save(autor);
        }
    }
    @Transactional
    public void delete(String id) throws MyExeptions {
        System.out.println("ingreso al metodo");
           validateID(id);
           Optional<Autor> rs = autorRepository.findById(id);
            if (rs.isPresent()) {
            Autor autor = rs.get();
            autorRepository.delete(autor);
        }
    }
    private void validateName(String name) throws MyExeptions {
        if (name == null || name.isEmpty()) {
            throw new MyExeptions("El nombre no puede ser null");
        }
    }
    private void validateID(String id) throws MyExeptions {
        if (id == null || id.isEmpty()) {
            throw new MyExeptions("El id no puede ser nulo");
        }

    }

}
