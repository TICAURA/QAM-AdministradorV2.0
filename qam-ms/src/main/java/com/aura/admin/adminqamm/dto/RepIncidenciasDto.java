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
public class RepIncidenciasDto {
	
	private String centroCosto;
	
	private String cliente;
	
	private String clienteSubcontrato; 
	
	private Date fchTransferenciaFin;
	
	private Date fchTransferenciaInicio;

}
