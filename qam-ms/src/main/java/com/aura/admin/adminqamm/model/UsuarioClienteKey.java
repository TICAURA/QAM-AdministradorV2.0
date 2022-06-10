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
class UsuarioClienteKey implements Serializable {

    @Column(name = "id_usuario")
    private int idUsuario;
    @Column(name = "id_clnt")
    private int idCliente;

    public UsuarioClienteKey() {
    }

    public UsuarioClienteKey(int idUsuario, int idCliente) {
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
    }
}
