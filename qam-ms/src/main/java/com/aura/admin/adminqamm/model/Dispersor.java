package com.aura.admin.adminqamm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sistema_dispersor")
public @Getter @Setter class Dispersor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sistema")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

}
