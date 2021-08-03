import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  private Username = '';
  private roles = 'ZONAL_ADMIN,GLOBAL_ADMIN,';
  private zone = '';
  private languageCode = '';

  constructor() { }

  setUsername(username: string) {
    this.Username = username;
  }

  getUsername(): string {
    return this.Username;
  }

  setRoles(roles: string) {
    this.roles = roles;
  }

  getRoles(): string {
    const x = this.roles.split(',');
    x.splice(x.length - 1, 1);
    return x.join(', ').replace(/_/g, ' ');
  }

  getRoleCodes(): string {
    return this.roles;
  }

  setZone(zone: string) {
    this.zone = zone;
  }

  getZone(): string {
    return this.zone;
  }

  setlanguageCode(languageCode: string) {
    this.languageCode = languageCode;
  }

  getlanguageCode(): string {
    if(this.languageCode){
      return this.languageCode;
    }else{
      return "eng";
    }
  }
}
