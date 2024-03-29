
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError} from 'rxjs/operators';
import { AppConfigService } from 'src/app/app-config.service';


/**
 * @description AuthService for Admin App
 * @author Urvil Joshi
 */
@Injectable()
export class AuthService {

  constructor(public router: Router, public http: HttpClient, public appService: AppConfigService) { }
   rolesString: string ;
   token: string;
   roles: string[];
   isAuthenticated(): Observable<boolean> {
    return this.http.get(`${this.appService.getConfig().baseUrl}v1/partnermanager/authorize/admin/validateToken`, { observe: 'response'}).
    pipe(map(res => res.status === 200),
    catchError(error => {
      console.log(error);
      return of(true);
    }));
  }
}