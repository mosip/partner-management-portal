import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HeaderService } from 'src/app/core/services/header.service';

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {

  appConfig: any;

  constructor(private http: HttpClient, private headerService: HeaderService) { }

  async loadAppConfig() {
    this.appConfig = await this.http.get('./assets/config.json').toPromise();
  }

  getConfig() {
  	this.appConfig["primaryLangCode"] = this.headerService.getlanguageCode();
    return this.appConfig;
  }
}
