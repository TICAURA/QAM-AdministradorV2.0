import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Nom035Reporte } from 'src/app/model/nom035/nom035-reporte';
import { Nom035Service } from 'src/app/service/nom035.service';
import { Endpoint } from 'src/app/utils/endpoint';
import { Errors } from 'src/app/utils/errors';

@Component({
  selector: 'app-nom-reporte',
  templateUrl: './nom-reporte.component.html',
  styleUrls: ['./nom-reporte.component.css']
})
export class NomReporteComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private nominaService: Nom035Service,private router:Router) { }

  ngOnInit(): void {
    this.cuestionarioId = Number(this.activatedRoute.snapshot.queryParamMap.get('cuestionarioId'));
    this.colaboradorId = Number(this.activatedRoute.snapshot.queryParamMap.get('colaborador'));

    this.nominaService.getReporte(this.endpoint,this.colaboradorId,this.cuestionarioId,this.getSuccess,this.callFailure);
  }

  endpoint:Endpoint = Endpoint.NOMINA035;
  cuestionarioId:number;
  colaboradorId:number;
  showForm: boolean = true;
  nom035:Nom035Reporte;
  errorMessage: string;

  private getSuccess = (content: any): void => {
    
    this.nom035 = new Nom035Reporte();
    this.nom035.build(content);

  }

  private callFailure = (content: any, error: Errors): void => { this.showForm = false; this.errorMessage = error; }
  private callFailureShowMessage = (content: any, error: Errors): void => { alert(error); }

  public reporte(tipo:string){
    this.nominaService.downloadFile("nom035/reportes/"+tipo+"/"+this.colaboradorId+"/"+this.cuestionarioId,"ReporteNOM035_"+tipo+"_"+this.nom035.nombre.replace(" ","_"));
  }

}
