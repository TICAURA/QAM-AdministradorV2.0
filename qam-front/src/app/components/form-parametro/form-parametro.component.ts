import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UpdateParametro } from 'src/app/model/updateParametro';
import { GenericService } from 'src/app/service/generic.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';



@Component({
  selector: 'app-form-parametro',
  templateUrl: './form-parametro.component.html',
  styleUrls: ['./form-parametro.component.css']
})
export class FormParametroComponent implements OnInit {

  tipoParametros:string[]=["--Seleccione--","Global","Particular"];
  centroCostos:string[]=["--Seleccione--","Costo1","Costo2"];

  updateParametro:UpdateParametro=new UpdateParametro;
  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 

  typeEndpoint:Endpoint = Endpoint.PARAMETROS;

  filtro:String;
  tipoPa:String;
  centro:String;

  id:Number;
  parametro:String ;
  esActivo:boolean;
  valor:String;
  valornuevo:String;
  errorMessage:String;
  
  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService,private router:Router) { }


  ngOnInit(): void {


    this.activatedRoute.queryParams.subscribe((params:any) => {
      this.id = Number(params['requestedId']);
      this.valor= String(params['valor']);
      this.parametro= String(params['parametro']);
    });
    
  }

  editar(){
    console.info( "Enviar"+this.valor);
    

    this.updateParametro.consecutivo=Number(this.id);
    this.updateParametro.valor=String(this.valor);


    this.genericService.modify(this.typeEndpoint,this.updateParametro,this.updateSuccess,this.callFailureShowMessage);


  }

  
  private updateSuccess = (content: any): void => {
    alert("Elemento actualizado con éxito.");
    this.router.navigate(['/admin/'+this.typeEndpoint]); 
  }

  onSearch(){
    console.info( "nuscando");

  }
  onClean(){
    console.info( "limpiuando");

  }
}
