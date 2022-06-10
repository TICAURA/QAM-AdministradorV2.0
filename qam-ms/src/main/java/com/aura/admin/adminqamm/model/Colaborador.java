package com.aura.admin.adminqamm.model;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "colaborador")
public class Colaborador {

	@EmbeddedId
	private ColaboradorId colaboradorId;
	
	@Column(name="clave_colaborador")
	private Long claveColaborador;
	
	@Column(name="salario_diario_real")
	private Double salarioDiarioReal;
	
	@Column(name="salario_neto_periodo")
	private Double salarioNetoPeriodo;
	
	@Column(name="salario_diario_integrado")
	private Double salarioDiarioIntegrado;
	
	@Column(name="distribucion_imss_complemento")
	private Double distribucionImssComplemento;
	
	@Column(name="periodicidad")
	private String periodicidad;
	
	@Column(name="fch_inicio")
	private Date fchInicio;
	
	@Column(name="fch_fin")
	private Date fchFin;
	
	@Column(name="fch_pago")
	private Date fchPago;
	
	@Column(name="fch_corte_incidencias")
	private Date fchCorteIncidencias;
	
	@Column(name="es_activo")
	private Boolean esActivo;
	
	@Column(name = "password")
	private String password;

	@Column(name = "e_mail_registro")
	private String mailRegistro;
	
	@Column(name = "soy_guest")
	private Boolean soyGuest;


}
