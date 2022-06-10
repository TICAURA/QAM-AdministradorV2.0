import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpSenderService } from './http-sender.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpService:HttpSenderService) { }

  async login(user:string,password:string,successCallback:any,errorCallback:any){
   

    const rawResponse = await fetch(environment.qammUrl+'user/login', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({user:user,password:password})
      });
  
      const content = await rawResponse.json();
  
      if(rawResponse.status==200){
        this.httpService.setToken(content.token);
        successCallback();
      }else{
        console.log("Error en login.");
        console.debug("Error :"+content.error);
        errorCallback();
      }
  
    }
}
