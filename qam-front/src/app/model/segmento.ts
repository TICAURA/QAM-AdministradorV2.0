export class  Segmento{
    segmentoId: Number;
    descripcion: String;
    nombreCorto: String;
    esActivo: boolean;
    fchAlta: Date;

    build(content:any){
        this.segmentoId=content.segmentoId;
        this.descripcion=content.descripcion;
        this.nombreCorto=content.nombreCorto;
        this.esActivo=content.esActivo;
        this.fchAlta=content.fchAlta;
    }

}

