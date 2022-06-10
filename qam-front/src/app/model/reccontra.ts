export class RecContra {
    email:String;
    passwordConfirma:String;
    encoded:String;
    password:String;


    build(content:any){
        this.email = content.email;
        this.passwordConfirma = content.passwordConfirma;
        this.encoded = content.encoded;
        this.password = content.password;


    }
}