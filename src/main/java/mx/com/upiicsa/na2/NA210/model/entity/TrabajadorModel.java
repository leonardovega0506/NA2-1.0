package mx.com.upiicsa.na2.NA210.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import mx.com.upiicsa.na2.NA210.model.auth.UsuarioModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "trabajador")
public class TrabajadorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajador",nullable = false)
    private Long id;

    @Column(name = "numero_trabajador",nullable = false)
    private Long numero_trabajador;

    @Column(name="nombre_trabajador",nullable = false)
    private String nombre_trabajador;

    @Column(name = "apellidoP_trabajor",nullable = false)
    private String apellidoP_trabajador;

    private String rfc;

    private String nss;

    private String curp;

    @Column(name = "apellidoM_trabajador",nullable = false)
    private String apellidoM_trabajador;

    @Column(name = "sueldo_trabajador",nullable = false)
    private double sueldo;

    @Column(name = "estatus_trabajador",nullable = false)
    private String estatus;

    private LocalDate fechaIngreso;

    @Column(name = "celular_trabajador",nullable = false)
    private String celular;

    @Column(name = "correo_electronico_trabajador",nullable = false)
    private String correo_electronico;

    @Column(name = "puesto_trabajador",nullable = false)
    private String puesto;

    @JsonIgnore
    @OneToMany(mappedBy = "trabajadorModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<TareaModel> tareas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trabajadorModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<RetradoTrabajadorModel> retardos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trabajadorModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<QuejasAclaracionesModel> listaQuejas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trabajadorModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<HoraExtraModel> listaHorasExtra = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trabajadorModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<IncidenciaModel> listaIncidencias = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trabajadorModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<NominaTrabajadorModel> listaNominas = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "trabajadorModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private RenunciaTrabajadorModel renuncia;

    @JsonIgnore
    @OneToMany(mappedBy = "trabajadorModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<VacacionModel> listaVacaciones = new HashSet<>();

    @OneToOne
    private UsuarioModel usuario;
}

