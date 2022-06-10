import { LoginRedirectService } from './loginredirect.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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
    window.location.href = `${this.appService.getConfig().baseUrl}v1/partnermanager/logout/user?redirecturi=`+btoa(window.location.href);
  }
}
