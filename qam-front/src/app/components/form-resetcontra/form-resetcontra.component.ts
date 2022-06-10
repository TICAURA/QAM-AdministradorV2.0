import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecContra } from 'src/app/model/reccontra';
import { RecoverPasswordService } from 'src/app/service/recover-password.service';
import { RestServiceService } from 'src/app/service/rest-service.service';
import { UtileriaServicio } from 'src/app/utils/utileriasservicio';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-form-resetcontra',
  templateUrl: './form-resetcontra.component.html',
  styleUrls: ['./form-resetcontra.component.css']
})
export class FormResetcontraComponent implements OnInit {



  constructor(private activatedRoute: ActivatedRoute, private recoverPasswordService: RecoverPasswordService, private rest: RestServiceService) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params: any) => {
      this.token = new String(params['clave']);
      console.info(this.token);
    });
    //this.recoverPasswordService.checkToken(this.token,(content:any)=>{this.email = content.email},()=>{this.email="INVALID";});
  }

  utils: UtileriaServicio = new UtileriaServicio();

  contra1: string = '';
  contra2: string = '';

  error = '';
  token: String = '';
  email = '';

  item: RecContra = new RecContra;
  public checkPass() {
    this.error = ''
    if (this.contra1 === '' || this.contra2 === '') {
      this.error = '';
      return;
    }

    if (this.contra1 !== this.contra2) {
      this.error = 'Las contraseñas no coinciden';
      return;
    }

    this.error = ''

  }

  public checkcorreo() { }

  public sendData() {

    if (this.contra1 !== this.contra2) {
      this.error = 'Las contraseñas no coinciden';

    } else
      if (this.contra1 === '' || this.contra2 === '') {
        this.error = 'Por favor ingrese una contraseña válida.';
      } else {

        this.item.password = this.contra1;
        this.item.passwordConfirma = this.contra2;
        this.item.encoded = this.token;
        //this.rest.post(this.token,this.contra1,()=>{alert("Se cambio la contraseña, porfavor intente ingresar a la aplicación nuevamente.")},()=>{alert("Error, no pudimos cambiar la contraseña, porfavor contacte al admistrador.")});
        this.rest.post(environment.qammUrl + 'resetPassword', this.item).subscribe((content:any) => {
          console.log(content.token);

          this.error =(content.token);
        }
          , err => {
            this.error = err.statusText;
          })


      }
  }


}
