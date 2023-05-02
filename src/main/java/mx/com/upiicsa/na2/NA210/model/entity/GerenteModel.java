package mx.com.upiicsa.na2.NA210.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import mx.com.upiicsa.na2.NA210.model.auth.UsuarioModel;

import javax.persistence.*;

@Data
@Entity
@Table(name = "gerente")
public class GerenteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gerente")
    private long id;

    @Column(name = "numero_gerente",nullable = false)
    private long numero_gerente;

    @Column(name = "nombre_gerente",nullable = false)
    private String nombre_gerente;

    @Column(name = "apellidoP_gerente",nullable = false)
    private String apellidoP_gerente;

    @Column(name = "apellidoM_gerente",nullable = false)
    private String apllidoM_gerente;

    @Column(name = "sueldo_gerente",nullable = false)
    private double sueldo;

    @Column(name ="estatus_gerente",nullable = false)
    private String estaus;

    @Column(name = "celular_gerente",nullable = false)
    private String celular;

    @Column(name = "correo_electronico_gerente",nullable = false)
    private String correo_electronico;

    @Column(name = "puesto_gerente")
    private String puesto;


    @OneToOne
    private UsuarioModel usuario;
}
