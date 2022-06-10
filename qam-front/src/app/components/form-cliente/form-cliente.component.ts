import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/model/cliente';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { ActivatedRoute } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { Catalogue } from 'src/app/model/catalogue';
import { Servicio } from 'src/app/model/servicio';
import { Interfaz } from 'src/app/model/interfaz';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-cliente',
  templateUrl: './form-cliente.component.html',
  styleUrls: ['./form-cliente.component.css']
})
export class FormClienteComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params:any) => {
      this.requestedId = Number(params['requestedId']);
      if(this.requestedId!==-1){
        this.genericService.get(this.typeEndpoint,this.requestedId ,this.getAllSuccess,this.callFailure);
        this.genericService.getAll(Endpoint.FACTURA,this.loadCatalogueFacturador,
          (content:any,error:Errors) :void =>{this.showForm=false;this.errorFacturadores="Error con el catalogo de Factura : "+error;});
        this.genericService.getAll(Endpoint.DISPERSOR,this.loadCatalogueDispersor,
          (content:any,error:Errors) :void =>{this.showForm=false;this.errorFacturadores="Error con el catalogo de Dispersores : "+error;});
        this.genericService.getAll(Endpoint.SERVICIO,this.loadServices,
          (content:any,error:Errors) :void =>{this.showForm=false;this.errorFacturadores="Error con el catalogo de Servicios : "+error;});
        this.genericService.getAll(Endpoint.INTERFAZ,this.loadInterfaces,
          (content:any,error:Errors) :void =>{this.showForm=false;this.errorFacturadores="Error con el catalogo de Interfaces : "+error;}); 
      }
      else{this.showForm=false;this.errorMessage="Error, porfavor ingrese un ID valido para el cliente.";}
    });
   
  }
  showForm:boolean=true;
  requestedId:number;
  typeEndpoint:Endpoint = Endpoint.CLIENTE;
  clientData:Cliente;
  errorMessage:string;

  facturadores:Catalogue[];
  errorFacturadores:string="";
  dispersores:Catalogue[];
  errorDispersores:string="";
  servicios:Servicio[];
  errorServicios:string="";
  interfaces:Interfaz[];
  errorInterfaces:string="";

  private callFailure = (content:any,error:Errors) :void =>{this.showForm=false;this.errorMessage=error;} 
  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 

  private getAllSuccess = (content:any):void=>{
    this.clientData = new Cliente();
    this.clientData.build(content);
  }
  
   save():void{

    if(this.requestedId!==-1){
      this.genericService.modify(this.typeEndpoint,this.clientData,this.saveSuccess,this.callFailureShowMessage);
    }else{
      this.genericService.insert(this.typeEndpoint,this.clientData,this.saveSuccess,this.callFailureShowMessage);
    }

  }
  private saveSuccess=(content: any):void=>{
    alert("Elemento Guardado con exito.");
  }

  private loadCatalogueFacturador = (content:any):void =>{ 
    this.facturadores = new Array<Catalogue>();
    content.forEach((element:any) => {  
      let facturador:Catalogue = new Catalogue(); 
      facturador.build(element); 
      this.facturadores.push(facturador); 
    });
  }

  private loadCatalogueDispersor = (content:any):void =>{ 
    this.dispersores = new Array<Catalogue>();
    content.forEach((element:any) => {  
      let dispersor:Catalogue = new Catalogue(); 
      dispersor.build(element); 
      this.dispersores.push(dispersor); 
    });
  }

  private loadServices = (content:any):void =>{ 
    this.servicios = new Array<Servicio>();
    content.forEach((element:any) => {  
      let service:Servicio = new Servicio(); 
      service.build(element); 
      this.servicios.push(service); 
    });
  }

  private loadInterfaces = (content:any):void =>{ 
    this.interfaces = new Array<Interfaz>();
    content.forEach((element:any) => {  
      let interfaz:Interfaz = new Interfaz(); 
      interfaz.build(element); 
      this.interfaces.push(interfaz); 
    });
  }

}
