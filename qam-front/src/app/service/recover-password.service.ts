import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecoverPasswordService {

  constructor() { }

  async checkToken(token:string,successCallback:any,errorCallback:any){

    const rawResponse = await fetch(environment.qammUrl+'checar-token/'+token, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
    });

    const content = await rawResponse.json();
    if(rawResponse.status==200){
      successCallback(content);
    }else{
      console.log("Error validando el token.");
      console.debug("Error :"+content.error);
      errorCallback();
    }

   

  }
 async changePassword(token:string,password:string,successCallback:any,errorCallback:any){
   

  const rawResponse = await fetch(environment.qammUrl+'cambiar-contra/'+token, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({password:password})
    });

    const content = await rawResponse.json();

    if(rawResponse.status==200){
      successCallback();
    }else{
      console.log("Error validando el token.");
      console.debug("Error :"+content.error);
      errorCallback();
    }

  }
}
