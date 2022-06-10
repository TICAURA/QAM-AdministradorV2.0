import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Catalogue } from 'src/app/model/catalogue';
import { FormsModule } from '@angular/forms';
import { Errors } from 'src/app/utils/errors';
import { AssignEndpoint } from 'src/app/utils/assign-endpoint';
import { ValidationClass } from 'src/app/utils/validation-class';

@Component({
  selector: 'app-form-rol',
  templateUrl: './form-rol.component.html',
  styleUrls: ['./form-rol.component.css']
})
export class FormRolComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService,private router:Router) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params:any) => {
      this.requestedId = Number(params['requestedId']);
      if(this.requestedId!==-1){this.genericService.get(this.typeEndpoint,this.requestedId ,this.getAllSuccess,this.callFailure);}
    });
  }

  
  showForm:boolean=true;
  requestedId:number;
  typeEndpoint:Endpoint = Endpoint.ROL;
  catalogueData:Catalogue = new Catalogue();
  errorMessage:string;
  assignPermission:AssignEndpoint = AssignEndpoint.ROL_PERMISSION;
  originalName:string;
  validName: ValidationClass = ValidationClass.NORMAL;
  nameError:string="";

  private callFailure = (content:any,error:Errors) :void =>{this.showForm=false;this.errorMessage=error;} 
  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 

  private getAllSuccess = (content:any):void=>{
    this.catalogueData = new Catalogue();
    this.originalName = content.nombre;
    this.catalogueData.build(content);
  }
  
   save():void{

    if(this.requestedId!==-1){
      this.genericService.modify(this.typeEndpoint,this.catalogueData,this.updateSuccess,this.callFailureShowMessage);
    }else{
      this.genericService.insert(this.typeEndpoint,this.catalogueData,this.saveSuccess,this.callFailureShowMessage);
    }

  }
  private saveSuccess=(content: any):void=>{
    alert("Elemento Guardado con éxito.");
    this.router.navigate(['admin/form-rol'], { queryParams: { requestedId: content.id  }});
  }
  private updateSuccess = (content: any): void => {
    alert("Elemento actualizado con éxito.");
    this.router.navigate(['/admin/'+this.typeEndpoint]); 
  }

  validarNombre(callSave:boolean) {
    if(this.requestedId !== -1 && this.catalogueData.nombre === this.originalName)
    { this.validName = ValidationClass.VALID; this.nameError=""; if(callSave){this.save();}return;}
   

    if(this.catalogueData.nombre===''||this.catalogueData.nombre=== undefined || this.catalogueData.nombre===null){
      this.validName = ValidationClass.INVALID;
      this.nameError = "Porfavor ingrese un nombre valido.";
    }else{
    this.genericService.validateName(this.typeEndpoint, this.catalogueData.nombre,
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

}
