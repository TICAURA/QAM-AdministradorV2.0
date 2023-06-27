package com.aura.admin.adminqamm.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CargaMasivaUserDto {
	
	private Integer fila;
	private List<String> errores = new ArrayList<String>();
	private Boolean procesar;
	private String resultado;
	private UsuarioDto usuarioiDto;

}
