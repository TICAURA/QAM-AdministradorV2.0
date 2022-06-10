package com.aura.admin.adminqamm.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * @author Ulises Contreras
 */
@Entity
@Table(name="medio_contacto")
public class MedioContacto implements Serializable {

	
	private static final long serialVersionUID = 2395862520326673901L;

		
	@EmbeddedId
	private MedioContactoId mediContactoId;
	
//	@Column(name="tipo_cntk")
//	private String tipoCntk;
//	
	@Column(name = "es_activo")
	private Boolean esActivo;

	@Column(name = "es_principal")
	private Boolean esPrincipal;
	
	@Column(name = "es_celular_enrolamiento")
	private Boolean esCelularEnrolamiento;

	@Column(name = "fch_alta")
	private Date fchAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_tctk", nullable=false)
	private TipoContacto idTctk;

//	public Integer getNumClave() {
//		return numClave;
//	}
//
//	public void setNumClave(Integer numClave) {
//		this.numClave = numClave;
//	}

	
//	public String getTipoCntk() {
//		return tipoCntk;
//	}
//
//	public void setTipoCntk(String tipoCntk) {
//		this.tipoCntk = tipoCntk;
//	}

	public Boolean getEsActivo() {
		return esActivo;
	}

	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}

	public Boolean getEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}

	public Boolean getEsCelularEnrolamiento() {
		return esCelularEnrolamiento;
	}

	public void setEsCelularEnrolamiento(Boolean esCelularEnrolamiento) {
		this.esCelularEnrolamiento = esCelularEnrolamiento;
	}

	public Date getFchAlta() {
		return fchAlta;
	}

	public void setFchAlta(Date fchAlta) {
		this.fchAlta = fchAlta;
	}

	public MedioContactoId getMediContactoId() {
		return mediContactoId;
	}

	public void setMediContactoId(MedioContactoId mediContactoId) {
		this.mediContactoId = mediContactoId;
	}

	/**
	 * @return the idTctk
	 */
	public TipoContacto getIdTctk() {
		return idTctk;
	}

	/**
	 * @param idTctk the idTctk to set
	 */
	public void setIdTctk(TipoContacto idTctk) {
		this.idTctk = idTctk;
	}
	
}
