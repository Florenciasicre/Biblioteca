package edu.egg.Biblioteca.Entidad;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Prestamo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Date date;
    private Date devolucion;
    private Boolean alta;
   @OneToOne
    Libro libro;
   @OneToOne
   Cliente cliente;

    public Prestamo() {
    }

    public Prestamo(Date date, Date devolucion, Boolean alta, Libro libro, Cliente cliente) {
        this.date = date;
        this.devolucion = devolucion;
        this.alta = alta;
        this.libro = libro;
        this.cliente = cliente;
    }

    public Date getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Date devolucion) {
        this.devolucion = devolucion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", alta=" + alta +
                ", libro=" + libro +
                ", cliente=" + cliente +
                '}';
    }
}
