import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Repincidente } from 'src/app/model/repincidente';
import { GenericService } from 'src/app/service/generic.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import * as FileSaver from 'file-saver';
import { HttpSenderService } from 'src/app/service/http-sender.service';
import { UtileriaServicio } from 'src/app/utils/utileriasservicio';




@Component({
  selector: 'app-form-rincidencias',
  templateUrl: './form-rincidencias.component.html',
  styleUrls: ['./form-rincidencias.component.css']
})
export class FormRincidenciasComponent implements OnInit {
  
  showTable: boolean = true;
  showFiltro: boolean = false;
  showContenido: boolean = true;
  showContenidoUdate: boolean = false;

  errorMessage: string;
  canDelete: boolean = false;
  canEdited: boolean = false;
  canCreate: boolean = false;
 

  filterparam = "";
  //Filtros
  filtro1: string[] = ["--Seleccione--"];
  filtro2: string[] = ["--Seleccione--"];
  filtro3: string[] = ["--Seleccione--"];

  filtro2id: string[] = [""];

  filtro1Seleccion: String="--Seleccione--";
  filtro2Seleccion: String="--Seleccione--";
  filtro3Seleccion: String="--Seleccione--";



  
  filtroInput1: String;

  valoresTabla: any;
  idSeleccionado:number;
  fchInicio: string = "";
  fchFin: string = "";
  mdlRepIncidente:Repincidente=new Repincidente;
  utils:UtileriaServicio=new UtileriaServicio();

  constructor(private activatedRoute: ActivatedRoute, private genericService: GenericService, private router: Router,private rest:HttpSenderService) { }

  ngOnInit(): void {

    
    
//llenando centro de costos
this.genericService.getAll(Endpoint.CENTROCOSTOS, this.getAllSuccess, this.callFailure);
  }

  private callFailure = (content: any, error: Errors): void => { this.showTable = false; this.errorMessage = error; }

  private getAllSuccess = (content: any): void => {
    this.showFiltro = true;

    this.valoresTabla = content;
    content.forEach((element: any) => {
      this.filtro1.push(element.razon);


    });
//llenando clientes
    this.genericService.getAll(Endpoint.CLIENTE, this.getAllClientesSuccess, this.callFailure);


  }


  private getAllClientesSuccess = (content: any): void => {
    this.showFiltro = true;

    this.valoresTabla = content;
    content.forEach((element: any) => {
      this.filtro2.push(element.razon);
    });

    this.genericService.getAll(Endpoint.SUBCLIENTE, this.getAllSubClientesSuccess, this.callFailure);

  }
  

  private getAllSubClientesSuccess = (content: any): void => {
    this.showFiltro = true;

    this.valoresTabla = content;
    content.forEach((element: any) => {
      this.filtro3.push(element.razon);
    });

    this.onClean();
  }
  

  onClean(){
    this.filtro1Seleccion="--Seleccione--";
    this.filtro2Seleccion="--Seleccione--";
    this.filtro3Seleccion="--Seleccione--";
  
  
  }

  onSearch(){
    this.mdlRepIncidente.centroCostos=this.filtro1Seleccion;
    this.mdlRepIncidente.cliente=this.filtro2Seleccion;
    this.mdlRepIncidente.clienteSubcontrato=this.filtro3Seleccion;

    this.mdlRepIncidente.fechaInicio=this.fchInicio;
    this.mdlRepIncidente.fechaFin=this.fchFin;


    
    //this.genericService.getParametros(Endpoint.RINCIDENCIAS,this.mdlRepIncidente,this.finDescarga,this.errorMessage);
    this.downloadFile(new String(this.utils.getparametros(Endpoint.RINCIDENCIAS,this.mdlRepIncidente)));
  //  this.downloadFile(this.utils.getparametros(Endpoint.RINCIDENCIAS,this.mdlRepIncidente));

    
  }

  downloadFile(filename: String): void {
    this.rest
      .download(Endpoint.RINCIDENCIAS+filename)
      .subscribe(blob => FileSaver.saveAs(blob, "ReporteIncidencias.xls")  );
  }


 private finDescarga = (content: any): void => {
    alert("descargado con éxito.");
    //this.router.navigate(['/admin/form-corte']); 

  }
  
}
