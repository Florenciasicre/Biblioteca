package edu.egg.Biblioteca.Entidad;

import edu.egg.Biblioteca.Enumeraciones.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String email;
    private String password;
    private String password2;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario(String nombre, String email, String password, String password2, Rol rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.rol = rol;
    }

}
