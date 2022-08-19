import { Component, OnInit } from '@angular/core';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import * as FileSaver from 'file-saver';
import { CargaService } from 'src/app/service/carga.service';
import { Carga } from 'src/app/model/carga';
import { UtileriaServicio } from 'src/app/utils/utileriasservicio';
import { HttpSenderService } from 'src/app/service/http-sender.service';

@Component({
  selector: 'app-form-carga-col',
  templateUrl: './form-carga-col.component.html',
  styleUrls: ['./form-carga-col.component.css']
})

export class FormCargaColComponent implements OnInit {
  correcto = 0;
  error = 0;
  archivos = 1;
  files: File;
  typeEndpoint:Endpoint = Endpoint.CARGACOL;
  utils:UtileriaServicio=new UtileriaServicio();
  
  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService,private router:Router, private cargaService : CargaService, private rest : HttpSenderService){

  }

  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 

  ngOnInit(): void {
  }


  onUpload(){

    let form:FormData = new FormData();

    console.log (this.files);
    if(this.files !== undefined && this.files !== null){
    form.append('archivoCarga', this.files, this.files.name);}
    
    try{
      this.cargaService.insert(this.typeEndpoint, form, this.saveSuccess, this.callFailureShowMessage)
      this.correcto = 1;
    }
    catch{
      this.error = 1;
      
    }
    
    console.log("cargado")
  }

  onFileChange(event: any){
  
    this.files = event.target.files[0];
    console.log(this.files);

  } 
    

  private saveSuccess=(content: any):void=>{
    alert("Elemento Guardado con éxito.");
    this.router.navigate(['app-form-carga-col']);
  }

  onSearch(){

    this.downloadFile(new String(this.utils.getparametros(Endpoint.RFACTURACION,"7")));

  }

  downloadFile(filename: String): void {
    console.log (filename + "\n" );
    this.rest
      .download(Endpoint.RFACTURACION+filename)
      .subscribe(blob => FileSaver.saveAs(blob, "ReporteFactura.xls")  );
  }
}