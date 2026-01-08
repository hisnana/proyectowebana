package es.cursojava.cursos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cursojava.cursos.dao.CursoDAO;
import es.cursojava.cursos.entity.Curso;
import es.cursojava.utils.UtilidadesHibernate;

public class AppCursos {
	private static final Logger log = LoggerFactory.getLogger(CursoDAO.class);


    public static void main(String[] args) {
        insertarCursosEjemplo();
        log.info("---- TODOS LOS CURSOS ----");
        mostrarTodosLosCursos();
        log.info("---- CURSOS ACTIVOS ----");
        mostrarCursosActivos();
    }
    

    // Insertar 3 cursos de ejemplo
    public static void insertarCursosEjemplo() {
        Session session = null;
        Transaction tx = null;

        try {
            // Abrimos sesión
            session = UtilidadesHibernate.abrirSesion();
            // Iniciamos transacción
            tx = session.beginTransaction();

            // --------- Curso 1 ---------
            Curso c1 = new Curso();
            c1.setCodigo("JAVA-BASICO");
            c1.setNombre("Curso de Java Básico");
            c1.setDescripcion("Introducción a la programación en Java");
            c1.setHorasTotales(80);
            c1.setActivo(true);
            c1.setNivel("Básico");
            c1.setCategoria("Programación");
            c1.setPrecio(new BigDecimal("299.99"));
            c1.setFechaInicio(LocalDate.of(2025, 1, 10));
            c1.setFechaFin(LocalDate.of(2025, 3, 10));

            // --------- Curso 2 ---------
            Curso c2 = new Curso();
            c2.setCodigo("JAVA-AVANZADO");
            c2.setNombre("Java Avanzado y Spring");
            c2.setDescripcion("Java, Hibernate y Spring Framework");
            c2.setHorasTotales(120);
            c2.setActivo(true);
            c2.setNivel("Avanzado");
            c2.setCategoria("Programación");
            c2.setPrecio(new BigDecimal("499.00"));

            // --------- Curso 3 ---------
            Curso c3 = new Curso();
            c3.setCodigo("BIGDATA-INTRO");
            c3.setNombre("Introducción al Big Data");
            c3.setDescripcion("Conceptos básicos de Big Data y ecosistema Hadoop");
            c3.setHorasTotales(60);
            c3.setActivo(false); // este será el inactivo
            c3.setNivel("Intermedio");
            c3.setCategoria("Big Data");
            c3.setPrecio(new BigDecimal("350.00"));

            // Persistimos
            session.persist(c1);
            session.persist(c2);
            session.persist(c3);

            // Confirmamos la transacción
            tx.commit();

        } catch (Exception e) {
            // Si algo falla y la transacción existe, hacemos rollback
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception exRollback) {
                    System.err.println("Error al hacer rollback: " + exRollback.getMessage());
                }
            }
            e.printStackTrace();
        } finally {
            // Cerramos la sesión si se abrió
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // Mostrar todos los cursos
    public static void mostrarTodosLosCursos() {
        Session session = null;
        try {
            session = UtilidadesHibernate.abrirSesion();

            List<Curso> cursos = session
                    .createQuery("FROM Curso", Curso.class)
                    .getResultList();

            cursos.forEach(System.out::println);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // Mostrar solo cursos activos
    public static void mostrarCursosActivos() {
        Session session = null;
        try {
            session = UtilidadesHibernate.abrirSesion();

            List<Curso> cursosActivos = session
                    .createQuery("FROM Curso c WHERE c.activo = true", Curso.class)
                    .getResultList();

            cursosActivos.forEach(System.out::println);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
