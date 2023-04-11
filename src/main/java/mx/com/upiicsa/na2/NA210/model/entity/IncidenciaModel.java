package mx.com.upiicsa.na2.NA210.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "inicidencia")
public class IncidenciaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_incidencia;

    @Column(name = "tipo_incidencia")
    private String tipo_incidencia;

    @Column(name = "nombre_incidencia")
    private String nombre_incidencia;

    @Column(name = "evidencia_incidencia")
    private String evidencia_incidencia;

    @Column(name = "fecha_incidencia")
    private LocalDate fecha_incidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador",nullable = false)
    private TrabajadorModel trabajadorModel;
}
