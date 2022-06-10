package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.util.Date;

public @Data class DatosContactoDto {
    private String numClave;
    private String tipoContacto;
    private String valor;
    private Boolean esPrincipal;
    private Boolean esCelularEnrolamiento;
    private Date fchAlta;
    private Integer idTctk;
    private Boolean eliminaMedioDeContacto;
    private Boolean esActivo;
}
