package v1;

import modelo.Genero;
import servicio.Servicio;
import servicio.ServicioImpl;

import java.sql.SQLException;
import java.util.List;

public class EjemploServicio {
    public static void main(String[] args) {

        Servicio servicio = new ServicioImpl();
        try {
            List<Genero> generos = servicio.listarGeneros();
            generos.forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
