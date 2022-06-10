package com.aura.admin.adminqamm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="usuario_permiso")
public @Getter
@Setter
class UsuarioPermiso {
    @EmbeddedId
    private UsuarioPermisoKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    @JsonBackReference(value="user-userpermissions")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPermiso")
    @JoinColumn(name = "id_permiso")
    @JsonBackReference(value="permission-userpermissions")
    private Permiso permiso;

    @Column(name="fecha_registro")
    private Date registro;
}
