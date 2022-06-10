package com.aura.admin.adminqamm.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cs_banco")
@Data
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_id")
    private int id; //value

    @Column(name = "cve_banxico")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion; //label
}

