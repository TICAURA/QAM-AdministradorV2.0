package com.aura.admin.adminqamm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pers_id")
    private Integer personaId;

    @Column(name="curp")
    private String curp;

    @Column(name="rfc")
    private String rfc;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellido_pat")
    private String apellidoPat;

    @Column(name="apellido_mat")
    private String apellidoMat;

    @Column(name="fch_nace")
    private Date fechaNacimiento;

    @Column(name="email_corp")
    private String emailCorporativo;

    @Column(name="genero")
    private String genero;

    @Column(name="nss")
    private String nss;

    @Column(name="fch_alta_quincenam")
    private Date fechaAltaQuincena;

    @Column(name="es_activo")
    private boolean activo;

    @Column(name="es_representante_legal")
    private boolean isRepresentanteLegal;


}
