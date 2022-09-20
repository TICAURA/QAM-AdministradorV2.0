package com.aura.admin.adminqamm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "aux_carga_masiva")
public class AuxCarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carga_masiva")
    private Integer idCargaMasiva;
    
    @Column(name="nombre_archivo")
    private String nombreArchivo;

    @Column(name="fecha_alta")
    private Date fechaAlta;
}
