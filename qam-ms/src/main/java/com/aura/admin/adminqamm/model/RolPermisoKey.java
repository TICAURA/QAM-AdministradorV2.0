package com.aura.admin.adminqamm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public @Getter
@Setter
class RolPermisoKey implements Serializable {

    @Column(name = "id_rol")
    private int idRol;
    @Column(name = "id_permiso")
    private int idPermiso;

    public RolPermisoKey() {
    }

    public RolPermisoKey(int idRol, int idPermiso) {
        this.idRol = idRol;
        this.idPermiso = idPermiso;
    }
}
