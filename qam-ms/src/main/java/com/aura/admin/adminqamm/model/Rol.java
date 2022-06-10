package com.aura.admin.adminqamm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="c_rol_seguridad")
public @Getter @Setter class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_rol")
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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "rol")
    @JsonBackReference(value="rol-rolpermisos")
    Set<RolPermiso> rolPermisos;


}
