import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from 'src/app/model/cliente';
import { CorteAnticipo } from 'src/app/model/corteanticipo';
import { GenericService } from 'src/app/service/generic.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';

@Component({
  selector: 'app-form-corte',
  templateUrl: './form-corte.component.html',
  styleUrls: ['./form-corte.component.css']
})
export class FormCorteComponent implements OnInit {

  tableHeaders: Array<string>;
  tableFields: Array<Array<string>>;
  showTable: boolean = true;
  showFiltro: boolean = false;
  showContenido: boolean = true;
  showContenidoUdate: boolean = false;

  tableName: string;
  errorMessage: string;
  canDelete: boolean = false;
  canEdited: boolean = false;
  canCreate: boolean = false;
  type: Endpoint;
  corte: CorteAnticipo;

  filterparam = "";
  //Filtros
  filtro1: string[] = ["--Seleccione--", "Semanal", "Catorcenal", "Quincenal", "Mensual"];
  filtro2: string[] = ["--Seleccione--"];
  filtro2id: string[] = [""];

  filtro1Seleccion: String;
  filtro2Seleccion: String;

  clientData: Cliente[]=[];
  cliente: Cliente;

  valoresTabla: any;
  idSeleccionado: number;
  fchInicio: string = "";
  fchFin: string = "";
  fchPago: string = "";
  fchCorteIncidencias: string = "";
  CentroCostesNombre: string = "";

  corteUpdateData: CorteAnticipo = new CorteAnticipo;

  constructor(private activatedRoute: ActivatedRoute, private genericService: GenericService, private router: Router) { }



  ngOnInit(): void {

    //llenando centro de costos
    this.genericService.getAll(Endpoint.CENTROCOSTOS, this.getAllSuccess, this.callFailure);


  }

  private callFailure = (content: any, error: Errors): void => { this.showTable = false; this.errorMessage = error; }

  private getAllSuccess = (content: any): void => {
    this.showFiltro = true;

    this.valoresTabla = content;
    content.forEach((element: any) => {
      this.filtro2.push(element.razon);
      this.cliente = new Cliente();
      this.cliente.build(element);
      this.clientData.push(this.cliente)

    });


    this.filtro1Seleccion = "--Seleccione--";
    this.filtro2Seleccion = "--Seleccione--";

  }
  private getCortesSuccess = (content: any): void => {
    this.showFiltro = true;
    this.showTable = true;
    this.canEdited = true;

    this.tableName = "Corte anticipo";
    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();

    content.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model: CorteAnticipo = new CorteAnticipo();
      model.build(element);
      rowFields.push(model.corteId + "");
      rowFields.push(this.getPeriodicidad(model.periodicidad));
      this.getCentrosCosto(model.centroCostosId);
      rowFields.push(this.CentroCostesNombre);
      rowFields.push(model.esActivo ? "Activo" : "Inactivo");
      rowFields.push(model.fchInicio+"");
      rowFields.push(model.fchFin + "");
      rowFields.push(model.fchCorteIncidencias + "");
      rowFields.push(model.fchPago + "");

      this.tableFields.push(rowFields);
    });
    this.tableHeaders.push("Periodicidad");
    this.tableHeaders.push("Centro de costos");
    this.tableHeaders.push("Activo");
    this.tableHeaders.push("Fecha inicio");
    this.tableHeaders.push("Fecha fin");
    this.tableHeaders.push("Fecha corte incidencias");
    this.tableHeaders.push("Fecha pago");
    this.tableHeaders.push("Acción");


  }

  onSearch() {

    this.filtro1Seleccion;
    this.filtro2Seleccion;
    this.corte = new CorteAnticipo;
    this.getparametros(String(this.filtro2Seleccion));
    this.corte.centroCostosId = Number(this.idSeleccionado);
    this.corte.periodicidad = this.filtro1Seleccion.charAt(0);//this.filtro2Seleccion;

    this.genericService.getParametros(Endpoint.CORTE, this.corte, this.getCortesSuccess, this.callFailure)
  }

  onClean() {

    this.showTable = false;

    this.filtro1Seleccion = "";
    this.filtro2Seleccion = "";

    this.filtro1 = ["--Seleccione--", "Semanal", "Catorcenal", "Quincenal", "Mensual"];
    this.filtro2 = ["--Seleccione--"];
    this.filtro2id = [""];

    this.genericService.getAll(Endpoint.CENTROCOSTOS, this.getAllSuccess, this.callFailure);


  }
  onDescart() {

    this.showContenido = true;
    this.showContenidoUdate = false;

  }

 
  editar(content: any) {
    this.showContenidoUdate = true;
    this.showContenido = false;
    this.fchInicio = content[4];
    this.fchFin = content[5];
    this.fchPago = content[7];
    this.fchCorteIncidencias = content[6];
    this.corteUpdateData.corteId = content[0];


  }
  guardar() {

    console.info("Enviando");
    this.corteUpdateData.fchInicio = new Date(this.fchInicio);
    this.corteUpdateData.fchFin = new Date(this.fchFin);
    this.corteUpdateData.fchPago = new Date(this.fchPago);
    this.corteUpdateData.fchCorteIncidencias = new Date(this.fchCorteIncidencias);


    this.genericService.modify(Endpoint.CORTE, this.corteUpdateData, this.updateSuccess, this.callFailure);

  }


  private updateSuccess = (content: any): void => {
    alert("Elemento actualizado con éxito.");
    this.router.navigate(['/admin/form-corte']);
  }
  getparametros(data: string) {
    this.valoresTabla.forEach((element: any) => {
      if (element.razon == data) {
        this.idSeleccionado = element.idClient;
      }

    });
  }

  getPeriodicidad(valor: String) {

    switch (valor) {
      case "S": { return "Semanal"; }
      case "C": { return "Catorcenal"; }
      case "Q": { return "Quincenal"; }
      case "M": { return "Mensual"; }
      default: return "";

    }

  }

  getCentrosCosto(valor: Number) {

    this.CentroCostesNombre="";
    this.clientData.forEach((element: any) => {

      if (valor === element.idClient) {
        this.CentroCostesNombre= element.razon;
      }
    });
    ;
  }

}
