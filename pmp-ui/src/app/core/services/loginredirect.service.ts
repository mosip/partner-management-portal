import { Injectable } from '@angular/core';
import { v4 as uuid } from 'uuid';
import { CookieService } from 'ngx-cookie-service';
import { AppConfigService } from 'src/app/app-config.service';


@Injectable()
export class LoginRedirectService {

  constructor(private cookie: CookieService, private appService: AppConfigService) { }

  redirect(url: string) {
    console.log("uuid()>>>"+uuid());
    const stateParam = uuid();
    console.log("stateParam>>>"+stateParam);
    this.cookie.set('state', stateParam);
    window.location.href = `${this.appService.getConfig().baseUrl}v1/partnermanager/login/` + btoa(url);
  }
}
