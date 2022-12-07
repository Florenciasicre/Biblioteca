package edu.egg.Biblioteca.Service;

import edu.egg.Biblioteca.Entidad.Usuario;
import edu.egg.Biblioteca.Exeptions.MyExeptions;
import edu.egg.Biblioteca.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static edu.egg.Biblioteca.Enumeraciones.Rol.USER;

@Service
public class UsuarioServiceImp implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void singUp(String nombre, String email, String password, String password2) throws MyExeptions {
        validate(nombre, email, password, password2);
        Usuario usuario = new Usuario(nombre, email,new BCryptPasswordEncoder().encode(password), new BCryptPasswordEncoder().encode(password2), USER);
        usuarioRepository.save(usuario);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByemail(email);
        if (usuario != null) {
            List<GrantedAuthority> permition = new ArrayList<>();
            //a quien da estos permisos
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE" + usuario.getRol().toString());
            permition.add(p);
            return new User(usuario.getEmail(), usuario.getPassword(), permition);
        }
        return null;
    }

    public void validate(String nombre, String email, String password, String password2) throws MyExeptions {
        if (nombre == null || nombre.isEmpty()) {
            throw new MyExeptions("Hubo un problema con el nombre");
        }
        if (email == null || email.isEmpty()) {
            throw new MyExeptions("Hubo un problema con el email");
        }
        if (password == null || password.length() <= 5 ) {
            throw new MyExeptions("Hubo un problema con la contraseña");
        }
        if (password2 == null ||password2.length() <= 5) {
            throw new MyExeptions("Hubo un problema con la contraseña");
        }
        if (!password.equals(password2)) {
            throw new MyExeptions("Las contraseñas deben coincidir");
        }

    }
}
