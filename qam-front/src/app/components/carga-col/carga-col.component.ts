import { Component, OnInit } from '@angular/core';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { ActivatedRoute, Router } from '@angular/router';
import * as FileSaver from 'file-saver';
import { CargaService } from 'src/app/service/carga.service';
import { Carga } from 'src/app/model/carga';
import { HttpSenderService } from 'src/app/service/http-sender.service';
import { CommonModule } from '@angular/common';
import { cargaCol } from 'src/app/model/cargaCol';
import { BrowserModule } from '@angular/platform-browser';
import { AppModule } from 'src/app/app.module';
import { Content } from '@angular/compiler/src/render3/r3_ast';
import { localizedString } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-carga-col',
  templateUrl: './carga-col.component.html',
  styleUrls: ['./carga-col.component.css']
})
export class CargaColComponent implements OnInit {

  constructor(private router:Router, private cargaService : CargaService, private rest:HttpSenderService){
    
  }

  ngOnInit(): void {
    this.files = new Carga();
    this.files.idCarga = 0;
    this.cargando = false;
    this.mostrarTabla = true;
    
  }

  correcto = 0;
  error = 0;
  procesados = 0;
  files: Carga;
  typeEndpoint:Endpoint = Endpoint.CARGACOL;
  getAllSuccess: any;
  callFailure: any;
  tableFields:Array<Array<string>>;
  tableHeaders: Array<string>;
  tableName: String = "Tabla de registros";
  mostrarTabla: boolean = true;
  cargando: boolean=false;
  tittle:string;

  private callFailureShowMessage = (content:any,error:Errors) :void =>{
    alert(error);
    this.mostrarTabla = true;
    this.cargando = false;
  } 

  
  
  onSearch(){

      try{
        this.downloadFile();
     }
     catch{
       alert (this.error)
     }
      
  }
  
  downloadFile(): void {

    const date = new Date()
    const dateStr = date.toISOString().slice(0, 10).replace(/-/g, "");
    this.tittle = ("Layout_cargaCO"+dateStr);
  
      this.rest
        .download(Endpoint.CARGACOL)
        .subscribe(blob => FileSaver.saveAs(blob, this.tittle+".xlsx")  );
  }
  
  
  onUpload(){

    let form:FormData = new FormData();
    let extension = this.files.archivo.name.substring(this.files.archivo.name.lastIndexOf('.'),this.files.archivo.name.length);
    if (extension !== ".xls" && extension !== ".xlsx"){
        alert("Seleccione un archivo de excel");
        this.router.navigate(['app-form-carga-col']);}
      

    form.append('idCarga',this.files.idCarga+"");
  
    if(this.files !== undefined && this.files !== null){
      form.append('archivo', this.files.archivo, this.files.archivo.name);
    }
  
      
  

    this.cargaService.insert(this.typeEndpoint, form, this.saveSuccess, this.callFailureShowMessage)
    console.log("cargando...")
    this.mostrarTabla = false;
    this.cargando = true


  
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
          this.mostrarTabla = true;
          this.cargando = false;
  }

  
  

  private buildTable (content:any): void {

    this.tableFields = new Array<Array<string>>();    
    this.tableHeaders = new Array<string>();
    console.log(content);


     console.log(content.colaboradores);

    content.colaboradores.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model =new cargaCol;
      model.build(element);
      rowFields.push(model.nombre+"");
      rowFields.push(model.apellidoPat+"");
      rowFields.push(model.apellidoMat+"");
      rowFields.push(model.numeroDocumento+"");
      rowFields.push(model.clienteDto.razon+"");
      rowFields.push(model.descError+"");
      this.tableFields.push(rowFields);   
    
    });

    
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Apellido Paterno");
    this.tableHeaders.push("Apellido Materno");
    this.tableHeaders.push("Numero de Documento");
    this.tableHeaders.push("Razón Social");
    this.tableHeaders.push("Observaciones");


    

  }

}
