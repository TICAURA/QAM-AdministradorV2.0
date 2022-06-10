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
class UsuarioRolKey implements Serializable {
    @Column(name = "id_usuario")
    private int idUsuario;
    @Column(name = "id_rol")
    private int idRol;

    public UsuarioRolKey() {
    }

    public UsuarioRolKey(int idUsuario, int idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }
}
