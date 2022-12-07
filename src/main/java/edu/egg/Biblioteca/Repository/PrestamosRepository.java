package edu.egg.Biblioteca.Repository;

import edu.egg.Biblioteca.Entidad.Cliente;
import edu.egg.Biblioteca.Entidad.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamo, String>{
     public List<Prestamo> findByCliente(@Param("cliente") Cliente cliente);
}
