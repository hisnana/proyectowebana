package es.cursojava.cursos.service;

import es.cursojava.cursos.dao.AlumnoDAO;
import es.cursojava.cursos.dao.CursoDAO;
import es.cursojava.cursos.dto.AlumnoDTO;
import es.cursojava.cursos.dto.ResultadoAltaAlumno;
import es.cursojava.cursos.entity.Alumno;
import es.cursojava.cursos.entity.Curso;
import es.cursojava.utils.UtilidadesHibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AlumnoService {

    private static final Logger log = LoggerFactory.getLogger(AlumnoService.class);

    /**
     * Valida los datos del DTO, y si son correctos, inserta el alumno en BD.
     * Si hay errores, no toca la BD y rellena el mapa de errores.
     */
    public ResultadoAltaAlumno crearAlumno(AlumnoDTO dto) {

        ResultadoAltaAlumno res = new ResultadoAltaAlumno();
        Map<String, String> errores = res.getErrores();

        // 1) Validaciones "en memoria" (sin BD)
        String nombre = limpiar(dto.getNombre());
        String email = limpiar(dto.getEmail());
        String edadStr = limpiar(dto.getEdad());
        String codigoCurso = limpiar(dto.getCodigoCurso());

        // nombre obligatorio y longitud razonable
        if (nombre == null || nombre.isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio");
        } else if (nombre.length() > 100) {
            errores.put("nombre", "El nombre no puede superar 100 caracteres");
        }

        // email obligatorio + formato simple + longitud
        if (email == null || email.isEmpty()) {
            errores.put("email", "El email es obligatorio");
        } else if (email.length() > 255) {
            errores.put("email", "El email es demasiado largo");
        } else if (!email.matches("^[^@]+@[^@]+\\.[^@]+$")) {
            errores.put("email", "El formato de email no es válido");
        }

        // edad: número entero entre 0 y 120
        Integer edad = null;
        if (edadStr == null || edadStr.isEmpty()) {
            errores.put("edad", "La edad es obligatoria");
        } else {
            try {
                edad = Integer.parseInt(edadStr);
                if (edad < 0 || edad > 120) {
                    errores.put("edad", "La edad debe estar entre 0 y 120");
                }
            } catch (NumberFormatException e) {
                errores.put("edad", "La edad debe ser un número entero");
            }
        }

        // código de curso obligatorio
        if (codigoCurso == null || codigoCurso.isEmpty()) {
            errores.put("codigoCurso", "El código de curso es obligatorio");
        }

        // 2) Validaciones que necesitan BD (curso existe, email único)
        Session session = null;
        Transaction tx = null;

        try {
            // si ya hay errores básicos, ni abrimos la sesión
            if (!errores.isEmpty()) {
                return res;
            }

            session = UtilidadesHibernate.abrirSesion();
            tx = session.beginTransaction();

            AlumnoDAO alumnoDAO = new AlumnoDAO(session);
            CursoDAO cursoDAO = new CursoDAO(session);

            // validar que el curso existe
            Curso curso = cursoDAO.obtenerPorCodigo(codigoCurso);
            if (curso == null) {
                errores.put("codigoCurso", "No existe ningún curso con código " + codigoCurso);
            }

            // validar que el email no esté repetido
            if (!errores.containsKey("email")) {
                Alumno existente = alumnoDAO.obtenerAlumnoPorEmail(email);
                if (existente != null) {
                    errores.put("email", "Ya existe un alumno con ese email");
                }
            }

            // si hay errores de BD, hacemos rollback y salimos
            if (!errores.isEmpty()) {
                if (tx != null) tx.rollback();
                return res;
            }

            // 3) Crear entidad y guardar en la BD
            Alumno alumno = new Alumno();
            alumno.setNombre(nombre);
            alumno.setEmail(email);
            alumno.setEdad(edad);
            alumno.setCurso(curso); // relación ManyToOne

            alumnoDAO.guardarAlumno(alumno);

            tx.commit();

            res.setValido(true);
            res.setAlumno(alumno);

        } catch (Exception e) {
            log.error("Error al crear alumno", e);
            if (tx != null) {
                try { tx.rollback(); } catch (Exception ex) { log.error("Error en rollback", ex); }
            }
            errores.put("general", "Se ha producido un error al guardar el alumno");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return res;
    }

    private String limpiar(String s) {
        return s != null ? s.trim() : null;
    }
}
