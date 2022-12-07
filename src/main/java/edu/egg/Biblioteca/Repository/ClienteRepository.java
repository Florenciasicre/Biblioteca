package edu.egg.Biblioteca.Repository;

import edu.egg.Biblioteca.Entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, String>{
    public Cliente findBynombreAllIgnoreCase(String nombre);
}