package dao;

import modelo.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAO implements CrudDAO<Genero>{
    private Connection con;

    public GeneroDAO(Connection connection ) {
        this.con = connection;
    }

    @Override
    public List<Genero> listar() throws SQLException {
        List<Genero> generos = new ArrayList<>();
        try( Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT idgenero, genero FROM generos");
            while (rs.next()) {
                generos.add( obtener( rs ));
            }
        }
        return generos;
    }

    @Override
    public Genero obtener(Long id) throws SQLException {
        Genero genero = null;
        try( PreparedStatement st = con.prepareStatement("SELECT idgenero, genero FROM generos WHERE idgenero = ?")) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                genero = obtener(rs);
            }
        }
        return genero;
    }

    @Override
    public void alta(Genero elemento) throws SQLException {
        String sql = "INSERT INTO generos( genero ) VALUES (?)";
        try ( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {     // Para recuperar la ID autogenerada
            pst.setString(1, elemento.getNombre());
            // Recuperacion del ID generado
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) elemento.setId(rs.getLong(1));
        }
    }

    @Override
    public void actualizar(Genero elemento) throws SQLException {
        String sql = "UPDATE generos SET genero=? WHERE idgenero = ?";
        try ( PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, elemento.getNombre());
            pst.setLong(2, elemento.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void eliminar(Genero elemento) throws SQLException {
        try( PreparedStatement pst = con.prepareStatement("DELETE FROM generos WHERE idgenero = ?") ) {
            pst.setLong(1, elemento.getId());
            pst.executeUpdate();
        }
    }

    private Genero obtener( ResultSet rs ) throws SQLException {
        Genero genero = new Genero();
        genero.setId(rs.getLong("idgenero"));
        genero.setNombre(rs.getString("genero"));
        return genero;
    }
}
