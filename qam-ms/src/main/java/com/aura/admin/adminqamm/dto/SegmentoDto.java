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
public class SegmentoDto {
	
	private Integer segmentoId;
	
	private String descripcion;
	
	private String nombreCorto;
	
	private Boolean esActivo;
	
	private Date fchAlta;
	
}
