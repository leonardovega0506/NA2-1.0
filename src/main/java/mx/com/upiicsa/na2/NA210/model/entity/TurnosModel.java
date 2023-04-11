package mx.com.upiicsa.na2.NA210.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "turno")
public class TurnosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private int id_turno;

    @Column(name = "turno",nullable = false)
    private String turno;

    @Column(name = "cantidad_horas",nullable = false)
    private int cantidad_horas;
}
