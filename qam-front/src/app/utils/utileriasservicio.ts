import { Endpoint } from "./endpoint";

export class UtileriaServicio{

     public getparametros(rootUrl: Endpoint, body: any) {
        switch (String(rootUrl)) {
    
          case Endpoint.CORTE: {
            return "?centroCosto=" + body.centroCostosId + "&&periodicidad=" + body.periodicidad;
          }
    
          case Endpoint.RINCIDENCIAS: {
            
            var datoTemp = ("?");
            if (body.centroCostos != null && body.centroCostos != "") {
              datoTemp += "centroCosto=" + body.centroCostos;
            }
    
            if (body.cliente != null && body.cliente != "") {
              datoTemp += this.getAMPER(datoTemp)+"cliente=" + body.cliente;
            }
    
            if (body.clienteSubcontrato != null && body.clienteSubcontrato != "") {
              datoTemp += this.getAMPER(datoTemp)+"clienteSubcontrato=" + body.clienteSubcontrato;
            }
    
            if (body.fechaInicio != null && body.fechaInicio != "") {
              datoTemp += this.getAMPER(datoTemp)+"fechaInicio=" + body.fechaInicio;
            }
    
            if (body.fechaFin != null && body.fechaFin != "") {
              datoTemp += this.getAMPER(datoTemp)+"fechaFin=" + body.fechaFin;
            }
    
            return (datoTemp);
          }
            
          case Endpoint.RFACTURACION: {
            return "?mes=" + body;
          }

          default: { return; }
    
        }
      }
    
      public getAMPER(url: String) {
        if (url == "?") {
          return "";
        } else {
          return "&";
        }
      }
     
      esEmailValido(email: string):boolean {
        let mailValido = false;
          'use strict';
    
          var EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    
          if (email.match(EMAIL_REGEX)){
            mailValido = true;
          }
        return mailValido;
      }
}