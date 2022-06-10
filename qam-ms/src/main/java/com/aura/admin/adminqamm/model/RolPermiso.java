package com.aura.admin.adminqamm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="rol_permiso")
public @Getter
@Setter
class RolPermiso {
    @EmbeddedId
    private RolPermisoKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRol")
    @JoinColumn(name = "id_rol")
    @JsonBackReference(value="rol-rolpermisos")
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPermiso")
    @JoinColumn(name = "id_permiso")
    @JsonBackReference(value="permiso-rolpermisos")
    private Permiso permiso;

    @Column(name="fecha_registro")
    private Date registro;
}
