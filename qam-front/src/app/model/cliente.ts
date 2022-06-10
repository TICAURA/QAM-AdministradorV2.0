export class Cliente {
    idClient:number;
    curp:string;
    rfc:string;
    razon:string;
    email:string;
    dispersorId:number;
    facturadorId:number;
    dispersorName:string;
    facturadorName:string;
    servicioId:number;
    interfazId:number;
    servicioName:string;
    interfazName:string;
    periodoFactura:number;
    mensajeEmailRecuperarContra:string;
    mensajeEmailFactura:string;
    emailSender:string;
    build(content:any){
        this.idClient = content.idClient;
        this.curp = content.curp;
        this.rfc = content.rfc;
        this.razon = content.razon;
        this.email = content.email;
        this.dispersorId = content.dispersorId;
        this.facturadorId = content.facturadorId;
        this.dispersorName = content.dispersorName;
        this.facturadorName = content.facturadorName;
        this.servicioId = content.servicioId;
        this.servicioName = content.servicioName;
        this.interfazId = content.interfazId;
        this.interfazName = content.interfazName;
        this.periodoFactura = content.periodoFactura;
        this.mensajeEmailFactura = content.mensajeEmailFactura;
        this.mensajeEmailRecuperarContra = content.mensajeEmailRecuperarContra;
        this.emailSender = content.emailSender;
    }
}
