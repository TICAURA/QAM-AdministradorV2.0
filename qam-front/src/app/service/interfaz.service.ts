import { Injectable } from '@angular/core';
import { HttpSenderService } from './http-sender.service';
import { Endpoint } from '../utils/endpoint';
import { Form } from '@angular/forms';
import { Errors } from '../utils/errors';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InterfazService {

  constructor(private  httpSenderService:HttpSenderService) { }

 

  insert(rootUrl:Endpoint,body:FormData,successCallback:any,errorCallback:any){
    const endpoint:string = rootUrl;
    const method:string = 'POST';
    const jsonBody:FormData = body;
    this.httpSenderService.makeHttpRequestForm(endpoint,method,successCallback,errorCallback,jsonBody);
  }

  modify(rootUrl:Endpoint,body:any,successCallback:any,errorCallback:any){

    const endpoint:string = rootUrl;
    const method:string = 'PUT';
    const jsonBody:string = body;
    this.httpSenderService.makeHttpRequestForm(endpoint,method,successCallback,errorCallback,jsonBody);
  }

}
