import { Injectable } from '@angular/core';
import { Endpoint } from '../utils/endpoint';
import { HttpSenderService } from './http-sender.service';

@Injectable({
  providedIn: 'root'
})
export class GenericService {

  parametros: String;
  constructor(private httpSenderService: HttpSenderService) {

  }

  getAll(rootUrl: Endpoint, successCallback: any, errorCallback: any) {
    const endpoint: string = rootUrl + "/";
    const method: string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, undefined);
  }

  get(rootUrl: Endpoint, userId: number, successCallback: any, errorCallback: any) {
    const endpoint: string = `${rootUrl}/${userId}`;
    const method: string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, undefined);
  }
  getParametros(rootUrl: Endpoint, body: any, successCallback: any, errorCallback: any) {

    this.parametros = String(this.getparametrosCorte(rootUrl, body));
    console.info(this.parametros)
    const endpoint: string = `${rootUrl}${this.parametros}`;
    const method: string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, undefined);


  }

  insert(rootUrl: Endpoint, body: any, successCallback: any, errorCallback: any) {
    if (rootUrl === Endpoint.PERMISO || rootUrl === Endpoint.CLIENTE || rootUrl === Endpoint.DISPERSOR || rootUrl === Endpoint.FACTURA) { errorCallback("ERROR ESTE METODO HTTP NO ESTA DISPONIBLE PARA EL ENDPOINT : " + rootUrl); return; }
    const endpoint: string = rootUrl;
    const method: string = 'POST';
    const jsonBody: string = JSON.stringify(body);
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, jsonBody);
  }

  modify(rootUrl: Endpoint, body: any, successCallback: any, errorCallback: any) {
    if (rootUrl === Endpoint.PERMISO || rootUrl === Endpoint.DISPERSOR || rootUrl === Endpoint.FACTURA) { errorCallback("ERROR ESTE METODO HTTP NO ESTA DISPONIBLE PARA EL ENDPOINT : " + rootUrl); return; }
    const endpoint: string = rootUrl;
    const method: string = 'PUT';
    const jsonBody: string = JSON.stringify(body);
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, jsonBody);
  }
  delete(rootUrl: Endpoint, userId: number, successCallback: any, errorCallback: any) {
    if (rootUrl === Endpoint.PERMISO || rootUrl === Endpoint.CLIENTE || rootUrl === Endpoint.DISPERSOR || rootUrl === Endpoint.FACTURA) { errorCallback("ERROR ESTE METODO HTTP NO ESTA DISPONIBLE PARA EL ENDPOINT : " + rootUrl); return; }
    const endpoint: string = `${rootUrl}/${userId}`;
    const method: string = 'DELETE';
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, undefined);
  }
  validateName(rootUrl: Endpoint, name: string, successCallback: any, errorCallback: any) {
    const endpoint: string = `${rootUrl}/validate/${name}`;
    const method: string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, undefined);
  }

  public getparametrosCorte(rootUrl: Endpoint, body: any) {
    switch (String(rootUrl)) {

      case Endpoint.CORTE: {
        return "?centroCosto=" + body.centroCostosId + "&&periodicidad=" + body.periodicidad;
      }

      default: { return; }

    }
  }
 
}
