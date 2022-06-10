package com.aura.admin.adminqamm.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
public @Getter @Setter
class Cliente {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "clnt_id")
        private int clientId;

        //@ManyToOne(fetch = FetchType.LAZY)
        //@JoinColumn(name="pers_id_representante_legal")
        //@JsonBackReference(value="client-dispersor")
        //private Persona representanteLegal;

        @Column(name = "curp")
        private String curp;
        @Column(name = "rfc")
        private String rfc;
        @Column(name = "razon_social")
        private String razon;
        @Column(name = "fch_constitucion")
        private Date fechaConstitucion;
        @Column(name = "email_corp")
        private String email;
        @Column(name = "es_activo")
        private boolean activo;
        @Column(name="centro_costos_clnt_id")
    	private Integer centroCostosId;
    	@Column(name="registro_patronal")
    	private String registroPatronal;

        @Column(name = "subc_razon_social")
        private String subRazonSocial;
        @Column(name = "subc_rfc")
        private String subRFC;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="sistema_dispersor_id")
        @JsonBackReference(value="client-dispersor")
        private Dispersor dispersor;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="sistema_factura_id")
        @JsonBackReference(value="client-facturador")
        private Facturador facturador;

        @Column(name = "periodo_factura")
        private Integer periodoFactura;
        @Column(name = "mensaje_correo_recuperar_contra")
        private String mensajeEmailRecuperarContra;
        @Column(name = "mensaje_email_factura")
        private String mensajeEmailFactura;
        @Column(name = "mailer_email_sender")
        private String emailSender;
        
        @Column(name="es_subcontractor")
    	private Boolean esSubcontractor;
    	@Column(name="es_persona_fisica")
    	private Boolean esPersonaFisica;
    	@Column(name="nombre")
    	private String nombre;
    	@Column(name="apellido_pat")
    	private String apellidoPat; 	
    	@Column(name="apellido_mat")
    	private String apellidoMat;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="id_servicio")
        @JsonBackReference(value="client-servicio")
        private Servicio servicio;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="id_interfaz")
        @JsonBackReference(value="client-interfaces")
        private Interfaz interfaz;

        @OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente")
        @JsonBackReference(value="clients-userclients")
        Set<UsuarioCliente> usuarioClientes;


}
