export class Carga{

    idCarga:string;
    archivo:File;

    build(content:any){

        this.idCarga = content.idCarga;
        this.archivo = content.archivo;


    }

}