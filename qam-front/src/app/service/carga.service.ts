import { Injectable } from '@angular/core';
import { HttpSenderService } from './http-sender.service';
import { Endpoint } from '../utils/endpoint';
import { Form } from '@angular/forms';
import { Errors } from '../utils/errors';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CargaService {

  constructor(private  httpSenderService:HttpSenderService) { }

 

  insert(rootUrl:Endpoint,body:FormData,successCallback:any,errorCallback:any){
    
    const endpoint:string = rootUrl;
    const method:string = 'POST';
    const jsonBody:FormData = body;
    
    this.httpSenderService.makeHttpRequestForm(endpoint,method,successCallback,errorCallback,jsonBody);
    
  }

  getAll(rootUrl: Endpoint, getSuccess: any, errorCallback: any, idCarga:number) {
    const endpoint: string = rootUrl + "/procesados"+ `/${idCarga}`;
    const method: string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint, method, getSuccess, errorCallback, undefined);
  }

}