import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RequestModel } from '../models/request.model';
import { URL } from 'src/app/app.constants';
import { Observable } from 'rxjs';
import { AppConfigService } from 'src/app/app-config.service';

@Injectable({
  providedIn: 'root'
})
export class MispService {

  constructor(private http: HttpClient, private appService: AppConfigService) { }

  private BASE_URL = "https://dev.mosip.net/partnermanagement/v1/misps";

  getRegistrationMispDetails(request: RequestModel): Observable<any> {    
    return this.http.get(this.BASE_URL +"/misps");;
  }

  getMispDetails(request: RequestModel): Observable<any> {    
    console.log(JSON.stringify(request));
    return this.http.get(this.BASE_URL +"/misps/mispId/" + request.request.id);
  }
}