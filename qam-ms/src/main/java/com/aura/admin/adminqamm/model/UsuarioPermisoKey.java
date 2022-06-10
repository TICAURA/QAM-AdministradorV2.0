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
class UsuarioPermisoKey implements Serializable {
    @Column(name = "id_usuario")
    private int idUsuario;
    @Column(name = "id_permiso")
    private int idPermiso;

    public UsuarioPermisoKey() {
    }

    public UsuarioPermisoKey(int idUsuario, int idPermiso) {
        this.idUsuario = idUsuario;
        this.idPermiso = idPermiso;
    }
}
