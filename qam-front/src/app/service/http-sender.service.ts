import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Endpoint } from '../utils/endpoint';
import { Errors } from '../utils/errors';

@Injectable({
  providedIn: 'root'
})
export class HttpSenderService {
  header = {
    'Content-Type': 'application/json',
    'Authorization': '',
    'Access-Control-Allow-Origin': '*'
  }
  header2 = {
    'Accept': 'application/json',
    'Authorization': '',
    'Access-Control-Allow-Origin': '*'
  }

  headerExcel = {
    'Content-Type': 'application/vnd.ms-excel',
    'Authorization': '',
    'Access-Control-Allow-Origin': '*'
  }
  responseType: string = "";
  endpoint: boolean = false;

  constructor(private http: HttpClient) {

  }

  public setToken(token: string): void {
    this.header.Authorization = token;
    this.header2.Authorization = token;
    this.headerExcel.Authorization = token;
  }

  public getToken():string{
return this.header.Authorization;
  }



  public async makeHttpRequest(endpoint:string,method:string,successCallback:any,errorCallback:any,body?:any){

    
    if(this.header.Authorization === ''){ errorCallback("Porfavor haga login antes de acceder a la información.",Errors.LOGIN);}
   
    const rawResponse = await fetch(environment.qammUrl+endpoint,this.buildParameters(method,body));
    this.processResponse(rawResponse,successCallback,errorCallback);

     
  }

  public async makeHttpRequestForm(endpoint:string,method:string,successCallback:any,errorCallback:any,body?:any){


    const rawResponse = await fetch(environment.qammUrl+endpoint,{
      method: method,
      headers: this.header2,
      body: body
      });
  
      this.processResponse(rawResponse,successCallback,errorCallback);
     
  }

  public async processResponse(rawResponse:any,successCallback:any,errorCallback:any){
    const content = await rawResponse.json();
    if(rawResponse.status>=200 && rawResponse.status<300 ){
      successCallback(content);
    }else{
      console.debug("Error :"+content.error);
      let error:Errors;

      switch(rawResponse.status){
        case 401:{ error=Errors.UNAUTHORIZED; break;}
        case 404:{ error=Errors.NOT_FOUND; break;}
        case 406:{ error=Errors.NOT_ACCEPTABLE;break;}
        case 500:{ error=Errors.INTERNAL_SERVER_ERROR; break;}
        default: { error=Errors.INTERNAL_SERVER_ERROR; break;}
      }

      errorCallback(content.error,error);
    }

  }

  private buildParameters(method: string, body?: string) {
    if (body !== undefined) {
      const params = {
        method: method,
        headers: this.header,
        body: body
      }
      return params;
    } else {
      const params = {
        method: method,
        headers: this.header,
        responseType: this.responseType
      }
      return params;
    }
  }



  public download(file: String | undefined): Observable<Blob> {

    return this.http.get(environment.qammUrl + file, {

      headers: this.headerExcel,
      responseType: 'blob'
    });
  }


}
