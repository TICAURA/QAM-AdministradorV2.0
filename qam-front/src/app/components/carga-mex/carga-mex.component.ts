import { Component, OnInit } from '@angular/core';
import { Carga } from 'src/app/model/carga';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { ActivatedRoute, Router } from '@angular/router';
import * as FileSaver from 'file-saver';
import { CargaService } from 'src/app/service/carga.service';
import { HttpSenderService } from 'src/app/service/http-sender.service';
import { CommonModule, getLocaleDateFormat } from '@angular/common';
import { cargaCol } from 'src/app/model/cargaCol';
import { BrowserModule } from '@angular/platform-browser';
import { AppModule } from 'src/app/app.module';
import { Content } from '@angular/compiler/src/render3/r3_ast';
import { cargaMex } from 'src/app/model/cargaMex';
import { GenericService } from 'src/app/service/generic.service';

@Component({
  selector: 'app-carga-mex',
  templateUrl: './carga-mex.component.html',
  styleUrls: ['./carga-mex.component.css']
})
export class CargaMexComponent implements OnInit {

  showContenido: boolean = false;
  errorMessage: Errors;
  constructor(private router:Router, private cargaService : CargaService, private rest:HttpSenderService, private genericService: GenericService){
    
  }

  ngOnInit(): void {

    this.genericService.getAll(Endpoint.CARGACOL ,this.getAllSuccess,this.callFailure);
    this.files = new Carga();
    this.files.idCarga = 0;
    const date = new Date()
    const dateStr = date.toISOString().slice(0, 10).replace(/-/g, "");
    this.tittle = ("Layout_cargaMX"+dateStr);
    
  }

  correcto = 0;
  error = 0;
  procesados = 0;
  files: Carga;
  typeEndpoint:Endpoint = Endpoint.CARGAMEX;
  //getAllSuccess: any;
  //callFailure: any;
  tableFields:Array<Array<string>>;
  tableHeaders: Array<string>;
  tableName: String = "Tabla de registros";
  tittle:string;
  
  private getAllSuccess = (content: any): void => {this.showContenido = true;}
  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 
  private callFailure = (content: any, error: Errors): void => { this.showContenido = false; this.errorMessage = error; }
  
  
  onSearch(){

      try{
        this.downloadFile();
     }
     catch{
       alert (this.error)
     }
      
  }
  
  downloadFile(): void {
  
      this.rest
        .download(Endpoint.CARGAMEX)
        .subscribe(blob => FileSaver.saveAs(blob, "Layoutcarga.xls")  );
  }
  
  
  onUpload(){

    let form:FormData = new FormData();
    let extension = this.files.archivo.name.substring(this.files.archivo.name.lastIndexOf('.'),this.files.archivo.name.length);
    if (extension !== ".xls" && extension !== ".xlsx"){
        alert("Seleccione un archivo de excel");
        this.router.navigate(['app-form-carga-mex']);}
      

    form.append('idCarga',this.files.idCarga+"");
  
    if(this.files !== undefined && this.files !== null){
      form.append('archivo', this.files.archivo, this.files.archivo.name);
    }
  
      
    try{

        this.cargaService.insert(this.typeEndpoint, form, this.saveSuccess, this.callFailureShowMessage)
        console.log("cargando...")
        
    }catch{  console.log ("error al insertar archivo");}
  
  }
  onFileChange(event: any){
        
      this.files.archivo = event.target.files[0]
      console.log(this.files.archivo.name);

  }   
  
  
  private saveSuccess=(content: any):void=>{

          alert("Elemento Guardado con éxito.");
          this.buildTable(content)
          this.correcto = content.exitosos;
          this.error = content.fallidos;    
          this.procesados = content.procesados;
  }

  
  

  private buildTable (content:any): void {

    this.tableFields = new Array<Array<string>>();    
    this.tableHeaders = new Array<string>();
    console.log(content);

     var data=Object.keys(content).map(function (key){return[Number(key), content[key]];});

     var dataSource = Object.entries(content);

     console.log(content.colaboradores);

    content.colaboradores.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model =new cargaMex;
      model.build(element);
      rowFields.push(model.nombre+"");
      rowFields.push(model.apellidoPat+"");
      rowFields.push(model.apellidoMat+"");
      rowFields.push(model.rfc+"");
      rowFields.push(model.clienteDto.razon+"");
      rowFields.push(model.descError+"");
      this.tableFields.push(rowFields);   
    
    });

    
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Apellido Paterno");
    this.tableHeaders.push("Apellido Materno");
    this.tableHeaders.push("RFC");
    this.tableHeaders.push("Razón Social");
    this.tableHeaders.push("Observaciones");


    

  }

}
