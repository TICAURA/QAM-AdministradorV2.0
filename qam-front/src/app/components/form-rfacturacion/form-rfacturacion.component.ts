import { Component, OnInit } from '@angular/core';
import * as FileSaver from 'file-saver';
import { HttpSenderService } from 'src/app/service/http-sender.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { UtileriaServicio } from 'src/app/utils/utileriasservicio';

@Component({
  selector: 'app-form-rfacturacion',
  templateUrl: './form-rfacturacion.component.html',
  styleUrls: ['./form-rfacturacion.component.css']
})
export class FormRfacturacionComponent implements OnInit {

  constructor(private rest:HttpSenderService) { }

  fchreporte:string="";
  showcontent:boolean=false;
  errorMessage: string;
  
  utils:UtileriaServicio=new UtileriaServicio();
  mes:String;
     

  ngOnInit(): void {
  }

  private callFailure = (content: any, error: Errors): void => { this.showcontent = false; this.errorMessage = error; }

  onSearch(){
    this.fchreporte;

    var objDate = new Date(this.fchreporte);

    let mes= objDate.getMonth()+1;
    this.mes=new  String(mes);

    this.downloadFile(new String(this.utils.getparametros(Endpoint.RFACTURACION,this.mes)));


  }

  downloadFile(filename: String): void {
    this.rest
      .download(Endpoint.RFACTURACION+filename)
      .subscribe(blob => FileSaver.saveAs(blob, "ReporteFactura.xls")  );
  }
}
