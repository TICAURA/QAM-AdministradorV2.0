package com.aura.admin.adminqamm.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "moneda")
@Data
public class Moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_moneda")
    private int id; //value

    @Column(name = "nom_corto")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion; //label

}
