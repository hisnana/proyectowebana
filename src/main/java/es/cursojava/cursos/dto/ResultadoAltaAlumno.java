package es.cursojava.cursos.dto;

import java.util.HashMap;
import java.util.Map;

import es.cursojava.cursos.entity.Alumno;

/**
 * Objeto de resultado que devuelve el servicio:
 * - valido = true si todo OK
 * - alumno = entidad creada (cuando es válido)
 * - errores = mensajes de error por campo
 */
public class ResultadoAltaAlumno {

    private boolean valido;
    private Alumno alumno;
    private Map<String, String> errores = new HashMap<>();

    public boolean isValido() {
        return valido;
    }
    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Map<String, String> getErrores() {
        return errores;
    }
    public void setErrores(Map<String, String> errores) {
        this.errores = errores;
    }
}
