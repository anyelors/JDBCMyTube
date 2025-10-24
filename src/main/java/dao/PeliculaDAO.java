package dao;

import modelo.Genero;
import modelo.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO implements CrudDAO<Pelicula> {

    private Connection con;

    public PeliculaDAO(Connection connection ) {
        this.con = connection;
    }

    public List<Pelicula> listarByGenero( long idgenero ) throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        CrudDAO<Genero> generoDAO = new GeneroDAO(con);
        String sql = "SELECT idpelicula, titulo, idgenero, adulto, anyo, visionados FROM peliculas WHERE idgenero = ? ";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, idgenero);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getLong("idpelicula"));
                pelicula.setTitulo(rs.getString("titulo"));
                Genero genero = generoDAO.obtener(rs.getLong("idgenero"));
                pelicula.setGenero(genero);
                pelicula.setAdulto(rs.getBoolean("adulto"));
                pelicula.setAnyo(rs.getInt("anyo"));
                pelicula.setVisionado(rs.getInt("visionados"));

                peliculas.add(pelicula);
            }
        }
        return peliculas;
    }

    @Override
    public List<Pelicula> listar() throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        CrudDAO<Genero> generoDAO = new GeneroDAO(con);
        String sql = "SELECT idpelicula, titulo, idgenero, adulto, anyo, visionados FROM peliculas";
        try (Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getLong("idpelicula"));
                pelicula.setTitulo(rs.getString("titulo"));
                Genero genero = generoDAO.obtener(rs.getLong("idgenero"));
                pelicula.setGenero(genero);
                pelicula.setAdulto(rs.getBoolean("adulto"));
                pelicula.setAnyo(rs.getInt("anyo"));
                pelicula.setVisionado(rs.getInt("visionados"));

                peliculas.add(pelicula);
            }
        }
        return peliculas;
    }

    @Override
    public Pelicula obtener(Long id) throws SQLException {
        Pelicula pelicula = null;
        CrudDAO<Genero> generoDAO = new GeneroDAO(con);
        String sql = "SELECT idpelicula, titulo, idgenero, adulto, anyo, visionados FROM peliculas WHERE idpelicula = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1,id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                pelicula = new Pelicula();
                pelicula.setId(rs.getLong("idpelicula"));
                pelicula.setTitulo(rs.getString("titulo"));
                Genero genero = generoDAO.obtener(rs.getLong("idgenero"));
                pelicula.setGenero(genero);
                pelicula.setAdulto(rs.getBoolean("adulto"));
                pelicula.setAnyo(rs.getInt("anyo"));
                pelicula.setVisionado(rs.getInt("visionados"));
            }
        }
        return pelicula;
    }

    @Override
    public void alta(Pelicula elemento) throws SQLException {
        String sql = "INSERT INTO peliculas( titulo, adulto, anyo, idgenero ) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {     // Para recuperar la ID autogenerada
            pst.setString(1, elemento.getTitulo());
            pst.setBoolean(2, elemento.isAdulto());
            pst.setInt(3, elemento.getAnyo());
            pst.setLong(4, elemento.getGenero().getId());
            // Recuperacion del ID generado
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) elemento.setId(rs.getLong(1));
        }
    }

    @Override
    public void actualizar(Pelicula elemento) throws SQLException {
        String sql = "UPDATE peliculas SET titulo = ?, adulto = ?, anyo = ?, idgenero = ?, visionados = ? WHERE idpelicula = ?";
        try ( PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, elemento.getTitulo());
            pst.setBoolean(2, elemento.isAdulto());
            pst.setInt(3, elemento.getAnyo());
            pst.setLong(4, elemento.getGenero().getId());
            pst.setInt(5, elemento.getVisionado());
            pst.setLong(6, elemento.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void eliminar(Pelicula elemento) throws SQLException {
        try( PreparedStatement pst = con.prepareStatement("DELETE FROM peliculas WHERE idpelicula = ?") ) {
            pst.setLong(1, elemento.getId());
            pst.executeUpdate();
        }
    }
}
