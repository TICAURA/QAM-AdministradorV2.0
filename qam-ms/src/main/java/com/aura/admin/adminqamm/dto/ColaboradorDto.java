package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.util.Date;

public @Data
class ColaboradorDto {


    private int personaId;

    //Persona
    private String curp;

    private String rfc;

    private String nombre;


    private String apellidoPat;

    private String apellidoMat;

    private Date fechaNacimiento;

    private String emailCorporativo;

    private String genero;

    private String nss;

    private Date fechaDeAlta;

    private boolean activo;




    private Date fechaIngreso;

    private Integer clienteId;

    private Long claveColaborador;



    //colaborador
    private String periodicidad;

    private Date fchFin;

    private Double salarioDiarioReal;

    private Double salarioNetoPeriodo;

    private Double salarioDiarioIntegrado;

    private Double distribucionImssComplemento;



}
