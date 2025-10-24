package servicio;

import dao.*;
import datasource.Database;
import modelo.Genero;
import modelo.Pelicula;
import modelo.Usuario;
import modelo.Visionado;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ServicioImpl implements Servicio{
    @Override
    public List<Genero> listarGeneros() throws SQLException {
        List<Genero> generos = new ArrayList<>();
        try(Connection conn = Database.getConnection()) {
            CrudDAO<Genero> dao = new GeneroDAO( conn );
            generos = dao.listar();
        }
        return generos;
    }

    @Override
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try(Connection conn = Database.getConnection()) {
            CrudDAO<Usuario> dao = new UsuarioDAO( conn );
            usuarios = dao.listar();
        }
        return usuarios;
    }

    @Override
    public List<Pelicula> listarPeliculas() throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        try(Connection conn = Database.getConnection()) {
            CrudDAO<Pelicula> dao = new PeliculaDAO( conn );
            peliculas = dao.listar();
        }
        return peliculas;
    }

    @Override
    public void altaUsuario(Usuario usuario) throws SQLException {
        try(Connection conn = Database.getConnection()) {
            CrudDAO<Usuario> dao = new UsuarioDAO( conn );
            dao.alta(usuario);
        }
    }

    @Override
    public void altaPelicula(Pelicula pelicula) throws SQLException {
        try(Connection conn = Database.getConnection()) {
            CrudDAO<Pelicula> dao = new PeliculaDAO( conn );
            dao.alta(pelicula);
        }
    }

    @Override
    public void altaGenero(Genero genero) throws SQLException {
        try(Connection conn = Database.getConnection()) {
            CrudDAO<Genero> dao = new GeneroDAO( conn );
            dao.alta(genero);
        }
    }

    @Override
    public boolean visionado(Usuario usuario, Pelicula pelicula, LocalDate fecha) throws SQLException {
        long edadUsuario = ChronoUnit.YEARS.between(usuario.getNacimiento(), LocalDate.now());
        // Si NO sucede ( que la pelicula sea para mayor de edad y el usuario es menor )
        if ( !(pelicula.isAdulto() && edadUsuario <18)) {
            try ( Connection con = Database.getConnection()) {
                CrudDAO<Visionado> visionadosDAO = new VisionadoDAO( con );
                CrudDAO<Pelicula> peliculasDAO = new PeliculaDAO(con);
                // CONTROL DE TRANSACCION PARA ASEGURAR QUE TODOS LOS CAMBIOS
                // SE HAGAN INTEGRAMENTE ( o todos o ninguno ).
                try {
                    // Deshabilitar la confirmacion automatica de operaciones
                    // inserciones, eliminaciones y actualizaciones
                    if ( con.getAutoCommit()) con.setAutoCommit(false);

                    // Creo el registro de visionado y lo registro en la BD
                    Visionado visionado = new Visionado(usuario, pelicula, fecha);
                    visionadosDAO.alta(visionado);
                    // Actualizar el recuento de visionados de la pelicula
                    pelicula.setVisionado(pelicula.getVisionado() + 1);
                    peliculasDAO.actualizar(pelicula);

                    // Confirmar la transaccion -> APLICA FINALMENTE TODOS LOS CAMBIOS
                    con.commit();
                } catch( SQLException ex ) {
                    // Rechaza la transaccion -> DESHAZ TODOS LOS CAMBIOS
                    con.rollback();
                    throw ex;
                }
            }
            return true;
        } else
            return false;
    }

    @Override
    public List<Pelicula> listarPeliculasPorGenero(Genero genero) throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        try( Connection conn = Database.getConnection()) {
            PeliculaDAO dao = new PeliculaDAO(conn);
            peliculas = dao.listarByGenero(genero.getId());
        }
        return peliculas;
    }

    @Override
    public List<Usuario> listarUsuariosPorPelicula(Pelicula pelicula) throws SQLException {
        List<Visionado> visionados = new ArrayList<>();
        try( Connection conn = Database.getConnection()) {
            VisionadoDAO dao = new VisionadoDAO(conn);
            visionados = dao.listarByPelicula(pelicula.getId());
        }
        return visionados.stream().map(Visionado::getUsuario).toList();
    }

    @Override
    public List<Pelicula> listarPeliculasPorUsuario(Usuario usuario) throws SQLException {
        List<Visionado> visionados = new ArrayList<>();
        try( Connection conn = Database.getConnection()) {
            VisionadoDAO dao = new VisionadoDAO(conn);
            visionados = dao.listarByUsuario(usuario.getId());
        }
        return visionados.stream().map(Visionado::getPelicula).toList();
    }
}
