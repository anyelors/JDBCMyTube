package dao;

import modelo.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements CrudDAO<Usuario> {

    private Connection con;

    public UsuarioDAO(Connection connection) {
        con = connection;
    }

    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT idusuario, dni, nombre, nacimiento, mail FROM usuarios";
        try (Statement st = con.createStatement()) {

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                long id = rs.getLong("idusuario");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                LocalDate nacimiento = rs.getDate("nacimiento").toLocalDate();
                String mail = rs.getString("mail");
                usuarios.add(new Usuario(id, dni, nombre, nacimiento, mail));
            }
        }
        return usuarios;
    }

    @Override
    public Usuario obtener(Long ident) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT idusuario, dni, nombre, nacimiento, mail FROM usuarios WHERE idusuario = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, ident);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("idusuario");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                LocalDate nacimiento = rs.getDate("nacimiento").toLocalDate();
                String mail = rs.getString("mail");
                usuario = new Usuario(id, dni, nombre, nacimiento, mail);
            }
        }

        return usuario;
    }

    @Override
    public void alta(Usuario elemento) throws SQLException {
        String sql = "INSERT INTO usuarios (dni, nombre, nacimiento, mail) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, elemento.getDni());
            pst.setString(2, elemento.getNombre());
            pst.setDate(3, Date.valueOf(elemento.getNacimiento()));
            pst.setString(4, elemento.getMail());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                elemento.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public void actualizar(Usuario elemento) throws SQLException {
        String sql = "UPDATE usuarios SET dni = ?, nombre = ?, nacimiento = ?, mail = ? WHERE idusuario = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, elemento.getDni());
            pst.setString(2, elemento.getNombre());
            pst.setDate(3, Date.valueOf(elemento.getNacimiento()));
            pst.setString(4, elemento.getMail());
            pst.setLong(5, elemento.getId());

            pst.executeUpdate();
        }
    }

    @Override
    public void eliminar(Usuario elemento) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE idusuario = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, elemento.getId());

            pst.executeUpdate();
        }
    }
}
