package es.cursojava.cursos;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.cursojava.cursos.dto.AulaDTO;
import es.cursojava.cursos.dto.CursoDTO;
import es.cursojava.cursos.exception.AulaYaAsignadaException;
import es.cursojava.cursos.service.CursoService;
import es.cursojava.utils.UtilidadesHibernate;

public class AppCursoAulas {
    private static final Logger log = LoggerFactory.getLogger(AppCursos.class);


    public static void main(String[] args) {
        Session session = null;
        Transaction tx = null;
        
        

        try {
            session = UtilidadesHibernate.abrirSesion();
            tx = session.beginTransaction();

            CursoService cursoService = new CursoService(session);

            // 1) Crear DTOs de curso y aula
            CursoDTO cursoDTO = new CursoDTO();
            cursoDTO.setCodigo("JAVA-AULA-1");
            cursoDTO.setNombre("Java con Aula");
            cursoDTO.setDescripcion("Curso de Java con aula asignada");
            cursoDTO.setHorasTotales(80);
            cursoDTO.setActivo(true);
            cursoDTO.setNivel("Básico");
            cursoDTO.setCategoria("Programación");
            cursoDTO.setPrecio(new BigDecimal("250.00"));
            cursoDTO.setFechaInicio(LocalDate.of(2025, 1, 10));
            cursoDTO.setFechaFin(LocalDate.of(2025, 3, 10));

            AulaDTO aulaDTO = new AulaDTO();
            aulaDTO.setCodigoAula(101);
            aulaDTO.setCapacidad(20);
            aulaDTO.setUbicacion("Planta 1");

            CursoDTO cursoGuardado = null;
            // 2) Crear curso + aula a la vez
            try {
                cursoGuardado =
                        cursoService.crearCursoConAula(cursoDTO, aulaDTO);
                log.info("Curso creado con id {}", cursoGuardado.getId());
            } catch (AulaYaAsignadaException e) {
                // ESTE es el control del error que estás buscando
                log.warn("No se ha creado el curso porque: {}", e.getMessage());
                // aquí decides: seguir con otros cursos, pedir otro aula, etc.
            }

            

            // 3) Confirmamos la transacción
            tx.commit();

            // 4) Nueva sesión para simular otra petición que lee
            session = UtilidadesHibernate.abrirSesion();
            CursoService servicioLectura = new CursoService(session);
            
            if (cursoGuardado == null) {
                log.warn("Curso no creado por aula ya asignada");
            } else {
                CursoDTO cursoLeido = servicioLectura.obtenerCursoConAula(cursoGuardado.getId());

                log.info("Curso leído: " + cursoLeido.getNombre());
                if (cursoLeido.getAula() != null) {
                    log.info("Aula: " + cursoLeido.getAula().getCodigoAula()
                            + " (" + cursoLeido.getAula().getUbicacion() + "), capacidad "
                            + cursoLeido.getAula().getCapacidad());
                } else {
                    log.info("El curso no tiene aula asignada.");
                }
            }

 

        } catch (Exception e) {
            if (tx != null) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
