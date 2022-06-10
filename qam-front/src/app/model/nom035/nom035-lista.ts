export class Nom035Lista {
    
    id:number;
    nombre:string;
    c0:number;
    c1:number;

    public build(content:any){
        this.id = content.colaborador;
        this.nombre = content.nombre;
        this.c0 = content.c0Respondidas;
        this.c1 = content.c1Respondidas;
    }
}
