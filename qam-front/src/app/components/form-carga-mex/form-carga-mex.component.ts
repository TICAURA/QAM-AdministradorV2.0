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
  files: File;
  typeEndpoint:Endpoint = Endpoint.CARGAMEX;
  
  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService,private router:Router, private cargaService : CargaService){

  }

  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 

  ngOnInit(): void {
  }


  onSearch(){

  }

  onUpload(){

    let form:FormData = new FormData();

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
    this.router.navigate(['app-form-carga-mex']);
  }
}

