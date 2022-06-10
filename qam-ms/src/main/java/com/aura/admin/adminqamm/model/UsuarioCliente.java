package com.aura.admin.adminqamm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="usuario_cliente")
public @Getter
@Setter
class UsuarioCliente {

    @EmbeddedId
    private UsuarioClienteKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    @JsonBackReference(value="user-userclients")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCliente")
    @JoinColumn(name = "id_clnt")
    @JsonBackReference(value="clients-userclients")
    private Cliente cliente;

    @Column(name="fecha_registro")
    private Date registro;

}
