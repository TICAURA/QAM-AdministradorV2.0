export class Nom035Registro {

    id:number;
    nombre:string;
    riesgo:string;
    total:number;

    public build(content:any){
    this.id= content.id;
    this.nombre = content.nombre;
    this.riesgo = content.riesgo;
    this.total = content.total;
    }
}
