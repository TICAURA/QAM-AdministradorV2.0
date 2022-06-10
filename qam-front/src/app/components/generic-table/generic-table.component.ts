import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Endpoint } from 'src/app/utils/endpoint';
import { GenericService } from 'src/app/service/generic.service';
import { Usuario } from 'src/app/model/usuario';
import { Catalogue } from 'src/app/model/catalogue';
import { Cliente } from 'src/app/model/cliente';
import { Interfaz } from 'src/app/model/interfaz';
import { Servicio } from 'src/app/model/servicio';
import { Errors } from 'src/app/utils/errors';
import { Catalogos } from 'src/app/utils/catalogos';
import { Parametro } from 'src/app/model/parametro';
import { DatePipe } from '@angular/common';
import { Promocion } from 'src/app/model/promocion';
import { Segmento } from 'src/app/model/segmento';

@Component({
  selector: 'app-generic-table',
  templateUrl: './generic-table.component.html',
  styleUrls: ['./generic-table.component.css']
})
export class GenericTableComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private genericService: GenericService, private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params: any) => {
      this.type = params.get('type');
      this.genericService.getAll(this.type, this.getAllSuccess, this.callFailure);
    });
  }

  url: string;
  type: Endpoint;
  tableHeaders: Array<string>;
  tableFields: Array<Array<string>>;
  showTable: boolean = true;
  showFiltro: boolean = false;
  tableName: string;
  errorMessage: string;
  canDelete: boolean = false;
  canEdited: boolean = false;
  canCreate: boolean = true;

  filterparam = "";
  //Filtros
  filtro1: string[] = ["--Seleccione--", "Global", "Particular"];
  filtro2: string[] = ["--Seleccione--"];

  filtro1Seleccion: String;
  filtro2Seleccion: String;

  filtroInput1: string;
  

  valoresTabla: any;

  valoresTablaFiltrada: any = [];

  Segmentos: any = [];





  private getAllSuccess = (content: any): void => {
    this.showTable = true;
    this.canCreate = true;
    this.valoresTabla = content;
    this.showFiltro = false;

    switch (this.type) {
      case Endpoint.USUARIO: { this.tableName = "Usuarios"; this.url = "/admin/form-usuario"; this.canDelete = true; this.buildForUsers(content); return; }
      case Endpoint.CLIENTE: { this.tableName = "Clientes"; this.url = "/admin/form-cliente"; this.canDelete = false; this.buildForClients(content); return; }
      case Endpoint.INTERFAZ: { this.tableName = "Interfaces"; this.url = "/admin/form-interfaz"; this.canDelete = true; this.buildForInterfaces(content); return; }
      case Endpoint.SERVICIO: { this.tableName = "Servicios"; this.url = "/admin/form-servicio"; this.canDelete = true; this.buildForServices(content); return; }
      case Endpoint.ROL: { this.tableName = "Roles"; this.url = "/admin/form-rol"; this.canDelete = true; this.buildForRols(content); return; }
      case Endpoint.PROMOCION: { this.tableName = "Promociones"; this.url = "/admin/form-promocion"; this.canDelete = true; this.buildForPromo(content); return; }
      case Endpoint.PERMISO: { this.tableName = "Permisos"; this.url = "/admin/form-generic/" + Catalogos.PERMISO; this.canDelete = false; this.buildForCatalogues(content); return; }
      case Endpoint.DISPERSOR: { this.tableName = "Dispersores"; this.url = "/admin/form-generic/" + Catalogos.DISPERSOR; this.canDelete = false; this.buildForCatalogues(content); return; }
      case Endpoint.FACTURA: { this.tableName = "Facturadores"; this.url = "/admin/form-generic/" + Catalogos.FACTURA; this.canDelete = false; this.buildForCatalogues(content); return; }
      case Endpoint.TIPO_AMBIENTE: { this.tableName = "Tipo de ambientes"; this.url = "/admin/form-generic/" + Catalogos.TIPO_AMBIENTE; this.canDelete = true; this.buildForCatalogues(content); return; }
      case Endpoint.TIPO_SERVICIO: { this.tableName = "Tipo de servicios"; this.url = "/admin/form-generic/" + Catalogos.TIPO_SERVICIO; this.canDelete = true; this.buildForCatalogues(content); return; }
      
      case Endpoint.PARAMETROS: {
        content.forEach((elemento:any)=>{

          if(elemento.segmentoId!=null){
            console.log(elemento.parametroName)
          }
        });
        this.showFiltro = true; this.tableName = "Parámetros"; this.url = "/admin/form-parametro/"; this.canCreate = false; this.canDelete = false; this.canEdited = true;  this.genericService.getAll(Endpoint.SEGMENTO, this.getAllSegmentoSuccess, this.callCSegmentosFailure);

        return;
      }

      case Endpoint.SEGMENTO: { this.tableName = "Segmentos"; this.url = "/admin/form-segmento/"; this.canDelete = false; this.buildForSegmentos(content); return; }

      default: { return; }

    }

  }

  private buildForUsers(content: any): void {

    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();

    content.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model: Usuario = new Usuario();
      model.build(element);
      rowFields.push(model.userId + "");
      rowFields.push(model.name + "");
      rowFields.push(model.user + "");
      rowFields.push(model.fechaRegistro + "");
      rowFields.push(model.activo ? "Activo" : "Inactivo");
      this.tableFields.push(rowFields);
    });
    this.tableHeaders.push("ID");
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Usuario");
    this.tableHeaders.push("Fecha registro");
    this.tableHeaders.push("Activo");
    this.tableHeaders.push("Borrar");
  }
  private buildForClients(content: any): void {
    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();

    content.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model: Cliente = new Cliente();
      model.build(element);
      rowFields.push(model.idClient + "");
      rowFields.push(model.razon + "");
      rowFields.push(model.rfc + "");
      rowFields.push(model.curp + "");
      rowFields.push(model.dispersorName + "");
      rowFields.push(model.facturadorName + "");
      rowFields.push(model.servicioName + "");
      rowFields.push(model.interfazName + "");
      this.tableFields.push(rowFields);
    });
    this.tableHeaders.push("ID");
    this.tableHeaders.push("Razon social");
    this.tableHeaders.push("RFC");
    this.tableHeaders.push("Curp");
    this.tableHeaders.push("Dispersor");
    this.tableHeaders.push("Facturador");
    this.tableHeaders.push("Servicio");
    this.tableHeaders.push("Interfaz");


  }
  private buildForInterfaces(content: any): void {
    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();

    content.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model: Interfaz = new Interfaz();
      model.build(element);
      rowFields.push(model.idInterfaz + "");
      rowFields.push(model.nombre + "");
      rowFields.push(model.colorGlobalPrimario + "");
      rowFields.push(model.colorGlobalSecundario + "");
      rowFields.push(model.fechaRegistro + "");
      rowFields.push(model.activo ? "Activo" : "Inactivo");
      this.tableFields.push(rowFields);
    });
    this.tableHeaders.push("ID");
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Color primario");
    this.tableHeaders.push("Color secundario");
    this.tableHeaders.push("Fecha registro");
    this.tableHeaders.push("Activo");
    this.tableHeaders.push("Borrar");
  }
  private buildForServices(content: any): void {
    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();

    content.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model: Servicio = new Servicio();
      model.build(element);
      rowFields.push(model.idServicio + "");
      rowFields.push(model.nombre);
      rowFields.push(model.tipoAmbienteNombre + "");
      rowFields.push(model.tipoServicioNombre + "");
      rowFields.push(model.endpoint + "");
      rowFields.push(model.activo ? "Activo" : "Inactivo");
      this.tableFields.push(rowFields);
    });
    this.tableHeaders.push("ID");
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Ambiente");
    this.tableHeaders.push("Servicio");
    this.tableHeaders.push("EndPoint");
    this.tableHeaders.push("Activo");
    this.tableHeaders.push("Borrar");
  }
  private buildForRols(content: any): void {
    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();
    content.forEach((element: any) => {
      let catalogueFields = new Array<string>();
      let catalogue: Catalogue = new Catalogue();
      catalogue.build(element);
      catalogueFields.push(catalogue.id + "");
      catalogueFields.push(catalogue.nombre + "");
      catalogueFields.push(catalogue.descripcion + "");
      catalogueFields.push(catalogue.activo + "");
      this.tableFields.push(catalogueFields);
    });
    this.tableHeaders.push("ID");
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Descripción");
    this.tableHeaders.push("Activo");
    this.tableHeaders.push("Borrar");
  }
  private buildForCatalogues(content: any): void {
    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();
    content.forEach((element: any) => {
      let catalogueFields = new Array<string>();
      let catalogue: Catalogue = new Catalogue();
      catalogue.build(element);
      catalogueFields.push(catalogue.id + "");
      catalogueFields.push(catalogue.nombre + "");
      catalogueFields.push(catalogue.descripcion + "");
      catalogueFields.push(catalogue.activo + "");
      this.tableFields.push(catalogueFields);
    });
    this.tableHeaders.push("ID");
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Descripción");
    this.tableHeaders.push("Activo");

    if (this.canDelete) {
      console.log("Can borrar");
      this.tableHeaders.push("Borrar");
    }

  }
  

  private buildForPromo(content: any): void {
    const datepipe: DatePipe = new DatePipe('en-US');

    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();

    content.forEach((element: any) => {
      let rowFields = new Array<string>();
      let model: Promocion = new Promocion();
      model.build(element);
      rowFields.push(model.idPromocion + "");
      rowFields.push(model.nombre + "");
      rowFields.push(model.codigoPromo + "");

      if (element.fechaInicio !== undefined && element.fechaInicio !== null) {
        const fechaI: any = datepipe.transform(element.fechaInicio, 'dd-MM-YYYY HH:mm:ss');
        rowFields.push(fechaI !== null ? fechaI : "");
      }
      if (element.fechaFin !== undefined && element.fechaFin !== null) {
        const fechaF: any = datepipe.transform(element.fechaFin, 'dd-MM-YYYY HH:mm:ss');
        rowFields.push(fechaF !== null ? fechaF : "");
      }

      rowFields.push(model.activo ? "Activo" : "Inactivo");
      this.tableFields.push(rowFields);
    });
    this.tableHeaders.push("ID");
    this.tableHeaders.push("Nombre");
    this.tableHeaders.push("Código");
    this.tableHeaders.push("Fecha de inicio");
    this.tableHeaders.push("Fecha de fin");
    this.tableHeaders.push("Activo");
    this.tableHeaders.push("Borrar");
  }

  createData(): void {
    this.router.navigate([this.url], { queryParams: { requestedId: -1 } });
  }

  private callFailure = (content: any, error: Errors): void => { this.showTable = false; this.errorMessage = error; }
  private callFailureShowMessage = (content: any, error: Errors): void => { alert(error); }
  eliminar(id: string): void {
    const eliminationId: number = Number(id); //TODO IMPLEMENTAR
    this.genericService.delete(this.type, eliminationId,
      (content: any) => { this.tableFields = this.tableFields.filter((element) => { return element[0] !== eliminationId + ""; }); },
      this.callFailureShowMessage
    )

  }

  editar(row: any): void {

    this.router.navigate([this.url], { queryParams: { requestedId: row[0], valor: row[4], parametro: row[2] } });

  }

  private buildForParametros(content: any): void {
    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();
    content.forEach((element: any) => {
      let parametroFields = new Array<string>();
      let parametro: Parametro = new Parametro();


      parametro.build(element);
      parametroFields.push(parametro.idParametro + "");


      parametroFields.push(this.getSegmentoNombre(parametro.idSegmento) + "");
      parametroFields.push(parametro.parametroName + "");
      parametroFields.push(parametro.descripcion + "");
      parametroFields.push(parametro.valor + "");
      parametroFields.push(parametro.global + "");
      parametroFields.push(parametro.activo + "");

      this.tableFields.push(parametroFields);
    });
    this.tableHeaders.push("Segmento");
    this.tableHeaders.push("Parámetros");
    this.tableHeaders.push("Descripción");
    this.tableHeaders.push("Valor");
    this.tableHeaders.push("Global");
    this.tableHeaders.push("Activo");


    if (this.canEdited) {
      console.log("Can Editar");
      this.tableHeaders.push("Acción");
    }

  }

  getSegmentoNombre(id:Number){
    var segmento ="Sin asignar";
    this.Segmentos.forEach((elemento:any)=> {

      if(id!=null){

        if(id==elemento.segmentoId){
          segmento=elemento.nombreCorto;
        }
    }
    });

    return segmento;
  }

  getSegmentoid(nombre:String){
    var segmento =-1;
    this.Segmentos.forEach((elemento:any)=> {

      if(nombre==elemento.nombreCorto){
        segmento=elemento.segmentoId;
        console.info("id segmento :"+segmento);

      }
    });

    return segmento;
  }

  onSearch() {
    console.info("Filtrando");

    this.valoresTablaFiltrada=[];

    if (this.filtro2Seleccion == "--Seleccione--" && this.filtro1Seleccion == "--Seleccione--" && this.filtroInput1 === null && this.filtroInput1 == "") {
      alert("Debe ingresar al menos un dato ");
    } else {


      if (this.filtro1Seleccion != "--Seleccione--") {
        this.filtradoPosSeleccion();

      }
      if (this.filtro2Seleccion != "--Seleccione--") {
        this.filtradoPorSeleccion2();

      }

      if(this.filtroInput1 != null && this.filtroInput1 != ""){
        this.filtradoPorInput();
      }


      this.buildForParametros(this.valoresTablaFiltrada);

    }


  }

  private filtradoPosSeleccion() {
    let valtem = [];

    if (this.valoresTablaFiltrada.length > 0) {
      valtem = this.valoresTablaFiltrada;
    } else {
      valtem = this.valoresTabla;

    }
    valtem.forEach((element: any) => {


      switch (this.filtro1Seleccion) {

        case "Global": {
          if (element.global) {
            this.valoresTablaFiltrada.push(element);
          }
        } break;
        case "Particular": {
          if (!element.global) {
            this.valoresTablaFiltrada.push(element);
          }
        } break;

        default: { return; }

      }


    });
  }


  private filtradoPorSeleccion2() {
    let valtem = [];

    if (this.valoresTablaFiltrada.length > 0) {
      valtem = this.valoresTablaFiltrada;
      this.valoresTablaFiltrada=[]
    } else {
      valtem = this.valoresTabla;

    }
    valtem.forEach((element: any) => {

      if(element.idSegmento!==null && element.idSegmento==this.getSegmentoid(this.filtro2Seleccion)){
        this.valoresTablaFiltrada.push(element)
      }


    });
  }

  private filtradoPorInput() {
    let valtem = [];

    if (this.valoresTablaFiltrada.length > 0) {
      valtem = this.valoresTablaFiltrada;
      this.valoresTablaFiltrada=[]
    } else {
      valtem = this.valoresTabla;

    }
    valtem.forEach((element: any) => {

      if( String(element.idParametro).includes(this.filtroInput1 )){
      this.valoresTablaFiltrada.push(element)
      }

    });
  }




  onClean() {


    this.filtro1Seleccion = "--Seleccione--";
    this.filtro2Seleccion = "--Seleccione--";
    this.filtroInput1 = "";

    this.buildForParametros(this.valoresTabla);


  }





  private callCSegmentosFailure = (content: any, error: Errors): void => { this.showTable = false; this.errorMessage = error; }

  private getAllSegmentoSuccess = (content: any): void => {
    this.showFiltro = true;

    this.filtro2 = ["--Seleccione--"];

    this.filtro1Seleccion = "--Seleccione--";
    this.filtro2Seleccion = "--Seleccione--";
    this.Segmentos=[];
    this.Segmentos=content;
    content.forEach((element: any) => {
      this.filtro2.push(element.nombreCorto);

    });

    this.buildForParametros(this.valoresTabla)

  }


  private buildForSegmentos(content: any): void {
    const datepipe: DatePipe = new DatePipe('en-US');

    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();
    content.forEach((element: any) => {
      let segmentoFields = new Array<string>();
      let segmento: Segmento = new Segmento();
      segmento.build(element);
      segmentoFields.push(segmento.segmentoId+ "");
      segmentoFields.push(segmento.descripcion + "");
      segmentoFields.push(segmento.nombreCorto + "");
      segmentoFields.push(segmento.esActivo + "");

      if (segmento.fchAlta !== undefined && segmento.fchAlta !== null) {
        const fechaI: any = datepipe.transform(segmento.fchAlta, 'dd-MM-YYYY HH:mm:ss');
        segmentoFields.push(fechaI !== null ? fechaI : "");
      }

      this.tableFields.push(segmentoFields);
    });
    this.tableHeaders.push("ID");
    this.tableHeaders.push("Descripción");
    this.tableHeaders.push("Nombre corto");
    this.tableHeaders.push("Activo");
    this.tableHeaders.push("Fecha alta");


    if (this.canDelete) {
      console.log("Can borrar");
      this.tableHeaders.push("Borrar");
    }

  }

}