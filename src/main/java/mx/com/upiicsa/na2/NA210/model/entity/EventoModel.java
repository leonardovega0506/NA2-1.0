package mx.com.upiicsa.na2.NA210.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "eventos")
public class EventoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fecha_evento",nullable = false)
    private LocalDate fechEvento;

    @Column(name = "tipo_evento",nullable = false)
    private String tipo_evento;

    @Column(name = "titulo_evento",nullable = false)
    private String titulo_evento;

    @Column(name = "cuerpo_evento",nullable = false)
    private String cuerpo_evento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_gerente", nullable = false)
    private GerenteModel gerenteModel;

}
