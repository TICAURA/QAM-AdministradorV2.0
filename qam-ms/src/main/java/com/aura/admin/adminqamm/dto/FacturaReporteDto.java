package com.aura.admin.adminqamm.dto;

import lombok.Data;

@Data
public class FacturaReporteDto {
	

	private int claveColaborador;
	private String rfc;
	private String nombre;
	private String apellidoP;
	private String apellidoM;
	private String nombreC;
	private String email;
	private String nss;
	private String periodicidad;
	private String curp;
	private double iva;
	private double comision;

	
}
