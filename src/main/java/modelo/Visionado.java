package modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Visionado {

    private Long id;
    private Usuario usuario;
    private Pelicula pelicula;
    private LocalDate fecha;

    public Visionado() {}

    public Visionado(Long id, Usuario usuario, Pelicula pelicula, LocalDate fecha) {
        this.id = id;
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.fecha = fecha;
    }

    public Visionado(Usuario usuario, Pelicula pelicula, LocalDate fecha) {
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Visionado visionado = (Visionado) o;
        return Objects.equals(id, visionado.id);
    }

    @Override
    public String toString() {
        return "Visionado [" +
                "Fecha = " + fecha +
                ", Id = " + id +
                ", Usuario = " + usuario +
                ", Pelicula = " + pelicula +
                ']';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
