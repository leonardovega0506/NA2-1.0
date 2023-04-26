package mx.com.upiicsa.na2.NA210.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "renuncia_trabajador")
public class RenunciaTrabajadorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_renuncia;

    @Column(name = "fecha_renuncia",nullable = false)
    private LocalDate fecha_renuncia;

    @Column(name = "motivo_renuncia",nullable = false)
    private String motivo_renuncia;

    @Column(name = "finiquito",nullable = false)
    private double finiquito;

    private int tiempoTrabajado;

    private String estatusRenuncia;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TrabajadorModel trabajadorModel;
}
