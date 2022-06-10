package com.aura.admin.adminqamm.dto.response;

import lombok.Data;

import java.util.Date;

public @Data
class ServiceResponseDto {
    private int idServicio;

    private String nombre;

    private int tipoAmbienteId;
    private String tipoAmbienteNombre;

    private int tipoServicioId;
    private String tipoServicioNombre;

    private String endpoint;

    private Date fechaModificacion;
    private Date fechaRegistro;
    private boolean activo;
}
