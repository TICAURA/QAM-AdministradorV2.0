export class Carga{

    idCarga:number;
    archivo:File;

    build(content:any){

        this.idCarga = content.idCarga;
        this.archivo = content.archivo;


    }

}