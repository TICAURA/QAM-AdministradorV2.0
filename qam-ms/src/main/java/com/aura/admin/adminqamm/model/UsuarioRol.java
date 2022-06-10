package com.aura.admin.adminqamm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="usuario_rol")
public @Getter
@Setter
class UsuarioRol {
    @EmbeddedId
    private UsuarioRolKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    @JsonBackReference(value="user-userroles")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRol")
    @JoinColumn(name = "id_rol")
    @JsonBackReference(value="rol-userroles")
    private Rol rol;

    @Column(name="fecha_registro")
    private Date registro;
}
