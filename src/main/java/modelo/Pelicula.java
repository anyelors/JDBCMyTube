package modelo;

public class Pelicula {

    private Long id;
    private String titulo;
    private Genero genero;
    private boolean adulto;
    private int anyo;
    private int visionado;

    public Pelicula() {
    }

    public Pelicula(String titulo, Genero genero, boolean adulto, int anyo, int visionado) {
        this.titulo = titulo;
        this.genero = genero;
        this.adulto = adulto;
        this.anyo = anyo;
        this.visionado = visionado;
    }

    public Pelicula(Long id, String titulo, Genero genero, boolean adulto, int anyo, int visionado) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.adulto = adulto;
        this.anyo = anyo;
        this.visionado = visionado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public boolean isAdulto() {
        return adulto;
    }

    public void setAdulto(boolean adulto) {
        this.adulto = adulto;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public int getVisionado() {
        return visionado;
    }

    public void setVisionado(int visionado) {
        this.visionado = visionado;
    }

    @Override
    public String toString() {
        return "Pelicula [" +
                "Id = " + id +
                ", Titulo = '" + titulo + '\'' +
                ", Genero = " + genero +
                ", Adulto = " + adulto +
                ", Año = " + anyo +
                ", Visionado = " + visionado +
                ']';
    }
}
