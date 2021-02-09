import { Injectable } from '@angular/core';
import { v4 as uuid } from 'uuid';
import { CookieService } from 'ngx-cookie-service';
import { AppConfigService } from 'src/app/app-config.service';


@Injectable()
export class LoginRedirectService {

  constructor(private cookie: CookieService, private appService: AppConfigService) { }

  redirect(url: string) {
  	console.log('uuid()>>>' + uuid()+'<<<url>>>' + url);
    const stateParam = uuid();
    this.cookie.set('state', stateParam);
    console.log('returning false login redirect>>>' + this.cookie.get('state'));
    window.location.href = `${this.appService.getConfig().baseUrl}partnermanagement/v1/partners/login/` + btoa(url);
  }
}
