import { Injectable } from '@angular/core';
import { v4 as uuid } from 'uuid';
import { CookieService } from 'ngx-cookie-service';
import { AppConfigService } from 'src/app/app-config.service';


@Injectable()
export class LoginRedirectService {

  constructor(private cookie: CookieService, private appService: AppConfigService) { }

  redirect(url: string) {
    console.log("uuid() new>>>"+uuid());
    const stateParam = uuid();
    document.cookie = "state="+stateParam+"; Max-Age=${60*60*24}; SameSite=Strict";
    console.log("this.cookie.get>>>"+this.cookie.get("state"));
    window.location.href = `${this.appService.getConfig().baseUrl}v1/partnermanager/login/` + btoa(url);
  }
}
