import { Component, OnInit } from '@angular/core';
import { Errors } from 'src/app/utils/errors';

@Component({
  selector: 'app-carga-masiva',
  templateUrl: './carga-masiva.component.html',
  styleUrls: ['./carga-masiva.component.css']
})
export class CargaMasivaComponent implements OnInit {

  showTable: boolean = true;
  errorMessage: Errors;
  showFiltro: boolean = true;
  showContenido: boolean = true;

  
  constructor() { }
  ngOnInit(): void {

    //this.genericService.getAll(Endpoint.CARGA, this.getAllSuccess, this.callFailure);
  }

    

    
}
