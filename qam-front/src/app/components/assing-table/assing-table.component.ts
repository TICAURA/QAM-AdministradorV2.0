import { Component, OnInit, Input } from '@angular/core';
import { AssignEndpoint } from 'src/app/utils/assign-endpoint';
import { GenericAssignService } from 'src/app/service/generic-assign.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Relation } from 'src/app/model/relation';
import { GenericService } from 'src/app/service/generic.service';
import { Cliente } from 'src/app/model/cliente';
import { Catalogue } from 'src/app/model/catalogue';
import { Errors } from 'src/app/utils/errors';

@Component({
  selector: 'app-assing-table',
  templateUrl: './assing-table.component.html',
  styleUrls: ['./assing-table.component.css']
})
export class AssingTableComponent implements OnInit {

  @Input()
  type: AssignEndpoint;

  @Input()
  fatherId: number;

  showTable:boolean = true;

  relations:Relation[]=new Array<Relation>();

  tableName:string;

  tableFields: Array<Array<string>>;

  tableHeaders: Array<string>;

  errorMessage:Errors;

  //<app-assing-table [type]="{{ AssignEndpoint.USER_CLIENT }}"  [fatherId]={{ this.usuario.userId }}></app-assing-table>

  constructor(private genericAssignService:GenericAssignService, private genericService:GenericService ) { }

  ngOnInit(): void {
    this.setTableName();
    //load assign
    this.genericAssignService.getAll(this.type,this.fatherId,this.getAllRelationsSuccess,this.callFailure);

  }


  private setTableName(){
    switch(this.type){
      case AssignEndpoint.USER_CLIENT:{ this.tableName="Clientes"; return;}
      case AssignEndpoint.USER_ROL:{ this.tableName="Roles"; return;}
      default:{ this.tableName="Permisos"; return;}
    }
  }

  private getCatalogue() :Endpoint{
    if(this.type === AssignEndpoint.USER_CLIENT){return Endpoint.CLIENTE;}
    else if(this.type === AssignEndpoint.USER_ROL){return Endpoint.ROL;}
    else{return Endpoint.PERMISO;}
  }

   checkLink(id:number):number{
    const relationId : number = id;
    let found : number = 0;
    this.relations.forEach(relation => {
      if(relationId === relation.child){found = 1;}
    });
    return found;
  }

   link(row:number):void{
    const rowData:Array<string> = this.tableFields[row];
    const relationId : number = Number(rowData[0]);
    this.tableFields[row][rowData.length-1] = '2';
    let relation:Relation = new Relation();
    relation.father=this.fatherId;
    relation.child=relationId;
    this.genericAssignService.link(this.type,this.fatherId,
      (content: any)=>{
        this.relations.push(relation);
        this.tableFields[row][rowData.length-1] = '1';
      },
      this.callFailureShowMessage
      ,relation);
  }
   break(row:number):void{
    const rowData:Array<string> = this.tableFields[row];
    const relationId : number = Number(rowData[0]);
    this.tableFields[row][rowData.length-1] = '3';
    let relation:Relation = new Relation();
    relation.father=this.fatherId;
    relation.child=relationId;
    this.genericAssignService.break(this.type,this.fatherId,
      (content: any)=>{
        this.relations = this.relations.filter((element)=>{return element.child !== relation.child; });
        this.tableFields[row][rowData.length-1] = '0';
      },
      this.callFailureShowMessage
      ,relation);
  }

  private getAllRelationsSuccess = (content:any) :void =>{

    this.relations = new Array();

    content.forEach((element:any) => {
      let relation:Relation = new Relation();
      relation.build(element);
      this.relations.push(relation);
    });

    //load catalogue
    this.genericService.getAll(this.getCatalogue(),this.getAllCatogueSuccess,this.callFailure)

   }

  private getAllCatogueSuccess=(content:any):void =>{
    this.tableFields = new Array<Array<string>>();
    this.tableHeaders = new Array<string>();
    if(this.type === AssignEndpoint.USER_CLIENT){
      content.forEach((element:any) => {
        let clienteFields = new Array<string>();
        let cliente:Cliente = new Cliente();
        cliente.build(element);
        clienteFields.push(cliente.idClient+"");
        clienteFields.push(cliente.razon+"");
        clienteFields.push(cliente.rfc+"");
        clienteFields.push(cliente.curp+"");
        clienteFields.push(this.checkLink(cliente.idClient)+"");
        this.tableFields.push(clienteFields);
      });
     this.tableHeaders.push("ID");
     this.tableHeaders.push("Razon social");
     this.tableHeaders.push("RFC");
     this.tableHeaders.push("CURP");
     this.tableHeaders.push("LINK");
    }else{
      content.forEach((element:any) => {
        let catalogueFields = new Array<string>();
        let catalogue:Catalogue = new Catalogue();
        catalogue.build(element);
        catalogueFields.push(catalogue.id+"");
        catalogueFields.push(catalogue.nombre+"");
        catalogueFields.push(catalogue.descripcion+"");
        catalogueFields.push(this.checkLink(catalogue.id)+"");
        this.tableFields.push(catalogueFields);
      });
     this.tableHeaders.push("ID");
     this.tableHeaders.push("Nombre");
     this.tableHeaders.push("Descripción");
     this.tableHeaders.push("LINK");
    }
   }

   private callFailure = (content:any,error:Errors) :void =>{this.showTable=false;this.errorMessage=error;} 
   private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 

}
