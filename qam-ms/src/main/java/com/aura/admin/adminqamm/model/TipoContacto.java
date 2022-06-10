package com.aura.admin.adminqamm.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tipo_contacto")
@Data
public class TipoContacto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tctk")
    private int id; //value

    @Column(name = "nom_corto")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion; //label
}


