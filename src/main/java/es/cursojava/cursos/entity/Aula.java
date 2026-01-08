package es.cursojava.cursos.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "TB_AULA_ANA",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_AULA_CODIGO", columnNames = "CODIGO_AULA")
        }
)
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // Código de aula, único
    @Column(name = "CODIGO_AULA", nullable = false, length = 20, unique = true)
    private Integer codigoAula;

    // Capacidad (nº de alumnos)
    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    // Ubicación física
    @Column(name = "UBICACION", length = 100)
    private String ubicacion;
    
    @OneToOne(mappedBy = "aula")
    private Curso curso;

    public Aula() {}

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

    @Override
    public String toString() {
        return "Aula{" +
                "id=" + id +
                ", codigoAula='" + codigoAula + '\'' +
                ", capacidad=" + capacidad +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
