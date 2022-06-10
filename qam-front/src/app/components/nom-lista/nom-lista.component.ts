import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from 'src/app/model/cliente';
import { Nom035Cuestionario } from 'src/app/model/nom035/nom035-cuestionario';
import { Nom035Lista } from 'src/app/model/nom035/nom035-lista';
import { GenericService } from 'src/app/service/generic.service';
import { Nom035Service } from 'src/app/service/nom035.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';

@Component({
  selector: 'app-nom-lista',
  templateUrl: './nom-lista.component.html',
  styleUrls: ['./nom-lista.component.css']
})
export class NomListaComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private nomService: Nom035Service, private router: Router,private generalService:GenericService) { }

  ngOnInit(): void {

    this.nomService.getLista(this.type, this.getAllSuccess, this.callFailure);
    this.generalService.getAll(Endpoint.CLIENTE,this.getClientes,this.callFailure);
    
  }

  url: string = "/admin/nom-reporte/";
  type: Endpoint = Endpoint.NOMINA035;
  nomlist: Array<Nom035Lista>;
  cuestionario: Array<Nom035Cuestionario>
  showTable: boolean = true;
  showFiltro: boolean = false;
  tableName: string;
  errorMessage: string;
  clientCatalogue:Array<Cliente>;
  clienteId:number=-1;

  private getAllSuccess = (content: any): void => {

    this.nomlist  = new Array<Nom035Lista>();
    content.forEach((element: any) => {
      let model: Nom035Lista = new Nom035Lista();
      model.build(element);
      this.nomlist.push(model);
    });
    this.nomService.getCuestionarios(this.type, this.getAllSuccessCuestionarios, this.callFailure);

  }

  private getClientes = (content:any):void =>{

    this.clientCatalogue = new Array<Cliente>();
    content.forEach((element: any) => {
     let client : Cliente = new Cliente();
     client.build(element);
     this.clientCatalogue.push(client);
    });

  }

  private getAllSuccessCuestionarios = (content: any): void => {
    
    this.cuestionario = new Array<Nom035Cuestionario>();
    content.forEach((element: any) => {
      let model: Nom035Cuestionario = new Nom035Cuestionario();
      model.build(element);
      this.cuestionario.push(model);
    });

  }

  private callFailure = (content: any, error: Errors): void => { this.showTable = false; this.errorMessage = error; }
  private callFailureShowMessage = (content: any, error: Errors): void => { alert(error); }

  public getClientReporte(){
    console.log(this.clienteId);

    let cliente:Cliente = new Cliente();
    cliente.idClient = -1;
    
    this.clientCatalogue.forEach((element: Cliente) => {
      if(element.idClient+"" === this.clienteId+""){
        cliente = element;
        return;
      }
    });

    if(cliente.idClient === -1){
      alert("Porfavor seleccione un cliente.")
      return;
    }

    console.log(cliente);

    this.nomService.downloadFile("nom035/reportes/general/"+cliente.idClient,"ReporteNOM035_"+cliente.razon);
  }

}
