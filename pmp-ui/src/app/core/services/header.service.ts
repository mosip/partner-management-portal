import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  private Username = '';
  private roles = 'ZONAL_ADMIN,GLOBAL_ADMIN,';
  private zone = '';
  private languageCode = '';
  private emailId = '';
  private organizationName = '';
  private address = '';
  private contactNumber = '';
  private partnerType = '';
  private notificationLanguage = '';

  constructor() { }

  setOrganizationName(organizationName: string) {
    this.organizationName = organizationName;
  }

  getOrganizationName(): string {
    return this.organizationName;
  }

  setAddress(address: string) {
    this.address = address;
  }

  getAddress(): string {
    return this.address;
  }

  setContactNumber(contactNumber: string) {
    this.contactNumber = contactNumber;
  }

  getContactNumber(): string {
    return this.contactNumber;
  }

  setPartnerType(partnerType: string) {
    this.partnerType = partnerType;
  }

  getPartnerType(): string {
    return this.partnerType;
  }

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

  setNotificationLanguage(notificationLanguage: string) {
    this.notificationLanguage = notificationLanguage;
  }

  getNotificationLanguage() {
    return this.notificationLanguage;
  }

  setZone(zone: string) {
    this.zone = zone;
  }

  getZone(): string {
    return this.zone;
  }

  setEmailId(emailId: string) {
    this.emailId = emailId;
  }

  getEmailId(): string {
    return this.emailId;
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
