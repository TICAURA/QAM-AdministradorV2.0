package com.aura.admin.adminqamm.dto.response;

import com.aura.admin.adminqamm.model.ParametroSistema;

import lombok.Data;

@Data
public class ParametroDto {
	private String parametro;
	private String descripcion;
	private String valor;
	private boolean global;
	private boolean activo;
	private Integer idConsecutivo;
	private String idParametro;
	private Integer idSegmento;

	public ParametroDto build(ParametroSistema parametro) {
		this.idConsecutivo=parametro.getConsecutivo();
		this.idParametro=parametro.getIdParametro();
		this.parametro=parametro.getDescripcion();
		this.valor=parametro.getValor();
		this.global=parametro.getEsGlobal();
		this.activo=parametro.getEsActivo();
		this.descripcion=parametro.getDescripcion();
		this.idSegmento= parametro.getSgmnId();
		
		return this;
	}
}
