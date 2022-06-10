import { DatePipe } from '@angular/common';
export class Promocion {
    idPromocion:number;
    nombre:string;
    descripcion:string;
    tipoPromocion:number;
    codigoPromo:string;
    fechaInicio:Date;
    fechaFin:Date;
    montoBeneficio:number;
    porcentajeBeneficio:number;
    montoPorcentajeMaximo:number;
    fechaCreacion:Date;
    activo:boolean;

    build(content:any){
        const datepipe: DatePipe = new DatePipe('en-US')
        this.idPromocion = content.idPromocion;
        this.nombre = content.nombre;
        this.descripcion = content.descripcion;
        this.tipoPromocion = content.tipoPromocion;

        this.codigoPromo = content.codigoPromo;

        this.fechaInicio = content.fechaInicio;
        this.fechaFin = content.fechaFin;

        this.montoBeneficio = content.montoBeneficio;

        this.porcentajeBeneficio = content.porcentajeBeneficio;
        this.montoPorcentajeMaximo = content.montoPorcentajeMaximo;

     
        this.fechaCreacion = content.fechaCreacion;

        if(content.activo !== undefined && content.activo !== null)
        this.activo = content.activo;
        
    
    }
}
