package com.aura.admin.adminqamm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "cuenta_banco")
public class CuentaBanco {

    @Id
    @Column(name="clabe")
    private String clabe;

    @Column(name="pers_id")
    private int pers_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bank_id", nullable=false)
    private Banco banco;

    @Column(name="clnt_id")
    private Long clntId;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="numero_cta")
    private String numeroCta;

    @Column(name="mond_id")
    private Long mondId;

    @Column(name="es_activo")
    private Boolean esActivo;

    @Column(name="fch_alta")
    private Date fechaAlta;

    @Column(name="id_moneda")
    private Integer idMoneda;

}

