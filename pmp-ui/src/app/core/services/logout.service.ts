import { LoginRedirectService } from './loginredirect.service';
import { Router } from '@angular/router';
import { ResponseModel } from './../models/response.model';
import { LogoutResponse } from './../models/logoutresponse';
import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import { AppConfigService } from 'src/app/app-config.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {
  constructor(
    public http: HttpClient,
    public router: Router,
    public redirectService: LoginRedirectService,
    public appService: AppConfigService
  ) {}

  logout() {
    this.http
      .delete(`${this.appService.getConfig().baseUrl}v1/partnermanager/logout/user`, {
        observe: 'response'
      })
      .subscribe(
        (res: HttpResponse<ResponseModel<LogoutResponse>>) => {
          if (res.body.response.status === 'Success') {
            this.redirectService.redirect(
              window.location.origin + '/pmp-ui/'
            );
          } else {
            window.alert(res.body.response.message);
          }
        },
        (error: HttpErrorResponse) => {
          window.alert(error.message);
        }
      );
  }
}
