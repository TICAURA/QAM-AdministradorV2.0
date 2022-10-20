import { Component, OnInit } from '@angular/core';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { ActivatedRoute, Router } from '@angular/router';
import * as FileSaver from 'file-saver';
import { CargaService } from 'src/app/service/carga.service';
import { Carga } from 'src/app/model/carga';
import { HttpSenderService } from 'src/app/service/http-sender.service';
import { cargaCol } from 'src/app/model/cargaCol';

@Component({
  selector: 'app-carga-col',
  templateUrl: './carga-col.component.html',
  styleUrls: ['./carga-col.component.css']
})
export class CargaColComponent implements OnInit {
  

  constructor(private cargaService : CargaService, private rest:HttpSenderService){
    
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
    console.log(content);
    this.mostrarTabla = true;
    this.cargando = false;
  } 

  
  
  onSearch(){

    try{
        this.downloadFile();
    }
    catch{alert (this.error)}

  }
  
  downloadFile(): void {

    const date = new Date()
    const dateStr = date.toISOString().slice(0, 10).replace(/-/g, "");
    this.tittle = ("Layout_cargaCO"+dateStr);
    this.rest.download(Endpoint.CARGACOL).subscribe(blob => FileSaver.saveAs(blob, this.tittle+".xls")  )
    if ( this.rest.header2.Authorization==null ||  this.rest.header2.Authorization ==""){
      alert("No tiene los permisos para ejetucar operaciones en esta información.");
    }
  }
  
  onUpload(){

    let form:FormData = new FormData();

    form.append('idCarga',this.files.idCarga+"");
  
    if(this.files !== undefined && this.files !== null){
      form.append('archivo', this.files.archivo, this.files.archivo.name);
    }
  
      
  
    this.cargaService.insert(this.typeEndpoint, form, this.saveSuccess, this.callFailureShowMessage)
    console.log("cargando...")
    this.mostrarTabla = false;
    this.cargando = true;

  }

  onFileChange(event: any){
        
      this.files.archivo = event.target.files[0]
      console.log(this.files.archivo.name);

  }   
  
  private saveSuccess=(content: any):void=>{

    this.cargaService.getAll(this.typeEndpoint,this.getSuccess,this.callFailureShowMessage,content.idCargaMasiva);
    this.buildTable(content)
    this.correcto = content.exitosos;
    this.error = content.fallidos;    
    this.procesados = content.procesados;

  }

  private getSuccess=(content:any):void=>{

    
    this.buildTable(content)
    this.mostrarTabla = true;
    this.cargando = false;
    alert("Elemento guardado con éxito.");

  }  
  

  private buildTable (content:any): void {

    this.tableFields = new Array<Array<string>>();    
    this.tableHeaders = new Array<string>();

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
