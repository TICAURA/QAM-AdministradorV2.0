package com.aura.admin.adminqamm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="c_tipo_servicio")
public @Getter
@Setter
class TipoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tipo_servicio")
    private int id;

    @Column(name = "nombre_servicio")
    private String nombre;

    @Column(name = "descripcion_servicio")
    private String descripcion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    @Column(name = "ind_activo")
    private boolean activo;
}
