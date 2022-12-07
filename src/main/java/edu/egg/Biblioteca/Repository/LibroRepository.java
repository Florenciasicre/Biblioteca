package edu.egg.Biblioteca.Repository;

import edu.egg.Biblioteca.Entidad.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository <Libro, String>{

}
