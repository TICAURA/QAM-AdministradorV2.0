import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { Catalogos } from 'src/app/utils/catalogos';
import { Endpoint } from 'src/app/utils/endpoint';
import { Catalogue } from 'src/app/model/catalogue';
import { Errors } from 'src/app/utils/errors';
import { FormsModule } from '@angular/forms';
import { ValidationClass } from 'src/app/utils/validation-class';

@Component({
  selector: 'app-generic-catalogue-form',
  templateUrl: './generic-catalogue-form.component.html',
  styleUrls: ['./generic-catalogue-form.component.css']
})
export class GenericCatalogueFormComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private genericService: GenericService,private router:Router) { }

  ngOnInit(): void {
    this.type=this.activatedRoute.snapshot.paramMap.get('type') as Catalogos;
    this.requestedId = Number(this.activatedRoute.snapshot.queryParamMap.get('requestedId'));
  
    this.getTypeEndpoint();
      //en caso de que elija un catalogo que no puede modificar y no escoja un id.
    if(this.requestedId !== -1 && !this.canSave){this.showForm=false;return;}

    if (this.requestedId !== -1) { this.genericService.get(this.typeEndpoint, this.requestedId, this.getAllSuccess, this.callFailure); } 
  }
  showForm: boolean = true;
  type: Catalogos;
  requestedId: number;
  typeEndpoint: Endpoint;
  catalogueData: Catalogue = new Catalogue();
  errorMessage: string;
  canSave: boolean = false;
  validName: ValidationClass = ValidationClass.NORMAL;
  catalogueName:string;
  nameError:string ="";
  originalName:string="";

  private getTypeEndpoint() {
    switch (this.type) {
      case Catalogos.PERMISO: { this.typeEndpoint = Endpoint.PERMISO; this.catalogueName="Permiso"; this.canSave = false; break; }
      case Catalogos.FACTURA: { this.typeEndpoint = Endpoint.FACTURA; this.catalogueName="Facturador"; this.canSave = false; break; }
      case Catalogos.DISPERSOR: { this.typeEndpoint = Endpoint.DISPERSOR; this.catalogueName="Dispersor"; this.canSave = false; break; }
      case Catalogos.TIPO_AMBIENTE: { this.typeEndpoint = Endpoint.TIPO_AMBIENTE; this.catalogueName="Tipo de ambiente"; this.canSave = true; break; }
      case Catalogos.TIPO_SERVICIO: { this.typeEndpoint = Endpoint.TIPO_SERVICIO; this.catalogueName="Tipo de servicio"; this.canSave = true; break; }
      default: { this.showForm = false; break; }
    }
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


 private save(): void {
    
      if (this.requestedId !== -1) {
        this.genericService.modify(this.typeEndpoint, this.catalogueData, this.updateSuccess, this.callFailureShowMessage);
      } else {
        this.genericService.insert(this.typeEndpoint, this.catalogueData, this.saveSuccess, this.callFailureShowMessage);
      }
    
  }

  //Callbacks
  private callFailure = (content: any, error: Errors): void => { this.showForm = false; this.errorMessage = error; }
  private callFailureShowMessage = (content: any, error: Errors): void => { alert(error); }

  private getAllSuccess = (content: any): void => {
    this.catalogueData = new Catalogue();
    this.originalName = content.nombre;
    this.catalogueData.build(content);
  }
  private saveSuccess = (content: any): void => {
    alert("Elemento Guardado con éxito.");
    this.router.navigate(['/admin/form-generic/'+this.typeEndpoint], { queryParams: { requestedId: content.id  }}); 
  }
  private updateSuccess = (content: any): void => {
    alert("Elemento actualizado con éxito.");
    this.router.navigate(['/admin/'+this.typeEndpoint]); 
  }
}
