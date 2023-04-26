package mx.com.upiicsa.na2.NA210.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "queja_aclaracion")
public class QuejasAclaracionesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_queja;

    @Column(name = "fecha_queja",nullable = false)
    private LocalDate fechaQueja;

    @Column(name = "tipo_queja",nullable = false)
    private String tipo_Queja;

    @Column(name = "cuerpo_queja",nullable = false)
    private String cuerpo_queja;

    @Column(name = "estatus_queja",nullable = false)
    private String estatus_queja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TrabajadorModel trabajadorModel;
}
