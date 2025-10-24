package v1;

import dao.*;
import datasource.Database;
import modelo.Genero;
import modelo.Pelicula;
import modelo.Usuario;
import modelo.Visionado;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EjemploDBCRelacionesN {
    public static void main(String[] args) {
        try (Connection con = Database.getConnection()) {
            CrudDAO<Usuario> usuarioDAO = new UsuarioDAO(con);
            CrudDAO<Pelicula> peliculaDAO = new PeliculaDAO(con);
            CrudDAO<Genero> generoDAO = new GeneroDAO(con);
            VisionadoDAO visionadoDAO = new VisionadoDAO(con);
            PeliculaDAO peliculaDAOBy = new PeliculaDAO(con);

            Usuario usuarioSeleccionado = usuarioDAO.obtener(2L);
            List<Visionado> visionadosUser = visionadoDAO.listarByUsuario(usuarioSeleccionado.getId());
            visionadosUser.forEach(System.out::println);

            System.out.println();

            Pelicula peliculaSeleccionado = peliculaDAO.obtener(1L);
            List<Visionado> visionadosPel = visionadoDAO.listarByPelicula(peliculaSeleccionado.getId());
            visionadosPel.forEach(System.out::println);

            System.out.println();

            Genero genero = generoDAO.obtener(1L);
            List<Pelicula> peliculasGene = peliculaDAOBy.listarByGenero(genero.getId());
            peliculasGene.forEach(System.out::println);

        } catch (SQLException ex) {
            System.out.println("Código de error: " + ex.getErrorCode());
            System.out.println("Mensaje: " + ex.getMessage());
            System.out.println("Estado SQL" + ex.getSQLState());
        }
    }
}
