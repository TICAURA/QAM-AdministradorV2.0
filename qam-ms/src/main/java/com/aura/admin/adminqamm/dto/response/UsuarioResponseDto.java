package com.aura.admin.adminqamm.dto.response;

import com.aura.admin.adminqamm.model.Usuario;
import lombok.Data;

import java.util.Date;

public @Data class UsuarioResponseDto {
    private int userId;
    private String name;
    private Date fechaModificacion;
    private Date fechaRegistro;
    private boolean activo;

    private String user;
    private Date ultimoLogin;
    private Date ultimaActividad;

    public UsuarioResponseDto build(Usuario usuario){
        this.userId = usuario.getUserId();
        this.name = usuario.getName();
        this.fechaModificacion = usuario.getFechaModificacion();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.activo = usuario.isActivo();
        this.user = usuario.getUser();
        this.ultimoLogin = usuario.getUltimoLogin();
        this.ultimaActividad = usuario.getUltimaActividad();
        return this;
    }

}
