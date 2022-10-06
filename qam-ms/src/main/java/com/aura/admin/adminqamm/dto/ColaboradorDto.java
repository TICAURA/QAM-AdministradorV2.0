package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Date fchPago;
    
    private Date FchCorteIncidencias;
    
    private String periodicidad;

    private Date fchFin;

    private Double salarioDiarioReal;

    private Double salarioNetoPeriodo;

    private Double salarioDiarioIntegrado;

    private Double distribucionImssComplemento;
    
    private String numeroDocumento;
    
    private String descError;
    
    //Relacion
    private PersonaDto personaDto;
    
    private CuentaBancoDto cuentaBancoDto;
    
    private ClienteDto clienteDto;
    
    private List<DatosContactoDto> datosContactoDto = new ArrayList<DatosContactoDto>();


}
