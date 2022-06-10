import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Segmento } from 'src/app/model/segmento';
import { GenericService } from 'src/app/service/generic.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';

@Component({
  selector: 'app-form-segmento',
  templateUrl: './form-segmento.component.html',
  styleUrls: ['./form-segmento.component.css']
})
export class FormSegmentoComponent implements OnInit {


  showForm: boolean = false;
  requestedId: number;
  errorMessage: string;
  typeEndpoint: Endpoint = Endpoint.SEGMENTO;
  errores:String;
  descripcion:String="";
  nombreCorto:String="";
  esActivo:boolean=true;
  public segmentoConsultado:Segmento;


  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService,private router:Router) { }
  private callFailure = (content: any, error: Errors): void => { this.showForm = false; this.errorMessage = error; }

  ngOnInit(): void {



    this.activatedRoute.queryParams.subscribe((params:any) => {
      this.requestedId = Number(params['requestedId']);

      if(  this.requestedId==-1){
        this.showForm=true;
      }else{
      this.genericService.get(this.typeEndpoint,this.requestedId ,this.getsSegmentoSucces,this.callFailure);
      }
      
    });



  }


 

  private getsSegmentoSucces = (content: any): void => {
    this.segmentoConsultado = new Segmento();
    this.segmentoConsultado.build(content);

    this.showForm=true;

  }
  
  private updateSegmentoSucces = (content: any): void => {
    alert("Elemento se ha guardado  con éxito.");
    this.router.navigate(['/admin/'+this.typeEndpoint]); 
  }

  editar(){

    if(this.requestedId==-1){
      
      if(this.validaformulario()!==""){
        this.errorMessage = "Los campos son obligatorios"
      }else{

        this.segmentoConsultado=new Segmento();
        this.segmentoConsultado.descripcion=this.descripcion;
        this.segmentoConsultado.nombreCorto=this.nombreCorto;
        this.segmentoConsultado.esActivo=this.esActivo;

        this.genericService.insert(this.typeEndpoint,this.segmentoConsultado ,this.updateSegmentoSucces,this.callFailure);

      }

    }
    else{
      this.genericService.modify(this.typeEndpoint,this.segmentoConsultado ,this.updateSegmentoSucces,this.callFailure);
    }

  }

  validaformulario(){
    this.errores=""
    if(this.descripcion==null||this.descripcion=="")
      {
        this.errores+="descripción";
      }

      if(this.nombreCorto==null||this.nombreCorto=="")
      {
        this.errores+="nombre corto";
      }
     
      return this.errores;

  }
}
