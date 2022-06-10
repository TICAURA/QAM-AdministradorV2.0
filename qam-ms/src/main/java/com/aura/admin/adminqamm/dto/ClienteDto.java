package com.aura.admin.adminqamm.dto;

import lombok.Data;

public @Data class ClienteDto {

    private int idClient;
    private String curp;
    private String rfc;
    private String razon;
    private String email;
    private int dispersorId;
    private int facturadorId;
    private String dispersorName;
    private String facturadorName;
    private Integer periodoFactura;
    private String mensajeEmailRecuperarContra;
    private String mensajeEmailFactura;
    private String emailSender;
    private Integer servicioId;
    private Integer interfazId;
    private String servicioName;
    private String interfazName;
    private String registroPatronal;
    private String subRazonSocial;

}
