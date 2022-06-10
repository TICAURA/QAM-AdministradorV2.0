import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {RecoverPasswordService} from '../../service/recover-password.service';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.css']
})
export class RecoverPasswordComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute,private recoverPasswordService:RecoverPasswordService) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params:any) => {
      this.token= params['token'];
    });
    this.recoverPasswordService.checkToken(this.token,(content:any)=>{this.email = content.email},()=>{this.email="INVALID";});
  }

  title = 'administrador-qamm';
  var1 = 'ganzo1';
  contra1:string='';
  contra2:string='';
  error='';
  passmatch = false;
  token='';
  email='';


  public checkPass(){
    
    if(this.contra1 ==='' || this.contra2==='' ){
      this.error = '';
      this.passmatch = false;
      return;
    }

    if(this.contra1 !== this.contra2){
      this.error = 'Las contraseñas no coinciden';
      this.passmatch = false;
      return;
    }

    this.passmatch = true;
    this.error = ''

  }
  
  public sendData(){
    
    if(this.contra1 !== this.contra2){
      this.error = 'Las contraseñas no coinciden';
      return;
    }
    if(this.contra1 !== '' || this.contra2 !== ''){
      this.error = 'Por favor ingrese una contraseña válida.';
      return;
    }

   this.recoverPasswordService.changePassword(this.token,this.contra1,()=>{alert("Se cambio la contraseña, porfavor intente ingresar a la aplicación nuevamente.")},()=>{alert("Error, no pudimos cambiar la contraseña, porfavor contacte al admistrador.")});

  }


}
