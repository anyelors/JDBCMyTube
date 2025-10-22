package v1;

import datasource.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EjemploJDBCSimple {

    public static void listarClientes () {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (Connection con = Database.getConnection()) {

            String sql = "SELECT idusuario, dni, nombre, nacimiento, mail FROM usuarios";
            Statement st = con.createStatement();
            ResultSet rs =  st.executeQuery(sql);

            while ( rs.next() ) {
                long id = rs.getLong("idusuario");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                LocalDate nacimiento = rs.getDate("nacimiento").toLocalDate();
                String mail = rs.getString("mail");

                System.out.printf("%d %s %s %s %s\n", id, dni, nombre, nacimiento.format(format), mail);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static Long insertarCliente(String dni, String nombre, LocalDate nacimiento, String mail) {
        Long id = null;

        try (Connection con = Database.getConnection()) {

            String sql = "INSERT INTO usuarios (dni, nombre, nacimiento, mail) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setDate(3, Date.valueOf(nacimiento));
            pst.setString(4, mail);
            //Ejecutemos la query INSERT/UPDATE/DELETE ( executeUpdate() )
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
                System.out.println("id = " + id);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return id;
    }

    public static void main(String[] args) {
        /*
        try {
            Connection conexion = Database.getConnection();
            System.out.println("isClosed = [" + conexion.isClosed() + "]");
            System.out.println("BD Open");
            conexion.close();
            System.out.println("BD Close");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        */

        /*
        Long id = insertarCliente("3056629W",
                "Pepa Pig",
                LocalDate.of(1988, 3, 20),
                "pepa@mail.com");
         */

        listarClientes();
    }

}
