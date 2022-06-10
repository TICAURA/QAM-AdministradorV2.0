package com.aura.admin.adminqamm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="interfaz_grafica")
public @Getter @Setter class Interfaz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_interfaz")
    private int idInterfaz;
    @Column(name = "nombre_interfaz")
    private String nombre;

    @Column(name="titulo_interfaz")
    private String titulo;

    @Column(name = "url_logo_icono")
    private String urlLogoIcono;
    @Column(name = "color_global_primario")
    private String colorGlobalPrimario;
    @Column(name = "color_global_secundario")
    private String colorGlobalSecundario;
    @Column(name = "color_home_primario")
    private String colorHomePrimario;
    @Column(name = "color_home_secundario")
    private String colorHomeSecundario;
    @Column(name = "color_texto")
    private String colorTexto;
    @Column(name = "color_error")
    private String colorError;
    @Column(name = "color_background")
    private String colorBackground;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario")
    @JsonBackReference(value="user-interfaces")
    private Usuario usuario;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    @Column(name = "ind_activo")
    private boolean activo;
}
