import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfigService } from 'src/app/app-config.service';
import { RequestModel } from '../models/request.model';
import { Observable } from 'rxjs';

const httpOptions = {
    headers:new HttpHeaders({})
};

@Injectable({
    providedIn: 'root'
})

export class PolicyService{

    constructor(public http:HttpClient, public appService:AppConfigService){}

    public BASE_URL = "https://dev..mosip.net/partnermanagement/v1/policies";

    getPolicyDetails(request: RequestModel): Observable<any> {    
        console.log("request>>>"+JSON.stringify(request));
        return this.http.get(this.BASE_URL +"/policies");
    }
    
    getPolicyGroupDetails(request: RequestModel): Observable<any> {
        console.log(JSON.stringify(request));
        return this.http.post(this.BASE_URL + '/policyGroup/search', request, httpOptions);
    }

    getPolicyInfo(request: RequestModel): Observable<any> {    
        console.log(JSON.stringify(request));
        return this.http.get("/policies/" + request.request.id, httpOptions);
      }
      
}