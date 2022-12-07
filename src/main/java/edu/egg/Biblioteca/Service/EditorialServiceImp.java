package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Editorial;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Repository.EditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class EditorialServiceImp implements EditorialService{
    @Autowired
    EditoriaRepository editoriaRepository;
    @Transactional
    public void createEditorial(String nombre)throws MyExeptions {
        validateName(nombre);
        editoriaRepository.save(new Editorial(nombre));
    }
    @Override
    public List<Editorial> fetchListEditoriales(){
        return editoriaRepository.findAll();
    }

    @Transactional
    @Override
    public void editEditorial(String id, String nombre)throws MyExeptions{
        validateName(nombre); validateId(id);
        Optional<Editorial> rs = editoriaRepository.findById(id);
        if(rs.isPresent()){
            Editorial editorial= rs.get();
            editorial.setNombre(nombre);
            editoriaRepository.save(editorial);
        }
    }
    @Transactional
    @Override
    public void delete(String id)throws MyExeptions{
        validateId(id);
        Optional<Editorial> rs = editoriaRepository.findById(id);
        if(rs.isPresent()){
            Editorial editorial= rs.get();
            editoriaRepository.delete(editorial);
        }
    }
    @Override
    public Editorial getOne(String id){
        return editoriaRepository.getOne(id);
    }

    private void validateName(String name)throws MyExeptions{
        if(name == null|| name.isEmpty()){
            throw new MyExeptions("El nombre no puede ser null");
        }

    }
    private void validateId(String id)throws MyExeptions{
        if(id == null|| id.isEmpty()){
            throw new MyExeptions("El id no puede ser null");
        }

    }
}
