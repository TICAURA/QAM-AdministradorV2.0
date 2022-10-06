import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/model/cliente';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';
import { Router } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { Catalogue } from 'src/app/model/catalogue';
import { Servicio } from 'src/app/model/servicio';
import { Interfaz } from 'src/app/model/interfaz';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-form-carga',
  templateUrl: './form-carga.component.html',
  styleUrls: ['./form-carga.component.css']
})

export class FormCargaComponent implements OnInit {
  showTable: boolean;
  errorMessage: Errors;
  showFiltro: boolean = true;
  showContenido: boolean = true;

  
  constructor(private genericService: GenericService,) { }
  ngOnInit(): void {

    //this.genericService.getAll(Endpoint.CARGA, this.getAllSuccess, this.callFailure);
  }
  private callFailure = (content: any, error: Errors): void => { this.showTable = false; this.errorMessage = error; }
  private getAllSuccess = (content: any): void => {

    this.showFiltro = true;
  }

    

    
}