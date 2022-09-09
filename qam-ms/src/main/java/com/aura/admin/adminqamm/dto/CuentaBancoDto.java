/**
 * 
 */
package com.aura.admin.adminqamm.dto;

import java.util.Date;

import lombok.Data;

/**
 * @author Cesar Agustin Soto
 *
 */
@Data
public class CuentaBancoDto {

	private Integer banco;
	private String descripcionBanco;
	private String cuenta;
	private String clabe;
	private String descripcion;
	private String compartimiento;
	private Long mondId;
	private Boolean esActivo;
	private Date fechaAlta;	
	private PersonaDto personaDto;
}
