import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/service/login.service';
import { Router } from '@angular/router';
import { ValidationClass } from 'src/app/utils/validation-class';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private loginService:LoginService,private router:Router ) { }

  ngOnInit(): void {

  }
  user:string="";
  password:string="";
  userValid:ValidationClass = ValidationClass.NORMAL;
  passValid:ValidationClass = ValidationClass.NORMAL;

   login():void{

    let valid:boolean = true;
    if(this.user === ""){this.userValid = ValidationClass.INVALID; valid = false;}else{this.userValid = ValidationClass.VALID;}
    if(this.password === ""){this.passValid = ValidationClass.INVALID; valid = false;}else{this.passValid = ValidationClass.VALID;}

    if(valid){
      this.loginService.login(this.user,this.password
        ,()=>{ this.router.navigate(['/menu']); }
        ,()=>{alert("Error, contraseña o usuario incorrecto.");});
    }

  }

}
