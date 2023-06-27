package com.aura.admin.adminqamm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "det_carga_nequi")
public class DetCargaNequi {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_det_carga_nequi")
    private Integer idDetCarga;
    
    @Column(name="id_carga_nequi")
    private Integer idCargaMasiva;

    @Column(name="nombre")
    private String nombre;
    
    @Column(name="primer_apellido")
    private String primerApellido;
    
    @Column(name="segundo_apellido")
    private String segundoApellido;
    
    @Column(name="tipo_documento_id")
    private String tipoDocumentoId;
    
    @Column(name="numero_documento_id")
    private String numeroDocumentoId;
    
    @Column(name="celular")
    private String celular;
    
    @Column(name="email")
    private String email;
    
    @Column(name="cuenta_nequi")
    private String cuentaNequi;
    
    @Column(name="fecha_nacimiento")
    private Date fechaNacimiento;
    
    @Column(name="genero")
    private String genero;
    
    @Column(name="periodicidad")
    private String periodicidad;
    
    @Column(name="salario_diario")
    private Double salarioDiario;
    
    @Column(name="area_posicion")
    private String areaPosicion;
    
    @Column(name="tipo_contrato")
    private String tipoContrato;
    
    @Column(name="c_estado")
    private Integer cEstado;
    
    @Column(name="id_banco_nequi")
    private Integer idBancoNequi;
    
    @Column(name="id_tipo_cuenta_banco")
    private Integer idTipoCuentaBanco;
    
    @Column(name="num_cuenta_banco")
    private String numCuentaBanco;
    
    @Column(name="id_estatus_carga_nequi")
    private Integer idEstatusCarga;  
    
    @Column(name="observacion_carga")
    private String observacionCarga;
    
    @Column(name="nit")
    private String nit;
    
}
