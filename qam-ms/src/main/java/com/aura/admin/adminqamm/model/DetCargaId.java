package com.aura.admin.adminqamm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cesar A. Soto
 *
 */
@Getter
@Setter
@Embeddable
public class DetCargaId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9002904132988832861L;

	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_det_carga_aguilas")
    private Integer idDetCarga;
    
    @Column(name="id_carga_masiva")
    private Integer idCargaMasiva;

}
