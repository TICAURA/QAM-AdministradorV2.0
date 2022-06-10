/**
 * 
 */
package com.aura.admin.adminqamm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author ulises.contreras
 *
 */
@Embeddable
public class MedioContactoId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3843215333318573062L;


	@Column(name="num_clave")
	private String numClave;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numClave == null) ? 0 : numClave.hashCode());
		result = prime * result + ((persId == null) ? 0 : persId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedioContactoId other = (MedioContactoId) obj;
		if (numClave == null) {
			if (other.numClave != null)
				return false;
		} else if (!numClave.equals(other.numClave))
			return false;
		if (persId == null) {
			if (other.persId != null)
				return false;
		} else if (!persId.equals(other.persId))
			return false;
		return true;
	}

	@Column(name = "pers_id")
	private Integer persId;

	

	public String getNumClave() {
		return numClave;
	}

	public void setNumClave(String numClave) {
		this.numClave = numClave;
	}

	public Integer getPersId() {
		return persId;
	}

	public void setPersId(Integer persId) {
		this.persId = persId;
	}

	
}
