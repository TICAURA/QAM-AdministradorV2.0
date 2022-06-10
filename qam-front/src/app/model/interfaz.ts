import { DatePipe } from '@angular/common';

export class Interfaz {


    idInterfaz:number;
    nombre:string;
    titulo:string;
    urlLogoIcono:File;
    urlLogoIconoId:string;
    colorGlobalPrimario:string;
    colorGlobalSecundario:string;
    colorHomePrimario:string;
    colorHomeSecundario:string;
    colorTexto:string;
    colorError:string;
    colorBackground:string;
   
    fechaModificacion:Date;
    fechaRegistro:Date;
    activo:boolean;

    build(content:any){
        const datepipe: DatePipe = new DatePipe('en-US')
        this.idInterfaz = content.idInterfaz;
        this.nombre = content.nombre;
        this.titulo = content.titulo;
        this.urlLogoIconoId = content.urlLogoIcono;
        this.colorGlobalPrimario = content.colorGlobalPrimario;
        this.colorGlobalSecundario = content.colorGlobalSecundario;
        this.colorHomePrimario = content.colorHomePrimario;
        this.colorHomeSecundario = content.colorHomeSecundario;
        this.colorTexto = content.colorTexto;
        this.colorError = content.colorError;
        this.colorBackground = content.colorBackground;

    

  

        const fechaR:any = datepipe.transform(content.fechaRegistro, 'dd-MM-YYYY HH:mm:ss');
        this.fechaRegistro = fechaR!==null?fechaR:"";

        const fechaM:any = datepipe.transform(content.fechaModificacion, 'dd-MM-YYYY HH:mm:ss');
        this.fechaModificacion = fechaM!==null?fechaM:"";
        this.activo = content.activo;
    }
}
