package com.aura.admin.adminqamm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="c_tipo_ambiente")
public @Getter
@Setter
class TipoAmbiente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tipo_ambiente")
    private int id;

    @Column(name = "nombre_ambiente")
    private String nombre;

    @Column(name = "descripcion_ambiente")
    private String descripcion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    @Column(name = "ind_activo")
    private boolean activo;
}

