export class Repincidente {
    centroCostos:String;
    cliente:String;
    clienteSubcontrato:String;
    fechaInicio:String;
    fechaFin:String;
    build(content:any){

        this.centroCostos=content.centroCostos;
        this.cliente=content.cliente;
        this.clienteSubcontrato=content.cliente
        this.fechaInicio=content.fechaInicio;
        this.fechaFin=content.fechaFin;


        

    }
}
