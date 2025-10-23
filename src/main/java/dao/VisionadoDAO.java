package dao;

import modelo.Pelicula;
import modelo.Usuario;
import modelo.Visionado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisionadoDAO implements CrudDAO<Visionado> {

    private Connection con;

    public VisionadoDAO( Connection connection ) {
        con = connection;
    }

    @Override
    public List<Visionado> listar() throws SQLException {
        List<Visionado> visionados = new ArrayList<>();
        CrudDAO<Pelicula> peliculasDAO = new PeliculaDAO(con);
        CrudDAO<Usuario> usuariosDAO = new UsuarioDAO(con);
        String sql = "SELECT idvisionado, idusuario, idpelicula, fecha FROM visionados";
        try(Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while( rs.next()) {
                Visionado visionado = new Visionado();
                visionado.setId( rs.getLong("idvisionado"));
                Pelicula pelicula = peliculasDAO.obtener(rs.getLong("idpelicula"));
                visionado.setPelicula(pelicula);
                Usuario usuario = usuariosDAO.obtener(rs.getLong("idusuario"));
                visionado.setUsuario(usuario);
                visionado.setFecha(rs.getDate("fecha").toLocalDate());
                visionados.add(visionado);
            }
        }
        return visionados;
    }

    @Override
    public Visionado obtener(Long id) throws SQLException {
        Visionado visionado = null;
        CrudDAO<Pelicula> peliculasDAO = new PeliculaDAO(con);
        CrudDAO<Usuario> usuariosDAO = new UsuarioDAO(con);
        String sql = "SELECT idvisionado, idusuario, idpelicula, fecha FROM visionados WHERE idvisionado = ?";
        try(PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if( rs.next()) {
                visionado = new Visionado();
                visionado.setId( rs.getLong("idvisionado"));
                Pelicula pelicula = peliculasDAO.obtener(rs.getLong("idpelicula"));
                visionado.setPelicula(pelicula);
                Usuario usuario = usuariosDAO.obtener(rs.getLong("idusuario"));
                visionado.setUsuario(usuario);
                visionado.setFecha(rs.getDate("fecha").toLocalDate());
            }
        }
        return visionado;
    }

    @Override
    public void alta(Visionado elemento) throws SQLException {
        String sql = "INSERT INTO visionados( idusuario, idpelicula, fecha ) VALUES (?, ?, ?)";
        try ( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {     // Para recuperar la ID autogenerada
            pst.setLong(1, elemento.getUsuario().getId());
            pst.setLong(2, elemento.getPelicula().getId());
            pst.setDate(3, Date.valueOf(elemento.getFecha()));
            // Recuperacion del ID generado
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) elemento.setId(rs.getLong(1));
        }
    }

    @Override
    public void actualizar(Visionado elemento) throws SQLException {
        String sql = "UPDATE visionados SET idusuario = ? , idpelicula = ? , fecha = ? WHERE idvisionado = ?";
        try ( PreparedStatement pst = con.prepareStatement(sql)) {     // Para recuperar la ID autogenerada
            pst.setLong(1, elemento.getUsuario().getId());
            pst.setLong(2, elemento.getPelicula().getId());
            pst.setDate(3, Date.valueOf(elemento.getFecha()));
            pst.setLong(4, elemento.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void eliminar(Visionado elemento) throws SQLException {
        try( PreparedStatement pst = con.prepareStatement("DELETE FROM visionados WHERE idvisionado = ?") ) {
            pst.setLong(1, elemento.getId());
            pst.executeUpdate();
        }
    }
}
