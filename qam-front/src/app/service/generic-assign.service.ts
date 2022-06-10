import { Injectable } from '@angular/core';
import { AssignEndpoint } from '../utils/assign-endpoint';
import { HttpSenderService } from './http-sender.service';

@Injectable({
  providedIn: 'root'
})
export class GenericAssignService {

  constructor(private  httpSenderService:HttpSenderService) { 

  }

  getAll(rootUrl:AssignEndpoint,userId:number,successCallback:any,errorCallback:any){
    const endpoint:string = `${rootUrl}/${userId}`;
    const method:string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint,method,successCallback,errorCallback,undefined);
  }

 
  link(rootUrl:AssignEndpoint,userId:number,successCallback:any,errorCallback:any,body:any){
    const endpoint:string = rootUrl;
    const method:string = 'POST';
    const jsonBody:string = JSON.stringify(body);
    this.httpSenderService.makeHttpRequest(endpoint,method,successCallback,errorCallback,jsonBody);
  }

  break(rootUrl:AssignEndpoint,userId:number,successCallback:any,errorCallback:any,body:any){
    const endpoint:string = rootUrl;
    const method:string = 'DELETE';
    const jsonBody:string = JSON.stringify(body);
    this.httpSenderService.makeHttpRequest(endpoint,method,successCallback,errorCallback,jsonBody);
  }
}
