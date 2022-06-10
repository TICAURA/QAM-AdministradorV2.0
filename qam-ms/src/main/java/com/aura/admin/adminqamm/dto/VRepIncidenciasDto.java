package com.aura.admin.adminqamm.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Cesar Agustin Soto
 *
 */
@Data
public class VRepIncidenciasDto {

	private String centroCostos;
	
	private String cliente;
	
	private String rfcCliente;

	private String registroPatronalCliente;
	
	private String clienteSubcontrato;
	
	private String rfcClienteSubcontrato;
	
	private String claveColaborador;
	
	private String curp;
	
	private String nombre;
	
	private String apellidoPat;
	
	private String apellidoMat;
	
	private String periodicidad;
	
	private String periodo;
	
	private String producto;
	
	private String banco;
	
	private String clave;
	
	private String cuenta;
	
	private String tarjeta;
	
	private String importe;
	
	private String comision;
	
	private String total;
	
	private String saldoGratis;
	
	private String montoNeto;
	
	private String fchTransferencia;
	
	private String claveAutorizacion;
	
	private String fechaNacimiento;
	
	private String idAnti;
	
	private String sexo;
	
	private String fechaStp;
	
	private String estado;
	
	private String motivoRechazo;

}
