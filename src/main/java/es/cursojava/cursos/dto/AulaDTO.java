package es.cursojava.cursos.dto;

public class AulaDTO {

    private Long id;
    private Integer codigoAula;
    private Integer capacidad;
    private String ubicacion;

    public AulaDTO() {}

    public AulaDTO(Long id, Integer codigoAula, Integer capacidad, String ubicacion) {
        this.id = id;
        this.codigoAula = codigoAula;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoAula() {
        return codigoAula;
    }

    public void setCodigoAula(Integer codigoAula) {
        this.codigoAula = codigoAula;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
