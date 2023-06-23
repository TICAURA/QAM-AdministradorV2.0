package com.aura.admin.adminqamm.dto;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;

import lombok.Data;


@Data
public class UsuarioDto {
	
		private int idCargaMasiva;

	    //Persona
	    private String nombre;
	    private String primerApellido;	
	    private String segundoApellido;
	    private String celular;
	    private String email;
	    private Date fechaNacimiento;
	    private String genero;
	    private String cEstado;
	    
	    private String areaPosicion;
	    private String periodicidad;
	    private Double salarioDiario;
	    private String tipoDocumentoId;
	    private String tipoContrato;    
	    private String cuentaNequi;
	    private String numeroDocumentoId;
	    private Integer idTipoCuentaBanco;
	    private Integer idBancoNequi;
	    private Integer idEstatusCarga; 
	    private String numCuentaBanco;
	    private String observacioCarga;
	    
	    
	    
	    

}
