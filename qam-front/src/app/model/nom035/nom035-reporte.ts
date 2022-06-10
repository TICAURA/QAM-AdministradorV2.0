import { Nom035Registro } from "./nom035-registro";

export class Nom035Reporte {
    id:number;
    nombre:string;
    cuestionario:Nom035Registro;
    categorias:Array<Nom035Registro>;
    dominios:Array<Nom035Registro>;

    public build(content:any){
        this.id = content.id;
        this.nombre = content.nombre;
        this.cuestionario = new Nom035Registro();
        this.cuestionario.build(content.cuestionario);
        this.categorias = new Array<Nom035Registro>();
        
        content.categorias.forEach((element: any) => {
            let model: Nom035Registro = new Nom035Registro();
            model.build(element);
            this.categorias.push(model);
          });

        this.dominios = new Array<Nom035Registro>();
        content.dominios.forEach((element: any) => {
          let model: Nom035Registro = new Nom035Registro();
          model.build(element);
          this.dominios.push(model);
        });  
    }
}
