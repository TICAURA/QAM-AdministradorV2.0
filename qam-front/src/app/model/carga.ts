export class Carga{

    id_det_carga_aguilas:number;
    id_carga_masiva:number;
    name:string;
    father_last_name:string;
    mother_last_name:string;
    email:string;
    plate:string;
    phone_number:string;
    document_number:string;
    Amount:number;

    build(content:any){
        this.id_det_carga_aguilas = content.id_det_carga_aguilas;
        this.id_carga_masiva = content.id_carga_masiva;
        this.name = content.name;
        this.father_last_name = content.father_last_name;
        this.mother_last_name = content.mother_last_name;
        this.plate = content.plate;
        this.email = content.email;
        this.phone_number = content.phone_number;
        this.document_number = content.document_number;
        this.Amount = content.Amount;


    }

}