package es.cursojava.cursos.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cursojava.cursos.entity.Alumno;
import es.cursojava.cursos.entity.Aula;

/**
 * DAO de la entidad Alumno.
 * Encapsula las operaciones de acceso a datos usando Hibernate.
 *
 * Igual que en CursoDAO:
 *  - NO abre/cierra la sesión ni gestiona transacciones.
 *  - Eso lo hace la capa superior (servicio / main).
 */
public class AlumnoDAO {

    private static final Logger log = LoggerFactory.getLogger(AlumnoDAO.class);

    private final Session session;

    public AlumnoDAO(Session session) {
        this.session = session;
    }

    // ----------------- CRUD BÁSICO -----------------

    /**
     * Guarda un alumno nuevo en la base de datos (INSERT).
     */
    public void guardarAlumno(Alumno alumno) {
        log.debug("Guardando alumno con email {}", alumno.getEmail());
        session.persist(alumno);
    }

    /**
     * Actualiza un alumno existente.
     */
    public void actualizarAlumno(Alumno alumno) {
        log.debug("Actualizando alumno con id {}", alumno.getId());
        session.merge(alumno); // o update, según cómo lo uséis en el curso
    }

    /**
     * Busca un alumno por su ID (PK).
     */
    public Alumno obtenerAlumnoPorId(Long id) {
        log.debug("Buscando alumno por id {}", id);
        return session.get(Alumno.class, id);
    }

    /**
     * Elimina un alumno (objeto ya cargado).
     */
    public void eliminarAlumno(Alumno alumno) {
        if (alumno == null) {
            log.warn("Intento de eliminar alumno nulo");
            return;
        }
        log.debug("Eliminando alumno con id {}", alumno.getId());
        session.remove(alumno);
    }

    /**
     * Elimina un alumno por su id.
     */
    public void eliminarAlumnoPorId(Long id) {
        Alumno alumno = obtenerAlumnoPorId(id);
        if (alumno != null) {
            eliminarAlumno(alumno);
        } else {
            log.warn("No se encontró alumno con id {} para eliminar", id);
        }
    }

    /**
     * Devuelve todos los alumnos.
     */
    public List<Alumno> obtenerTodosLosAlumnos() {
        log.debug("Obteniendo todos los alumnos");
        return session
                .createQuery("FROM Alumno", Alumno.class)
                .list();
    }

    // ----------------- BÚSQUEDAS ÚTILES -----------------

    /**
     * Busca un alumno por email (debería ser único).
     */
    public Alumno obtenerAlumnoPorEmail(String email) {
        log.debug("Buscando alumno por email {}", email);
        return session
                .createQuery("FROM Alumno a WHERE a.email = :email", Alumno.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    /**
     * Devuelve la lista de alumnos de un curso concreto.
     */
    public List<Alumno> obtenerAlumnosPorCursoId(Long cursoId) {
        log.debug("Obteniendo alumnos del curso con id {}", cursoId);
        return session
                .createQuery(
                        "FROM Alumno a WHERE a.curso.id = :cursoId",
                        Alumno.class
                )
                .setParameter("cursoId", cursoId)
                .list();
    }

    /**
     * Cuenta cuántos alumnos tiene un curso (útil para controles de capacidad de aula, etc.).
     */
    public long contarAlumnosPorCursoId(Long cursoId) {
        log.debug("Contando alumnos del curso con id {}", cursoId);
        Long count = session
                .createQuery(
                        "SELECT COUNT(a) FROM Alumno a WHERE a.curso.id = :cursoId",
                        Long.class
                )
                .setParameter("cursoId", cursoId)
                .uniqueResult();
        return count != null ? count : 0L;
    }
    
    /**
     * Devuelve el Aula en la que está matriculado el alumno cuyo nombre coincide.
     * Asumimos que el nombre es único en este ejercicio (alumno100).
     */
    public Aula obtenerAulaDeAlumnoPorNombre(String nombreAlumno) {
        log.debug("Buscando aula del alumno {}", nombreAlumno);

        return session.createQuery(
                        "SELECT al.curso.aula FROM Alumno al " +
                        "WHERE al.nombre = :nombre",
                        Aula.class
                )
                .setParameter("nombre", nombreAlumno)
                .uniqueResult();
    }
    
    
    
    /**
     * Devuelve el alumno con su curso y aula cargados (JOIN FETCH),
     * buscando por nombre.
     */
    public Alumno obtenerAlumnoConCursoYAulaPorNombre(String nombreAlumno) {
        log.debug("Buscando alumno (con curso y aula) por nombre {}", nombreAlumno);

        return session.createQuery(
                        "SELECT al FROM Alumno al " +
                        "JOIN FETCH al.curso c " +
                        "JOIN FETCH c.aula a " +
                        "WHERE al.nombre = :nombre",
                        Alumno.class
                )
                .setParameter("nombre", nombreAlumno)
                .uniqueResult();
    }
    
    //Para practicar el acceso a todo mediante objeto con relacion bidireccional
	/**	
	 * Devuelve el alumno por su nombre.
	 */
   
    public Alumno obtenerAlumnoPorNombre(String nombreAlumno) {
        return session.createQuery(
                "FROM Alumno al WHERE al.nombre = :nombre",
                Alumno.class
            )
            .setParameter("nombre", nombreAlumno)
            .uniqueResult();
    }

}
