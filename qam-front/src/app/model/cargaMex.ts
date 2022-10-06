export class cargaMex{

    nombre:String;
    apellidoPat:String;
    apellidoMat:String;
    rfc: String;
    clienteDto:{
        razon:String;
    };
    descError:String;
    correctos:number;
    incorrectos:number;
    procesados:number;

    build(content:any){

        this.nombre = content.nombre
        this.apellidoPat = content.apellidoPat
        this.apellidoMat = content.apellidoMat
        this.rfc=content.rfc
        this.clienteDto = content.clienteDto
        this.descError = content.descError
        this.correctos = content.correctos
        this.incorrectos = content.incorrectos
        this.procesados = content.procesados
    }

}