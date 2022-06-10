package com.aura.admin.adminqamm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cesar A. Soto
 *
 */
@Getter
@Setter
@Embeddable
public class ColaboradorId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 309779298587780481L;

	@Column(name="pers_id")
	private Integer personaId;	

	@Column(name="fch_ingreso")
	private Date fechaIngreso;

	@Column(name="clnt_id")
	private Integer clienteId;
	
	
	@Override
	public int hashCode() {		
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {		
		return super.equals(obj);
	}	
	
}
