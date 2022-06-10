package com.aura.admin.adminqamm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="servicio")
public @Getter
@Setter
class Servicio {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id_servicio")
        private int idServicio;

        @Column(name = "nombre_servicio")
        private String nombre;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="id_tipo_ambiente")
        @JsonBackReference(value="servicio-tipo-ambiente")
        private TipoAmbiente ambiente;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="id_tipo_servicio")
        @JsonBackReference(value="servicio-tipo-servicio")
        private TipoServicio servicio;

        @Column(name = "endpoint")
        private String endpoint;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="id_usuario")
        @JsonBackReference(value="user-services")
        private Usuario usuario;

        @Column(name = "fecha_modificacion")
        private Date fechaModificacion;
        @Column(name = "fecha_registro")
        private Date fechaRegistro;
        @Column(name = "ind_activo")
        private boolean activo;

}
