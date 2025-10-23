package v1;

import dao.CrudDAO;
import dao.GeneroDAO;
import dao.PeliculaDAO;
import datasource.Database;
import modelo.Genero;
import modelo.Pelicula;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EjemploJDBCRelaciones {

    public static void main(String[] args) {

        try ( Connection con = Database.getConnection()) {

            CrudDAO<Pelicula> peliculaDAO = new PeliculaDAO(con);
            CrudDAO<Genero> generoDAO = new GeneroDAO(con);

            // Obtener un genero
            Genero genero_seleccionado = generoDAO.obtener(1L);
            System.out.println("Genero obtenido: " + genero_seleccionado.getNombre());
            // Creo la pelicula
            Pelicula nueva = new Pelicula("Matrix",genero_seleccionado, true, 2000, 0 );
            // Inserto la pelicula
            peliculaDAO.alta(nueva);

            System.out.println("LISTADO DE PELICULAS");
            List<Pelicula> peliculas = peliculaDAO.listar();
            peliculas.stream().map( p -> p.getTitulo() + " " + p.getGenero().getNombre())
                    .forEach(System.out::println);
            //peliculas.forEach(System.out::println);

        } catch ( SQLException ex ) {
            System.out.println("Código de error: " + ex.getErrorCode());
            System.out.println("Mensaje: " + ex.getMessage());
            System.out.println("Estado SQL" + ex.getSQLState());
        }

    }

}
