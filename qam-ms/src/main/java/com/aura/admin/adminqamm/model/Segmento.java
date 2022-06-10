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

/**
 * @author Cesar Agustin Soto
 *
 */
@Entity
@Table(name = "c_segmento")
@Getter
@Setter
public class Segmento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sgmn_id")
	private Integer sgmnId;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="nom_corto")
	private String nomCorto;
	
	@Column(name = "es_activo")
	private Boolean esActivo;
	
	@Column(name = "fch_alta")
	private Date fchAlta;

}
