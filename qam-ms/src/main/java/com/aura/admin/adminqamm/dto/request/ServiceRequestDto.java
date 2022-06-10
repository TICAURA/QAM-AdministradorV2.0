package com.aura.admin.adminqamm.dto.request;

import lombok.Data;

import java.util.Date;

public @Data
class ServiceRequestDto {
    private int idServicio;

    private String nombre;

    private int tipoAmbienteId;
    private String tipoAmbienteNombre;

    private int tipoServicioId;
    private String tipoServicioNombre;

    private String endpoint;

    private boolean activo;
}
