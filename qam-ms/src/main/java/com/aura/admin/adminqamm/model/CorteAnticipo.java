/**
 * 
 */
package com.aura.admin.adminqamm.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cesar Agustin Soto
 *
 */
@Entity
@Table(name = "cortes_anticipo")
@Getter 
@Setter
public class CorteAnticipo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cort_id")
    private Integer corteId;
	
	@Column(name="centro_costos_clnt_id")
	private Integer centroCostosId;
	
	@Column(name="periodicidad")
	private String periodicidad;
	
	@Column(name="fch_inicio")
	private Date fchInicio;
	
	@Column(name="fch_fin")
	private Date fchFin;
	
	@Column(name="fch_corte_incidencias")
	private Date fchCorteIncidencias;
	
	@Column(name="fch_pago")
	private Date fchPago;
	
	@Column(name="es_activo")
	private Boolean esActivo;

}
