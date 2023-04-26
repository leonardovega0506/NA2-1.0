package mx.com.upiicsa.na2.NA210.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = "tarea_trabajador")
@Entity
public class TareaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Tarea;

    @Column(name = "nombre_tarea")
    private String nombre;

    @Column(name = "fecha_tarea")
    private LocalDate fecha;

    @Column(name = "estatus_tarea")
    private String estatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_trabajador",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TrabajadorModel trabajadorModel;
}
