package mx.com.upiicsa.na2.NA210.model.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "administrador")
public class AdministradorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_administrador;

    @Column(name = "usuario_Administrador")
    private String usuario;

    @Column(name = "nombre_Administrador")
    private String nombre;
}
