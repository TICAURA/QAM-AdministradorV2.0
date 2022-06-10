import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { Errors } from 'src/app/utils/errors';
import { Endpoint } from 'src/app/utils/endpoint';
import { Servicio } from 'src/app/model/servicio';
import { FormsModule } from '@angular/forms';
import { Catalogue } from 'src/app/model/catalogue';
import { ValidationClass } from 'src/app/utils/validation-class';

@Component({
  selector: 'app-form-servicio',
  templateUrl: './form-servicio.component.html',
  styleUrls: ['./form-servicio.component.css']
})
export class FormServicioComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService,private router:Router) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params:any) => {
      this.requestedId = Number(params['requestedId']);
      if(this.requestedId!==-1){
        this.genericService.get(this.typeEndpoint,this.requestedId ,this.getAllSuccess,this.callFailure);
      }
    });
    this.genericService.getAll(Endpoint.TIPO_AMBIENTE,this.loadCatalogueAmbiente,
      (content:any,error:Errors) :void =>{this.showForm=false;this.errorTipoAmbiente=true;});
    this.genericService.getAll(Endpoint.TIPO_SERVICIO,this.loadCatalogueServicio,
      (content:any,error:Errors) :void =>{this.showForm=false;this.errorTipoServicio=true;});
   }
  showForm:boolean=true;
  requestedId:number;
  typeEndpoint:Endpoint = Endpoint.SERVICIO;
  serviceData:Servicio = new Servicio();
  errorMessage:string;

  tipoAmbientes:Catalogue[];
  errorTipoAmbiente:boolean=false;
  tipoServicios:Catalogue[];
  errorTipoServicio:boolean=false;
   originalName:string;

  validName: ValidationClass = ValidationClass.NORMAL;
  nameError:string="";
 

  private callFailure = (content:any,error:Errors) :void =>{this.showForm=false;this.errorMessage=error;} 
  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 

  private getAllSuccess = (content:any):void=>{
    this.serviceData = new Servicio();
    this.originalName = content.nombre;
    this.serviceData.build(content);
  }
  
  validarNombre(callSave:boolean) {

    if(this.requestedId !== -1 && this.serviceData.nombre === this.originalName)
    { this.validName = ValidationClass.VALID; this.nameError=""; if(callSave){this.save();}return;}
   

    if(this.serviceData.nombre===''||this.serviceData.nombre=== undefined || this.serviceData.nombre===null){
      this.validName = ValidationClass.INVALID;
      this.nameError = "Porfavor ingrese un nombre valido.";
    }else{
      this.genericService.validateName(this.typeEndpoint, this.serviceData.nombre,
        (content: any) => { 
          if(content.found === true){
            this.validName = ValidationClass.INVALID; this.nameError = "El nombre ya existe, porfavor elija otro nombre.";
          }else{
            this.validName = ValidationClass.VALID; this.nameError=""; if(callSave){this.save();}
          }   
      },
        this.callFailureShowMessage);
      }
  }

   save():void{

    if(this.requestedId!==-1){
      this.genericService.modify(this.typeEndpoint,this.serviceData,this.updateSuccess,this.callFailureShowMessage);
    }else{
      this.genericService.insert(this.typeEndpoint,this.serviceData,this.saveSuccess,this.callFailureShowMessage);
    }

  }
  private saveSuccess=(content: any):void=>{
    alert("Elemento Guardado con éxito.");
    this.router.navigate(['admin/form-servicio'], { queryParams: { requestedId: content.id  }});
  }

  private updateSuccess = (content: any): void => {
    alert("Elemento actualizado con éxito.");
    this.router.navigate(['/admin/'+this.typeEndpoint]); 
  }

  private loadCatalogueAmbiente = (content:any):void =>{ 
    this.tipoAmbientes = new Array<Catalogue>();
    content.forEach((element:any) => {  
      let tipoAmbiente:Catalogue = new Catalogue(); 
      tipoAmbiente.build(element); 
      this.tipoAmbientes.push(tipoAmbiente); 
    });
  }

  private loadCatalogueServicio = (content:any):void =>{ 
    this.tipoServicios = new Array<Catalogue>();
    content.forEach((element:any) => {  
      let tipoServicio:Catalogue = new Catalogue(); 
      tipoServicio.build(element); 
      this.tipoServicios.push(tipoServicio); 
    });
  }


}
