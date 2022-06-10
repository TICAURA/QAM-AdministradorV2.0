package com.aura.admin.adminqamm.dto.request;

import lombok.Data;


public @Data class UsuarioRequestDto {
    private int userId;
    private String name;

    private boolean activo;

    private String user;
    private String password;


}
