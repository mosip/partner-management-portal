import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HeaderService } from 'src/app/core/services/header.service';

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {

  appConfig: any;

  constructor(public http: HttpClient, public headerService: HeaderService) { }

  async loadAppConfig() {
    this.appConfig = await this.http.get('./assets/config.json').toPromise();
    this.appConfig["primaryLangCode"] = this.headerService.getUserPreferredLanguage();
  }

  getConfig() {
    console.log('before getConfig new change>>>'+this.appConfig);
  	this.appConfig = {'primaryLangCode':'eng'};
    console.log('after getConfig>>>'+this.appConfig);
    return this.appConfig;
  }
}
