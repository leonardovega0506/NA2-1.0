package mx.com.upiicsa.na2.NA210.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = "vacacion_trabajador")
@Entity
public class VacacionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_vacacion;

    private LocalDate fechaCreacion;

    @Column(name = "estatus_vacaciones",nullable = false)
    private String estatus_vacacion;

    @Column(name = "fecha_inicio_vacaciones",nullable = false)
    private LocalDate fecha_inicio;

    @Column(name = "fecha_final_vacaciones",nullable = false)
    private LocalDate fecha_fin;

    @Column(name = "prima_vacacional",nullable = false)
    private double prima_vacacional;

    private String observaciones;

    @Column(name = "cantidad_dias_vacaciones",nullable = false)
    private int cantidad_dias;

    private LocalDate fechaRespuesta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TrabajadorModel trabajadorModel;
}
