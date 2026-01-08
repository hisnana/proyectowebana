package es.cursojava.cursos.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "TB_ALUMNO_ANA",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_ALUMNO_EMAIL", columnNames = "EMAIL")
        }
)
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "EMAIL", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "EDAD", nullable = false)
    private Integer edad;

    // ----- RELACIÓN MUCHOS-AL-UNO CON CURSO -----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "CURSO_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ALUMNO_CURSO")
    )
    private Curso curso;

    public Alumno() {
    }

    // Getters / setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                '}';
    }
}
