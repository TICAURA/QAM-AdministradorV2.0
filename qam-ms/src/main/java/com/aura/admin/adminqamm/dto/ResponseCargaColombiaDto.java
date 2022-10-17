/**
 * 
 */
package com.aura.admin.adminqamm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author Cesar Agustin Soto
 *
 */
@Data
public class ResponseCargaColombiaDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1806383649497790974L;
	
	private List<ColaboradorDto> colaboradores = new ArrayList<ColaboradorDto>();
	
	private Integer procesados;
	
	private Integer exitosos;
	
	private Integer fallidos;
	
	private Integer idCargaMasiva;

}
