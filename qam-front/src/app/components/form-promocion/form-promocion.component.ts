import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from 'src/app/service/generic.service';
import { Endpoint } from 'src/app/utils/endpoint';import { Promocion } from 'src/app/model/promocion';
import { Errors } from 'src/app/utils/errors';
import {FormGroup, FormControl} from '@angular/forms';

@Component({
  selector: 'app-form-promocion',
  templateUrl: './form-promocion.component.html',
  styleUrls: ['./form-promocion.component.css']
})
export class FormPromocionComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private genericService:GenericService,private router:Router) { }

  ngOnInit(): void {
  
    this.activatedRoute.queryParams.subscribe((params:any) => {
      this.requestedId = Number(params['requestedId']);
      if(this.requestedId!==-1){this.genericService.get(this.typeEndpoint,this.requestedId ,this.getAllSuccess,this.callFailure);}else{
        this.promocion.tipoPromocion = 0;
      }
    });
  }

  showForm:boolean=true;
  requestedId:number;
  typeEndpoint:Endpoint = Endpoint.PROMOCION;
  promocion:Promocion = new Promocion();
  errorMessage:string;
  nameError:string="";
  porcentajeBeneficio:number;
  range:FormGroup = new FormGroup({
    start: new FormControl(new Date()),
    end: new FormControl(new Date()),
  });

public changePorcentaje(){


  this.promocion.porcentajeBeneficio=this.porcentajeBeneficio/100;

  if(this.promocion.porcentajeBeneficio == undefined||
    this.promocion.porcentajeBeneficio>1 || 
    this.promocion.porcentajeBeneficio<0){alert("Por favor ingrese un porcentaje válido, minimo porcentaje 1%, máximo 100%.");}

}

  private callFailure = (content:any,error:Errors) :void =>{this.showForm=false;this.errorMessage=error;} 
  private callFailureShowMessage = (content:any,error:Errors) :void =>{alert(error);} 

  private getAllSuccess = (content:any):void=>{
    this.promocion = new Promocion();
    this.promocion.build(content);

    if(this.promocion.tipoPromocion === 1){
      this.porcentajeBeneficio=this.promocion.porcentajeBeneficio*100;
    }

    this.range = new FormGroup({
      start: new FormControl(this.promocion.fechaInicio),
      end: new FormControl(this.promocion.fechaFin),
    });
    
  }
  
   save():void{
     this.promocion.fechaInicio = this.range.value.start;
     this.promocion.fechaFin = this.range.value.end;
     if(this.promocion.tipoPromocion === 1){
      
      if(this.promocion.porcentajeBeneficio == undefined||
        this.promocion.porcentajeBeneficio>1 || 
        this.promocion.porcentajeBeneficio<0){alert("Por favor ingrese un porcentaje válido, minimo porcentaje 1%, máximo 100%.");return;}

      if(this.promocion.montoPorcentajeMaximo<0 || !Number.isInteger(this.promocion.montoPorcentajeMaximo)){
        alert("Por favor ingrese un monto máximo para la promoción, la cantidad debe ser un entero mayor a 0.");
        return;
      }
     }
   
    if(this.requestedId!==-1){
      this.genericService.modify(this.typeEndpoint,this.promocion,this.updateSuccess,this.callFailureShowMessage);
    }else{
      this.genericService.insert(this.typeEndpoint,this.promocion,this.saveSuccess,this.callFailureShowMessage);
    }

  }
  private saveSuccess=(content: any):void=>{
    alert("Elemento Guardado con éxito.");
    this.router.navigate(['admin/form-promocion'], { queryParams: { requestedId: content.id  }});
  }

  private updateSuccess = (content: any): void => {
    alert("Elemento actualizado con éxito.");
    this.router.navigate(['/admin/'+this.typeEndpoint]); 
  }

}
