export class CorteAnticipo {
    corteId: Number;
    centroCostosId: Number;
    periodicidad: String;
    fchInicio: Date;
    fchFin: Date;
    fchCorteIncidencias: Date;
    fchPago: Date;
    esActivo: boolean;

    build(content: any) {
        this.corteId = content.corteId;
        this.centroCostosId = content.centroCostosId;
        this.periodicidad = content.periodicidad;
        this.fchInicio = content.fchInicio;
        this.fchFin = content.fchFin;
        this.fchCorteIncidencias = content.fchCorteIncidencias;
        this.fchPago = content.fchPago;
        this.esActivo = content.esActivo;

    }
}
