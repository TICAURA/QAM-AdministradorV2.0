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


@Getter
@Setter
@Entity
@Table(name = "aux_carga_nequi")
public class AuxCargaNequi {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carga_nequi")
    private Integer idCargaMasiva;
    
    @Column(name="nombre_archivo")
    private String nombreArchivo;

    @Column(name="fecha_alta")
    private Date fechaAlta;
    
    @Column(name="hash")
    private String hash;
}
