package servicio;

import modelo.Genero;
import modelo.Pelicula;
import modelo.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface Servicio {

    List<Genero> listarGeneros() throws SQLException;
    List<Usuario> listarUsuarios() throws SQLException;
    List<Pelicula> listarPeliculas() throws SQLException;

    void altaUsuario( Usuario usuario ) throws SQLException;
    void altaPelicula( Pelicula pelicula ) throws SQLException;
    void altaGenero( Genero genero ) throws SQLException;

    boolean visionado(Usuario usuario, Pelicula pelicula, LocalDate fecha) throws SQLException;

    List<Pelicula> listarPeliculasPorGenero(Genero genero ) throws SQLException;
    List<Usuario> listarUsuariosPorPelicula(Pelicula pelicula ) throws SQLException;
    List<Pelicula> listarPeliculasPorUsuario(Usuario usuario ) throws SQLException;

}
