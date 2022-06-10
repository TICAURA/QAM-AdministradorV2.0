import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RestServiceService {

  constructor(private http: HttpClient) {

}

  public get(url: string) {

    let header = new HttpHeaders();


    header = header.set('Content-Type', 'application/json; charset=utf-8').set('Access-Control-Allow-Origin', '*');

    return this.http.get(url);
  }


  public post(url: string, body: any) {

    let header = new HttpHeaders();
    

    header = header.set('Content-Type', 'application/json');


    return this.http.post(url, body, { headers: header } );
  }


  public put(url: string, body: any) {

    let header = new HttpHeaders();


    header = header.set('Content-Type', 'application/json; charset=utf-8').set('Access-Control-Allow-Origin', '*');


    return this.http.put(url, body, { headers: header });
  }


  public delate(url: string, body: any) {

    let header = new HttpHeaders();


    header = header.set('Content-Type', 'application/json; charset=utf-8').set('Access-Control-Allow-Origin', '*');


    return this.http.delete(url+"/"+body.id, { headers: header } );
  }
}
