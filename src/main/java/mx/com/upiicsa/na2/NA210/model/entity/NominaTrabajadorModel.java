package mx.com.upiicsa.na2.NA210.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "nomina_trabajador")
public class NominaTrabajadorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_nomina;

    @Column(name = "fecha_nomina_trabajador", nullable = false)
    private LocalDate fechaNomina;

    @Column(name = "nomina_trabajador",nullable = false)
    private double nomina_trabajador;

    @Column(name = "isr_trabajador",nullable = false)
    private double isr;

    @Column(name = "descuento_retardo",nullable = false)
    private double descuento_retardo;


    @Column(name = "cantidad_retardos",nullable = false)
    private int retardo_trabajador;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador",nullable = false)
    private TrabajadorModel trabajadorModel;
}
