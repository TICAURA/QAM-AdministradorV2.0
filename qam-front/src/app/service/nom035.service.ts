import { Injectable } from '@angular/core';
import * as FileSaver from 'file-saver';
import { Endpoint } from '../utils/endpoint';
import { HttpSenderService } from './http-sender.service';

@Injectable({
  providedIn: 'root'
})
export class Nom035Service {

  parametros: String;
  constructor(private httpSenderService: HttpSenderService) {

  }

  getLista(rootUrl: Endpoint, successCallback: any, errorCallback: any) {
    const endpoint: string = rootUrl + "/";
    const method: string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, undefined);
  }

  getCuestionarios(rootUrl: Endpoint, successCallback: any, errorCallback: any) {
    const endpoint: string = `${rootUrl}/cuestionarios`;
    const method: string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, undefined);
  }
  getReporte(rootUrl: Endpoint, idColaborador: number, idCuestionario:number, successCallback: any, errorCallback: any) {
    const endpoint: string = `${rootUrl}/reporte/${idColaborador}/${idCuestionario}`;
    const method: string = 'GET';
    this.httpSenderService.makeHttpRequest(endpoint, method, successCallback, errorCallback, undefined);
  }

  downloadFile(url: string,fileName:string): void {
    this.httpSenderService
      .download(url)
      .subscribe(blob => FileSaver.saveAs(blob, fileName+".xlsx")  );
  }
}
