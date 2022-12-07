package edu.egg.Biblioteca.Repository;

import edu.egg.Biblioteca.Entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String>{
    @Query("select u from Usuario u where u.email = :email")
    public Usuario findByemail(@Param("email")String email);
}
