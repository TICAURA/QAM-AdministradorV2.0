import { Component, OnInit } from '@angular/core';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { ActivatedRoute, Router } from '@angular/router';
import * as FileSaver from 'file-saver';
import { CargaService } from 'src/app/service/carga.service';
import { Carga } from 'src/app/model/carga';
import { HttpSenderService } from 'src/app/service/http-sender.service';
import { cargaCol } from 'src/app/model/cargaCol';
import { GenericService } from 'src/app/service/generic.service';
import { cargaNequi } from 'src/app/model/cargaNequi';
import * as XLSX from 'xlsx';


@Component({
  selector: 'app-carga-col',
  templateUrl: './carga-col.component.html',
  styleUrls: ['./carga-col.component.css']
})
export class CargaColComponent implements OnInit {

  showContenido: boolean = false;
  errorMessage: Errors;
  

  constructor(private cargaService : CargaService, private rest:HttpSenderService, private genericService: GenericService){
    
  }

  ngOnInit(): void {

    this.genericService.getAll(this.typeEndpoint ,this.getAllSuccess,this.callFailure);
    this.files = new Carga();
    this.files.idCarga = 0;
    this.cargando = false;
    this.mostrarTabla = true;


  }
  private getAllSuccess = (content: any): void => {this.showContenido = true;}
  private callFailure = (content: any, error: Errors): void => { this.showContenido = false; this.errorMessage = error; }
    


  filtro1Seleccion: String="--Seleccione--";
  filtro1: string[] = ["--Seleccione--","Layout Colaborador", "Layout Usuario"];
  correcto = 0;
  error = 0;
  procesados = 0;
  files: Carga;
  typeEndpoint:Endpoint = Endpoint.CARGACOL;
  tableFields:Array<Array<string>>;
  tableHeaders: Array<string>;
  tableName: String = "Tabla de registros";
  mostrarTabla: boolean = true;
  cargando: boolean=false;
  tittle:string;
  esDescarga: boolean = false;
  esColaborador: boolean = false;
  esUsuario: boolean = false

  private callFailureShowMessage = (content:any,error:Errors) :void =>{
    alert(content);
    console.log(content);
    this.mostrarTabla = true;
    this.cargando = false;
  } 

  
  seleccionarOpcionLayout(){

    if (this.filtro1Seleccion.toString() == this.filtro1[0].toString()){
      this.esDescarga=false;
    }else{
      
      this.esDescarga=true;
    }
    
  }

  descargaLayout(){

    if (this.filtro1Seleccion == this.filtro1[1]){
      try{
        const date = new Date()
        const dateStr = date.toISOString().slice(0, 10).replace(/-/g, "");
        this.tittle = ("Layout_carga_colaboradores_CO"+dateStr);

        const linkSource = `../../../assets/Layout_carga_colaborador_CO.xlsx`;
        const downloadLink = document.createElement('a');

        downloadLink.href = linkSource;
        downloadLink.download = this.tittle;
        downloadLink.click();
      }
      catch{alert (this.error)}
    }else{
        const date = new Date()
        const dateStr = date.toISOString().slice(0, 10).replace(/-/g, "");
        this.tittle = ("Layout_carga_usuarios_CO"+dateStr);

        const linkSource = `../../../assets/Layout_carga_usuario_CO.xlsx`;
        const downloadLink = document.createElement('a');

        downloadLink.href = linkSource;
        downloadLink.download = this.tittle;
        downloadLink.click();
    }

  }
  
  // downloadFile(): void {

  //   const date = new Date()
  //   const dateStr = date.toISOString().slice(0, 10).replace(/-/g, "");
  //   this.tittle = ("Layout_cargaCO"+dateStr);
  //   this.rest.download(Endpoint.CARGACOL).subscribe(blob => FileSaver.saveAs(blob, this.tittle+".xlsx")  )
  //   if ( this.rest.header2.Authorization==null ||  this.rest.header2.Authorization ==""){
  //     alert("No tiene los permisos para ejetucar operaciones en esta información.");
  //   }
  // }
  
  cargarArchivo(){

    let form:FormData = new FormData();

    form.append('idCarga',this.files.idCarga+"");
  
    if(this.files !== undefined && this.files !== null){
      form.append('archivo', this.files.archivo, this.files.archivo.name);
    }
  
    try {
      
    //this.cargaService.insertColb(this.typeEndpoint, form, this.saveSuccess, this.callFailureShowMessage)
    if (this.esColaborador){

      console.log("Carga Colaborador...");
      this.mostrarTabla = false;
      this.cargando = true;
      this.cargaService.insertColb(this.typeEndpoint, form, this.saveSuccess, this.callFailureShowMessage)

    }else if (this.esUsuario) {
      
      console.log("Carga Nequi...");
      this.mostrarTabla = false;
      this.cargando = true;
      this.cargaService.insertUser(this.typeEndpoint, form, this.saveSuccessNequi, this.callFailureShowMessage)

    }
    
    } catch (error) {

        this.callFailureShowMessage

    }  
   

  }

  onFileChange(event: any){
        
    
    this.files.archivo = event.target.files[0];

    this.fileToInputStream(this.files.archivo)
    .then((inputStream) => this.getSecondSheetName(inputStream))
    .then((secondSheetName) => {
      console.log('sheet name:', secondSheetName);
      this.esUsuario = true;
      this.esColaborador = false;
      
    })
    .catch((error) => {
      this.esColaborador = true;
      this.esUsuario = false;
      
    });
    

  }   
  
  private saveSuccess=(content: any):void=>{

    this.cargaService.getAll(this.typeEndpoint,this.getSuccess,this.callFailureShowMessage,content.idCargaMasiva);
    this.buildTable(content)
    this.correcto = content.exitosos;
    this.error = content.fallidos;    
    this.procesados = content.procesados;

  }

  private saveSuccessNequi=(content: any):void=>{

    this.cargaService.getAllNequi(this.typeEndpoint,this.getSuccessNequi,this.callFailureShowMessage,content.idCargaMasiva);
    
    this.buildTableNequi(content)
    this.correcto = content.exitosos;
    this.error = content.fallidos;    
    this.procesados = content.procesados;

  }

  private getSuccessNequi=(content:any):void=>{

    
    this.buildTableNequi(content)
    this.mostrarTabla = true;
    this.cargando = false;
    alert("Elemento guardado con éxito.");

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

  private buildTableNequi (content:any): void {

    this.tableFields = new Array<Array<string>>();    
    this.tableHeaders = new Array<string>();
    
    if(content.usuarioDto !== undefined){
    content.usuarioDto.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model =new cargaNequi;
      model.build(element);
      rowFields.push(model.nombre+"");
      rowFields.push(model.primerApellido+"");
      if(model.segundoApellido == null){model.segundoApellido = ""}
      rowFields.push(model.segundoApellido+"");
      rowFields.push(model.tipoDocumentoId+"");
      rowFields.push(model.celular+"");
      //rowFields.push(model.cuentaNequi+"");
      rowFields.push(model.numeroDocumentoId+"");
      rowFields.push(model.observacioCarga+"");
      this.tableFields.push(rowFields);   
    
    });}

    
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Apellido Paterno");
    this.tableHeaders.push("Apellido Materno");
    this.tableHeaders.push("Tipo Documento Id");
    this.tableHeaders.push("Celular");  
    //this.tableHeaders.push("Cuenta Nequi");
    this.tableHeaders.push("Número Documento ID")
    this.tableHeaders.push("Observaciones");


  }



 fileToInputStream(file: File): Promise<ArrayBuffer> {
  return new Promise<ArrayBuffer>((resolve, reject) => {
    const reader = new FileReader();

    reader.onload = (event: any) => {
      const arrayBuffer = event.target.result;
      resolve(arrayBuffer);
    };

    reader.onerror = (event: ProgressEvent<FileReader>) => {
      reject(new Error('Error al leer el archivo'));
    };

    reader.readAsArrayBuffer(file);
  });
}

getSecondSheetName(arrayBuffer: ArrayBuffer): Promise<string> {
  return new Promise<string>((resolve, reject) => {
    const workbook = XLSX.read(new Uint8Array(arrayBuffer), { type: 'array' });

    if (workbook.SheetNames.length >= 2) {
      const secondSheetName = workbook.SheetNames[1];
      resolve(secondSheetName);
    } else {
      reject('No se encontró la segunda hoja en el archivo Excel.');
    }
  });
}



}