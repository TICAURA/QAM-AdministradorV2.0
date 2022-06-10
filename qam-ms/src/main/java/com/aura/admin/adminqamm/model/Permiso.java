package com.aura.admin.adminqamm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="c_permiso_seguridad")
public @Getter @Setter class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_permiso")
    private int id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    @Column(name = "ind_activo")
    private boolean activo;
}
