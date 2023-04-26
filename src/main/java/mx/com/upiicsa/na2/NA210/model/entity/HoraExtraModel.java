package mx.com.upiicsa.na2.NA210.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "hora_extra")
public class HoraExtraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_horaExtra;

    @Column(name = "fecha_horaExtra",nullable = false)
    private LocalDate fechaHoraExtra;

    @Column(name = "cantidad_horasExtra",nullable = false)
    private int cantidad_horas;

    @Column(name = "costo_hora",nullable = false)
    private double costo_hora;

    @Column(name = "aumento_total")
    private double aumento_total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TrabajadorModel trabajadorModel;
}
