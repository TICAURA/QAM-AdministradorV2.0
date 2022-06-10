import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/model/usuario';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { GenericService } from 'src/app/service/generic.service';
import { AssignEndpoint } from 'src/app/utils/assign-endpoint';
import { ValidationClass } from 'src/app/utils/validation-class';

@Component({
  selector: 'app-form-usuario',
  templateUrl: './form-usuario.component.html',
  styleUrls: ['./form-usuario.component.css']
})
export class FormUsuarioComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private genericService: GenericService,private router:Router) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params: any) => {
      this.requestedId = Number(params['requestedId']);
      if (this.requestedId !== -1) { this.genericService.get(this.typeEndpoint, this.requestedId, this.getAllSuccess, this.callFailure); }
      else{this.disableFields=false;}
    });
  }
  showForm: boolean = true;
  requestedId: number;
  typeEndpoint: Endpoint = Endpoint.USUARIO;
  userData: Usuario = new Usuario();
  errorMessage: string;
  validName: ValidationClass = ValidationClass.NORMAL;
  nameError:string="";
  validPassword: ValidationClass = ValidationClass.NORMAL;
  password1:string;
  password2:string;
  errorPassword:string="";
  originalName:string;
  disableFields:boolean=true;

  assignClient: AssignEndpoint = AssignEndpoint.USER_CLIENT;
  assignRol: AssignEndpoint = AssignEndpoint.USER_ROL;
  assignPermission: AssignEndpoint = AssignEndpoint.USER_PERMISSION;

  private callFailure = (content: any, error: Errors): void => { this.showForm = false; this.errorMessage = error; }
  private callFailureShowMessage = (content: any, error: Errors): void => { alert(error); }

  private getAllSuccess = (content: any): void => {
    this.userData = new Usuario();
    this.originalName = content.user;

    this.userData.build(content);
    this.disableFields=false;

  }

  save(): void {
  
      if (this.requestedId !== -1) {
        this.genericService.modify(this.typeEndpoint, this.userData, this.updateSuccess, this.callFailureShowMessage);
      } else {
        this.genericService.insert(this.typeEndpoint, this.userData, this.saveSuccess, this.callFailureShowMessage);
      }
   
  }
  private saveSuccess = (content: any): void => {
    alert("Elemento Guardado con éxito.");
    this.router.navigate(['admin/form-usuario'], { queryParams: { requestedId: content.id }});
  }
  private updateSuccess = (content: any): void => {
    alert("Elemento actualizado con éxito.");
    this.router.navigate(['/admin/'+this.typeEndpoint]); 
  }

  public validarNombre() {

    if(this.requestedId !== -1 && this.userData.user === this.originalName)
    { this.validName = ValidationClass.VALID; this.nameError=""; return;}


    if(this.userData.user===''||this.userData.user=== undefined || this.userData.user===null){
      this.validName = ValidationClass.INVALID;
      this.nameError = "Porfavor ingrese un usuario valido.";
    }else{
      this.genericService.validateName(this.typeEndpoint, this.userData.user,
        (content: any) => { 
          if(content.found === true){
            this.validName = ValidationClass.INVALID; this.nameError = "El nombre ya existe, porfavor elija otro nombre.";
          }else{
            this.validName = ValidationClass.VALID; this.nameError=""; 
          }   
      },
        this.callFailureShowMessage);
      }
  }
  
  public validateForm(callSave:boolean) {
 
    if (this.requestedId===-1){
    if (this.userData.user === undefined || this.userData.user === null || this.userData.user === "" || this.validName !== ValidationClass.VALID) { alert("Información invalida, porfavor verifique los datos."); return; }
    if (this.userData.password === undefined || this.userData.password === null || this.userData.password === "" || this.validPassword !== ValidationClass.VALID) { alert("Información invalida, porfavor verifique los datos."); return; }
    }

    if(callSave){this.save();}
  }

  public checkPass():boolean{
    
    if(this.password1 ==='' || this.password2==='' ){
      this.errorPassword = '';
      this.validPassword = ValidationClass.INVALID;
      return false;
    }

    if(this.password1 !== this.password2){
      this.errorPassword = 'Las contraseñas no coinciden';
      this.validPassword = ValidationClass.INVALID;
      return false;
    }

    this.userData.password=this.password1;
    this.validPassword = ValidationClass.VALID;
    this.errorPassword = '';
    return true;

  }

}
