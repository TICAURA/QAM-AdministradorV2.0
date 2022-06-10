export class Nom035Cuestionario {

    id:number;
    nombre:string;
    cTotal:number;
    public build(content:any){
        this.id=content.idCuestionario;
        this.nombre=content.nombre;
        this.cTotal = content.ctotal;
    }

}
