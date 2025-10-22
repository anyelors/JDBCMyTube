package v1;

import dao.CrudDAO;
import dao.UsuarioDAO;
import datasource.Database;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EjemploJDBCDAO {

    public static void main(String[] args) {
        List<Usuario> usuarios;
        try (Connection con = Database.getConnection()){
            CrudDAO<Usuario> usuariosDAO = new UsuarioDAO(con);
            /*
            Usuario nuevo = new Usuario("12345678P",
                    "Paco Perez",
                    LocalDate.of(1985, 6, 17),
                    "paco@mail.com");

            usuariosDAO.alta(nuevo);
             */

            Usuario usuario = usuariosDAO.obtener(2L);
            System.out.println(usuario.toString());

            usuario.setNombre("Periquito Pim Pim");
            usuariosDAO.actualizar(usuario);

            Usuario usuarioDel = usuariosDAO.obtener(3L);
            usuariosDAO.eliminar(usuarioDel);

            System.out.println();

            usuarios = usuariosDAO.listar();
            usuarios.forEach(System.out::println);

        }catch (SQLException ex){
            System.out.println("Codigo error: + " + ex.getErrorCode());
            System.out.println("Mensaje error: + " + ex.getMessage());
            System.out.println("Estado SQL: + " + ex.getSQLState());
        }
    }
}
