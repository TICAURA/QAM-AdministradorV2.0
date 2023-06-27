/**
 * 
 */
package com.aura.admin.adminqamm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author Edgar Agustin Avila
 *
 */
@Data
public class ResponseCargaNequiDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1806383649497790974L;
	
	private List<UsuarioDto> usuarioDto = new ArrayList<UsuarioDto>();
	
	private Integer procesados;
	
	private Integer exitosos;
	
	private Integer fallidos;
	
	private Integer idCargaMasiva;

}
