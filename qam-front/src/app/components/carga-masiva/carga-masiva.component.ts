import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';

@Component({
  selector: 'app-carga-masiva',
  templateUrl: './carga-masiva.component.html',
  styleUrls: ['./carga-masiva.component.css']
})
export class CargaMasivaComponent implements OnInit {

  errorMessage: Errors;
  showContenido: boolean = true;
  typeEndpoint: Endpoint = Endpoint.CARGACOL
  
  constructor(private genericService: GenericService) { }
  ngOnInit(): void {

    this.genericService.getAll(this.typeEndpoint ,this.getAllSuccess,this.callFailure);
    
  }  

  
  private getAllSuccess = (content: any): void => {

    this.showContenido = true;

  }
  private callFailure = (content: any, error: Errors): void => { 

    this.showContenido = false; 
    this.errorMessage = error; 

  }
    

    
}

