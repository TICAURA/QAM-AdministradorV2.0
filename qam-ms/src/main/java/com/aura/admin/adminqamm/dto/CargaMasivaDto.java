/**
 * 
 */
package com.aura.admin.adminqamm.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author Cesar Agustin Soto
 *
 */
@Data
public class CargaMasivaDto {
	
	private Integer fila;
	private List<String> errores = new ArrayList<String>();
	private Boolean procesar;
	private String resultado;
	private ColaboradorDto colaboradorDTO;

}
