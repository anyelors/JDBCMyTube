package modelo;

import java.time.LocalDate;

public class Usuario {

    private long id;
    private String dni;
    private String nombre;
    private LocalDate nacimiento;
    private String mail;

    public Usuario() {

    }

    public Usuario(String dni, String nombre, LocalDate nacimiento, String mail) {
        this.dni = dni;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.mail = mail;
    }

    public Usuario(long id, String dni, String nombre, LocalDate nacimiento, String mail) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Usuario [" +
                "Id = " + id +
                ", Dni = '" + dni + '\'' +
                ", Nombre = '" + nombre + '\'' +
                ", Nacimiento = " + nacimiento +
                ", Mail = '" + mail + '\'' +
                ']';
    }
}
