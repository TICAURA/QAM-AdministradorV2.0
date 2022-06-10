import { Component, OnInit } from '@angular/core';
import { Errors } from 'src/app/utils/errors';
import { Interfaz } from 'src/app/model/interfaz';
import { Endpoint } from 'src/app/utils/endpoint';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { FormsModule } from '@angular/forms';
import { ValidationClass } from 'src/app/utils/validation-class';
import { environment } from '../../../environments/environment';
import { InterfazService } from 'src/app/service/interfaz.service';

@Component({
  selector: 'app-form-interfaz',
  templateUrl: './form-interfaz.component.html',
  styleUrls: ['./form-interfaz.component.css']
})
export class FormInterfazComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService,private router:Router, private interfazService: InterfazService) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params:any) => {
      this.requestedId = Number(params['requestedId']);
      if(this.requestedId!==-1){
        console.log(this.requestedId);
        this.genericService.get(this.typeEndpoint,this.requestedId ,this.getAllSuccess,this.callFailure);
      }else{
        this.interfazData = new Interfaz();
        this.interfazData.colorGlobalPrimario="#ffffc0";
        this.interfazData.colorGlobalSecundario="#ffffb0";
        this.interfazData.colorHomePrimario="#ffffa0";
        this.interfazData.colorHomeSecundario="#ffffd0";
        this.interfazData.colorTexto="#ffffa0";
        this.interfazData.colorError="#ffffd0";
        this.interfazData.colorBackground="#ffffe0";
      }
    });
  }

  
  showForm:boolean=true;
  requestedId:number;
  typeEndpoint:Endpoint = Endpoint.INTERFAZ;
  interfazData:Interfaz;
  errorMessage:string="";
  originalName:string="";

  imageRoute:string = environment.qammUrl+"interfaz/archivo/";

  validName: ValidationClass = ValidationClass.NORMAL;
  nameError:string;

  private callFailure = (content:any,error:Errors) :void =>{this.showForm=false;this.errorMessage=error;} 
  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 


  public onFileChange(event:any){

  
      this.interfazData.urlLogoIcono = event.target.files[0];
   
  }

  private getAllSuccess = (content:any):void=>{
    this.interfazData = new Interfaz();
    this.originalName = content.nombre;
    this.interfazData.build(content);
    console.log(content);
  }
  
  validarNombre(callSave:boolean) {
    if(this.requestedId !== -1 && this.interfazData.nombre === this.originalName){ this.validName = ValidationClass.VALID; this.nameError=""; if(callSave){this.save();}return;}
    if(this.interfazData.nombre===''||this.interfazData.nombre=== undefined || this.interfazData.nombre===null){
      this.validName = ValidationClass.INVALID;
      this.nameError = "Porfavor ingrese un nombre valido.";
    }else{
      this.genericService.validateName(this.typeEndpoint, this.interfazData.nombre,
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

    let form:FormData = new FormData();
    
    form.append('colorGlobalPrimario',this.interfazData.colorGlobalPrimario);
    form.append('colorGlobalSecundario',this.interfazData.colorGlobalSecundario);
    form.append('colorHomePrimario',this.interfazData.colorHomePrimario);
    form.append('colorHomeSecundario',this.interfazData.colorHomeSecundario);
    form.append('colorTexto',this.interfazData.colorTexto);
    form.append('colorError',this.interfazData.colorError);
    form.append('colorBackground',this.interfazData.colorBackground);

    form.append('nombre',this.interfazData.nombre);
    form.append('titulo',this.interfazData.titulo);
    form.append('activo',this.interfazData.activo+"");

    
    
    if(this.interfazData.urlLogoIcono !== undefined && this.interfazData.urlLogoIcono !== null){
    form.append('urlLogoIcono', this.interfazData.urlLogoIcono, this.interfazData.urlLogoIcono.name);}
   

    if(this.requestedId!==-1){
      form.append('idInterfaz',this.interfazData.idInterfaz+"");
      this.interfazService.modify(this.typeEndpoint,form,this.updateSuccess,this.callFailureShowMessage);
    }else{
      this.interfazService.insert(this.typeEndpoint,form,this.saveSuccess,this.callFailureShowMessage);
    }

  }
  private saveSuccess=(content: any):void=>{
    alert("Elemento Guardado con éxito.");
    this.router.navigate(['admin/form-interfaz'], { queryParams: { requestedId: content.id  }});
  }
  private updateSuccess = (content: any): void => {
    alert("Elemento actualizado con éxito.");
    this.router.navigate(['/admin/'+this.typeEndpoint]); 
  }

}
