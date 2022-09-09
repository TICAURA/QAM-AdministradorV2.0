import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/model/cliente';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { Catalogue } from 'src/app/model/catalogue';
import { Servicio } from 'src/app/model/servicio';
import { Interfaz } from 'src/app/model/interfaz';
import { FormsModule } from '@angular/forms';
import { UtileriaServicio } from 'src/app/utils/utileriasservicio';
import { HttpSenderService } from 'src/app/service/http-sender.service';
import * as FileSaver from 'file-saver';
import { CargaService } from 'src/app/service/carga.service';
import { Carga } from 'src/app/model/carga';

 @Component({
   selector: 'app-form-carga-mex',
   templateUrl: './form-carga-mex.component.html',
   styleUrls: ['./form-carga-mex.component.css']
 })
 export class FormCargaMexComponent implements OnInit {
correcto = 0;
error = 0;
archivos = 1;
files: Carga;
typeEndpoint:Endpoint = Endpoint.CARGAMEX;
getAllSuccess: any;
callFailure: any;
aux :File;

constructor(private router:Router, private cargaService : CargaService, private rest:HttpSenderService){

}
private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 
  ngOnInit(): void {
    this.files = new Carga();
    this.files.idCarga = "idCarga";
  }

  onSearch(){
    this.downloadFile("layoutcarga");
    

  }

  downloadFile(filename: String): void {

    this.rest
      .download(Endpoint.CARGAMEX+filename)
      .subscribe(blob => FileSaver.saveAs(blob, "Layoutcarga.xls")  );
  }


  onUpload(){
    let form:FormData = new FormData();

    form.append('idCarga',this.files.idCarga);

    if(this.files !== undefined && this.files !== null){
    form.append('archivo', this.files.archivo, this.files.archivo.name);}

    


    try{
      
      
      this.cargaService.insert(this.typeEndpoint, form, this.saveSuccess, this.callFailureShowMessage)
      console.log("cargado")
      
    }
    catch{
      console.log ("error al inertar archivo");
    }

  }
  onFileChange(event: any){

    this.files.archivo = event.target.files[0]
    console.log(this.files.archivo);
  }   



  private saveSuccess=(content: any):void=>{
         alert("Elemento Guardado con éxito.");
        this.router.navigate(['app-form-carga-mex']);
  }
}

