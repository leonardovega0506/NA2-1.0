package mx.com.upiicsa.na2.NA210.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "retardo_trabjador")
public class RetradoTrabajadorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_retardo;

    @Column(name = "fecha_retardo",nullable = false)
    private LocalDate fecha_retardo;

    @Column(name = "tiempo_retardo",nullable = false)
    private String tiempo_Retardo;

    @Column(name = "descuento_retardo",nullable = false)
    private double descuento_retardo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajador_id")
    @JsonIgnore
    private TrabajadorModel trabajadorModel;
}
