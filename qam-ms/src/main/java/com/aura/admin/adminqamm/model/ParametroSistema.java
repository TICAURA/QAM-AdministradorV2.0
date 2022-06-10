package com.aura.admin.adminqamm.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parametro_sistema")
@Getter
@Setter
public class ParametroSistema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="consecutivo")
	private Integer consecutivo;
	
	@Column(name="id_parametro")
	private String idParametro;
	
	@Column(name = "fch_alta")
	private Date fchAlta;
	
	@Column(name = "valor")
	private String valor;
	
	@Column(name = "es_global")
	private Boolean esGlobal;
	
	@Column(name = "es_activo")
	private Boolean esActivo;
	
	@Column(name = "clnt_id")
	private String idCliente;
	
	@Column(name = "fch_baja")
	private Date fchBaja;
	
	@Column(name = "descripcion ")
	private String descripcion;
	
	@Column(name="sgmn_id")
	private Integer sgmnId;
	
	
}
