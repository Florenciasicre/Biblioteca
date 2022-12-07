package edu.egg.Biblioteca.Repository;

import edu.egg.Biblioteca.Entidad.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditoriaRepository extends JpaRepository<Editorial,String>{

}
