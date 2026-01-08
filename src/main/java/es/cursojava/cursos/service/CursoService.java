package es.cursojava.cursos.service;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cursojava.cursos.dao.AulaDAO;
import es.cursojava.cursos.dao.CursoDAO;
import es.cursojava.cursos.dto.AulaDTO;
import es.cursojava.cursos.dto.CursoDTO;
import es.cursojava.cursos.entity.Aula;
import es.cursojava.cursos.entity.Curso;
import es.cursojava.cursos.exception.AulaYaAsignadaException;

public class CursoService {
	private static final Logger log = LoggerFactory.getLogger(CursoService.class);

    private final Session session;
    private final CursoDAO cursoDAO;
    private final AulaDAO aulaDAO;


    public CursoService(Session session) {
        this.session = session;
        this.cursoDAO = new CursoDAO(session);
        this.aulaDAO = new AulaDAO(session);
    }

    // --------- MAPEOS DTO <-> ENTITY ---------

    private Aula mapAulaDTOToEntity(AulaDTO dto) {
        if (dto == null) return null;
        Aula aula = new Aula();
        aula.setId(dto.getId());
        aula.setCodigoAula(dto.getCodigoAula());
        aula.setCapacidad(dto.getCapacidad());
        aula.setUbicacion(dto.getUbicacion());
        return aula;
    }

    private AulaDTO mapAulaToDTO(Aula aula) {
        if (aula == null) return null;
        AulaDTO dto = new AulaDTO();
        dto.setId(aula.getId());
        dto.setCodigoAula(aula.getCodigoAula());
        dto.setCapacidad(aula.getCapacidad());
        dto.setUbicacion(aula.getUbicacion());
        return dto;
    }

    private Curso mapCursoDTOToEntity(CursoDTO dto) {
        if (dto == null) return null;
        Curso curso = new Curso();
        curso.setId(dto.getId());
        curso.setCodigo(dto.getCodigo());
        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setHorasTotales(dto.getHorasTotales());
        curso.setActivo(dto.isActivo());
        curso.setNivel(dto.getNivel());
        curso.setCategoria(dto.getCategoria());
        curso.setPrecio(dto.getPrecio());
        curso.setFechaInicio(dto.getFechaInicio());
        curso.setFechaFin(dto.getFechaFin());
        // fechaCreacion la pone @PrePersist
        return curso;
    }

    private CursoDTO mapCursoToDTO(Curso curso) {
        if (curso == null) return null;
        CursoDTO dto = new CursoDTO();
        dto.setId(curso.getId());
        dto.setCodigo(curso.getCodigo());
        dto.setNombre(curso.getNombre());
        dto.setDescripcion(curso.getDescripcion());
        dto.setHorasTotales(curso.getHorasTotales());
        dto.setActivo(curso.isActivo());
        dto.setNivel(curso.getNivel());
        dto.setCategoria(curso.getCategoria());
        dto.setPrecio(curso.getPrecio());
        dto.setFechaInicio(curso.getFechaInicio());
        dto.setFechaFin(curso.getFechaFin());
        dto.setAula(mapAulaToDTO(curso.getAula()));
        return dto;
    }

    // --------- VALIDACIONES ---------

    private void validarAula(Aula aula) {
        if (aula.getCapacidad() == null || aula.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad del aula debe ser mayor que 0");
        }
    }

    private void validarAulaNoAsignada(Long aulaId) {
        Long count = session.createQuery(
                        "SELECT COUNT(c) FROM Curso c WHERE c.aula.id = :aulaId",
                        Long.class
                )
                .setParameter("aulaId", aulaId)
                .uniqueResult();

        if (count != null && count > 0) {
            throw new AulaYaAsignadaException("El aula ya está asignada a otro curso");
        }
    }

    // --------- MÉTODOS DEL ENUNCIADO ---------
 // ... (métodos de mapeo DTO <-> entity y validaciones que ya vimos)

    public CursoDTO crearCursoConAula(CursoDTO cursoDTO, AulaDTO aulaDTO) {

        // 1) ¿Existe ya un aula con ese código?
        Aula aula = null;
        if (aulaDTO != null && aulaDTO.getCodigoAula() != null) {
            aula = aulaDAO.buscarPorCodigo(aulaDTO.getCodigoAula());
        }

        if (aula != null) {
            // Ya existe → la reutilizamos
            log.warn("El aula con código {} ya existe, se reutiliza en lugar de crear una nueva",
                    aula.getCodigoAula());
            validarAula(aula);
            // También aquí podrías comprobar que no esté ya asignada a otro curso:
            validarAulaNoAsignada(aula.getId());
        } else {
            // No existe → se crea nueva
            aula = mapAulaDTOToEntity(aulaDTO);
            validarAula(aula);   // capacidad > 0, etc.
            // No hace falta guardar aquí: cascade=ALL desde Curso se encargará
        }

        // 2) Crear entidad Curso desde el DTO
        Curso curso = mapCursoDTOToEntity(cursoDTO);
        curso.setAula(aula);

        // 3) Guardar curso (y aula nueva si la hubiera)
        cursoDAO.guardarCurso(curso);

        return mapCursoToDTO(curso);
    }
    /**
     * Asigna un aula ya existente a un curso ya existente.
     */
    public void asignarAula(Long cursoId, Long aulaId) {
        Curso curso = cursoDAO.obtenerCursoPorId(cursoId);
        if (curso == null) {
            throw new IllegalArgumentException("No existe curso con id " + cursoId);
        }

        Aula aula = session.get(Aula.class, aulaId);
        if (aula == null) {
            throw new IllegalArgumentException("No existe aula con id " + aulaId);
        }

        validarAula(aula);
        validarAulaNoAsignada(aulaId);

        curso.setAula(aula);
        cursoDAO.actualizarCurso(curso);
    }

    public CursoDTO obtenerCursoConAula(Long cursoId) {
        Curso curso = cursoDAO.obtenerCursoConAula(cursoId);
        return mapCursoToDTO(curso);
    }
}
