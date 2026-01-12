package es.cursojava.cursos.dto;

/**
 * DTO que representa los datos del formulario de alumno.
 * Ojo: los tipos son String porque vienen del HTML;
 * el Service ya se encargará de parsear/validar.
 */
public class AlumnoDTO {

    private String nombre;
    private String email;
    private String edad;
    private String codigoCurso;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEdad() {
        return edad;
    }
    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }
    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }
}
