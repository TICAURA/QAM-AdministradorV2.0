import { DatePipe } from '@angular/common';

export class Catalogue {
    id:number;
    nombre:string;
    descripcion:string;
    fechaModificacion:Date;
    fechaRegistro:Date;
    activo:boolean;
    build(content:any){
        const datepipe: DatePipe = new DatePipe('en-US')
        this.id = content.id;
        this.nombre = content.nombre;
        this.descripcion = content.descripcion;
        if(content.fechaModificacion !== undefined && content.fechaModificacion !== null){
        const fechaM:any = datepipe.transform(content.fechaModificacion, 'dd-MM-YYYY HH:mm:ss');
        this.fechaModificacion = fechaM!==null?fechaM:"";}
        if(content.fechaRegistro !== undefined && content.fechaRegistro !== null){
        const fechaR:any = datepipe.transform(content.fechaRegistro, 'dd-MM-YYYY HH:mm:ss');
        this.fechaRegistro = fechaR!==null?fechaR:"";}
        if(content.activo !== undefined && content.activo !== null)
        this.activo = content.activo;
    }
}
