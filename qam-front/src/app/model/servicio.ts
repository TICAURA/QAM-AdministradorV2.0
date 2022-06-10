import { Catalogue } from './catalogue';
import { DatePipe } from '@angular/common';

export class Servicio {

    idServicio:number;
    nombre:string;
    
    
    tipoAmbienteId:number;
    tipoAmbienteNombre:string;
    tipoServicioId:number;
    tipoServicioNombre:string;

    endpoint:string;

    fechaModificacion:Date;
    fechaRegistro:Date;
    activo:boolean;
    build(content:any){
        const datepipe: DatePipe = new DatePipe('en-US')
        this.idServicio = content.idServicio;
        this.nombre = content.nombre;

        this.tipoAmbienteId  = content.tipoAmbienteId;
        this.tipoAmbienteNombre  = content.tipoAmbienteNombre;
       

        this.tipoServicioId = content.tipoServicioId;
        this.tipoServicioNombre = content.tipoServicioNombre;
     

        this.endpoint = content.endpoint;
       

        const fechaR:any = datepipe.transform(content.fechaRegistro, 'dd-MM-YYYY HH:mm:ss');
        this.fechaRegistro = fechaR!==null?fechaR:"";

        const fechaM:any = datepipe.transform(content.fechaModificacion, 'dd-MM-YYYY HH:mm:ss');
        this.fechaModificacion = fechaM!==null?fechaM:"";
        this.activo = content.activo;
    }
}
