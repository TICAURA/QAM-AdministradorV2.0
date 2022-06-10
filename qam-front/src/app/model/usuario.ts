
import { DatePipe } from '@angular/common';

export class Usuario {
    
    userId:number;
    name:string;
    fechaModificacion:string;
    fechaRegistro:string;
    activo:boolean;
    user:string;
    password:string;
    ultimoLogin:string;
    ultimaActividad:string;
    build(content:any){
        const datepipe: DatePipe = new DatePipe('en-US')
        this.userId = content.userId;
        this.name = content.name;
        this.user = content.user;
     
        if(content.ultimoLogin!==null && content.ultimoLogin!==undefined){
        const ultimoL:any = datepipe.transform(content.ultimoLogin, 'dd-MM-YYYY HH:mm:ss');
        this.ultimoLogin = ultimoL!==null?ultimoL:"";
        }
        if(content.ultimaActividad!==null && content.ultimaActividad!==undefined){
        const ultimaA:any = datepipe.transform(content.ultimaActividad, 'dd-MM-YYYY HH:mm:ss');
        this.ultimaActividad = ultimaA!==null?ultimaA:"";
        }

        const fechaR:any = datepipe.transform(content.fechaRegistro, 'dd-MM-YYYY HH:mm:ss');
        this.fechaRegistro = fechaR!==null?fechaR:"";

        const fechaM:any = datepipe.transform(content.fechaModificacion, 'dd-MM-YYYY HH:mm:ss');
        this.fechaModificacion = fechaM!==null?fechaM:"";
        this.activo = content.activo;
    }
}
