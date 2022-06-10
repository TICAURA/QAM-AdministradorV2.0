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
public class CorteAnticipoDto {
	
	
    private Integer corteId;
	
	private Integer centroCostosId;
	
	private String periodicidad;
	
	private Date fchInicio;
	
	private Date fchFin;
	
	private Date fchCorteIncidencias;
	
	private Date fchPago;
	
	private Boolean esActivo;

}
