import { ContentChild } from "@angular/core";

export class cargaNequi{


    /*  private String nombre;
	    private String primerApellido;	
	    private String segundoApellido;
	    private String celular;
	    private String email;
	    private Date fechaNacimiento;
	    private String genero;
	    private String cEstado;
	    
	    private String areaPosicion;
	    private String periodicidad;
	    private Double salarioDiario;
	    private String tipoDocumentoId;
	    private String tipoContrato;    
	    private String cuentaNequi;
	    private String numeroDocumentoId;
	    private Integer idTipoCuentaBanco;
	    private Integer idBancoNequi;
	    private Integer idEstatusCarga; 
	    private String numCuentaBanco;
	    private String observacioCarga;*/
    nombre:String;
    primerApellido: String;
    segundoApellido: String;
    tipoDocumentoId: String;	
    documentoIdentificacion: String;
    celular: String;	
    email: String;
    cuentaNequi: String;
    observacioCarga:String;
    correctos:number;
    incorrectos:number;
    procesados:number;
    numeroDocumentoId:String;
    
    build(content: any){

        this.nombre = content.nombre
        this.primerApellido = content.primerApellido
        this.segundoApellido = content.segundoApellido
        this.tipoDocumentoId=content.tipoDocumentoId
        this.documentoIdentificacion = content.documentoIdentificacion
        
        this.celular = content.celular
        this.email = content.email
        this.cuentaNequi = content.cuentaNequi
        this.observacioCarga = content.observacioCarga
        this.correctos = content.correctos
        this.incorrectos = content.incorrectos
        this.procesados = content.procesados
        this.numeroDocumentoId = content.numeroDocumentoId
    }

}