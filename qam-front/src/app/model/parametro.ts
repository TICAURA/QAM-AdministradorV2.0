export class Parametro{
    idParametro:number;
    idSegmento:number;
    parametroName:string;
    descripcion:string;
    valor:string;
    global:boolean;
    activo:boolean;



    build(content:any){

        this.idParametro=content.idConsecutivo;
        this.idSegmento=content.idSegmento;
        this.parametroName=content.idParametro;
        this.descripcion=content.descripcion;
        this.valor=content.valor;
        this.global=content.global;
        this.activo=content.activo;
    }
}