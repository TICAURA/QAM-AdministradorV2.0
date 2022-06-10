package com.aura.admin.adminqamm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="usuario_nuevo")
@Getter
@Setter

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario")
    private int userId;
    @Column(name = "display_name")
    private String name;
    @Column(name="fecha_modificacion")
    private Date fechaModificacion;
    @Column(name="fecha_registro")
    private Date fechaRegistro;
    @Column(name="ind_activo")
    private boolean activo;

    @Column(name="nombre_usuario")
    private String user;
    @Column(name="palabra_secreta")
    private String password;
    @Column(name="ultimo_login")
    private Date ultimoLogin;
    @Column(name="ultima_actividad")
    private Date ultimaActividad;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usuario")
    @JsonBackReference(value="user-services")
    Set<Servicio> servicios;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usuario")
    @JsonBackReference(value="user-interfaces")
    Set<Interfaz> interfaces;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usuario")
    @JsonBackReference(value="user-userclients")
    Set<UsuarioCliente> usuarioClientes;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usuario")
    @JsonBackReference(value="user-userroles")
    Set<UsuarioRol> roles;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usuario")
    @JsonBackReference(value="user-userpermissions")
    Set<UsuarioPermiso> permisos;



}
