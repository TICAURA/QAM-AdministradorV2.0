/**
 * 
 */
package com.aura.admin.adminqamm.dto;

import java.util.Date;

import lombok.Data;

/**
 * @author Cesar Agustin Soto
 *
 */
@Data
public class PersonaDto {
	
	private Integer id;
	
	private String curp;

    private String rfc;

    private String nombre;

    private String apellidoPat;

    private String apellidoMat;

    private Date fechaNacimiento;

    private String emailCorporativo;

    private String genero;

    private String nss;

    private Date fechaDeAlta;

    private boolean activo;

    private Date fechaIngreso;
    
	private Date fchAltaQuincenam;
	
	private Date fechaDeNacimiento;

}
