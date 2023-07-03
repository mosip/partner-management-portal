import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { DataStorageService } from './data-storage.service';
import { MatDialog } from '@angular/material';
import { AppConfigService } from 'src/app/app-config.service';
import { TranslateService } from '@ngx-translate/core';
import { DialogComponent } from 'src/app/shared/dialog/dialog.component';
import { RequestModel } from '../models/request.model';
import * as appConstants from '../../app.constants';
/*import { CenterModel } from '../models/center.model';*/
import { AuditService } from './audit.service';
import { MispModel } from '../models/misp.model';
import { PolicyModel } from '../models/policy.model';
import { PartnerSubmitReq } from '../models/partnersubmitreq.model';
import { PartnerStatus } from '../models/partnerstatus.model';
import { CreateAuthPolicy } from '../models/createauthpolicy.model';
import { AuthPolicies } from '../models/authpolicies.model';
import { AllowedKycAttributes } from '../models/allowedkycattributes.model';

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  actionMessages: any;
  serverMessages: any;
  useCaseDescription : string = 'useCaseDescription';
  partnerSubmitReq : PartnerSubmitReq;
  createAuthPolicy : CreateAuthPolicy;
  partnerStatus : PartnerStatus;
  authPolicies: AuthPolicies[];
  allowedKycAttributes: AllowedKycAttributes[];
  active : string = "Active";
  deactive : string = "De-Active";
  approved : string = "Approved";
  rejected : string = "Rejected";
  descr: string;
  constructor(
    public router: Router,
    public dataService: DataStorageService,
    public dialog: MatDialog,
    public appService: AppConfigService,
    public translate: TranslateService,
    public auditService: AuditService
  ) {
    translate
      .getTranslation(appService.getConfig().primaryLangCode)
      .subscribe(result => {
        this.actionMessages = result.actionMessages;
        this.serverMessages = result.serverError;
      });
  }

  public showMessage(data: any) {
    this.dialog.open(DialogComponent, {
      width: '350px',
      data: {
        case: 'MESSAGE',
        ...data
      }
    });
  }

  public confirmationPopup(type: string, data: any) {
    const obj = {
      case: 'CONFIRMATION',
      title: this.actionMessages[type]['confirmation-title'],
      message: this.actionMessages[type]['confirmation-message'][0] + data + this.actionMessages[type]['confirmation-message'][1],
      yesBtnTxt: this.actionMessages[type]['yesBtnTxt'],
      noBtnTxt: this.actionMessages[type]['noBtnTxt']
    };
    return this.dialog.open(DialogComponent, {
      width: '550px',
      data: obj
    });
  }

  public createMessage(type: string, listItem: string, data?: any) {    
    let obj = {};
    if (type === 'success') {
      if(this.actionMessages[listItem]){
        obj = {
          title: this.actionMessages[listItem]['success-title'],
          message: this.actionMessages[listItem]['success-message'][0] + data + this.actionMessages[listItem]['success-message'][1],
          btnTxt: this.actionMessages[listItem]['btnTxt']
        };
      }else{
        obj = {
          title: this.actionMessages.activate['success-title'],
          message: data,
          btnTxt: this.actionMessages.activate['btnTxt']
        };
      }      
    } else if (type === 'error') {
      if(this.actionMessages[listItem]){
        obj = {
          title: this.actionMessages[listItem]['error-title'],
          message: this.serverMessages[data[0].errorCode],
          btnTxt: this.actionMessages[listItem]['btnTxt']
        };
      }else{
        obj = {
          title: this.actionMessages.activate['error-title'],
          message: this.serverMessages[data[0].errorCode],
          btnTxt: this.actionMessages.activate['btnTxt']
        };
      }
    }
    this.showMessage(obj);
  }

  public showCertificateDetails(data: any) {
    this.dialog.open(DialogComponent, {
      width: '750px',
      data: {
        case: 'MESSAGE',
        ...data
      }
    });
  }

  public showCertificate(listItem: string, data?: any) {
    let obj = {};
    obj = {
      title: 'Certificate',
      message: data,
      btnTxt: this.actionMessages[listItem]['btnTxt']
    };
    this.showCertificateDetails(obj);
  }

  public confirmationPolicy(type: string, data: any) {
    const obj = {
      case: 'CONFIRMATION',
      title: "CONFIRMATION",
      message: "Do you want to Approve the Partner ( "+data.partnerName+" ) to Policy ( "+data.policyName+" ) ?",
      yesBtnTxt: "Approve",
      noBtnTxt: "Reject",
      cancelBtnTxt: "Cancel"
    };
    return this.dialog.open(DialogComponent, {
      width: '750px',
      data: obj
    });
  }

  public approveData(type: string, data: any) {
    let message = "";
    if(data.make){
      message = "Do you want to "+type+" the Make "+data.make+" ?";
    }else{
      message = "Do you want to "+type+" the Version "+data.swVersion+" ?";
    }
    const obj = {
      case: 'CONFIRMATION',
      title: "CONFIRMATION",
      message: message,
      yesBtnTxt: "Yes",
      noBtnTxt: "No"
    };
    return this.dialog.open(DialogComponent, {
      width: '750px',
      data: obj
    });
  }

  public updateMisp(callingFunction: string, data: MispModel) {
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      data
    );
    this.dataService.updateMisp(request).subscribe(
      response => {
        if (!response.errors || response.errors.length === 0) {
          this.createMessage('success', callingFunction, request.request.name);
          this.router.navigateByUrl(this.router.url);
        } else {
          this.createMessage('error', callingFunction, response.errors);
        }
      },
      error => this.createMessage('error', callingFunction)
    );
  }

  public updatePolicy(callingFunction: string, data: PolicyModel) {
    const routeParts = this.router.url.split("/")[3];
    let mapping = appConstants.masterdataMapping[`${routeParts}`];
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      data
    );
    this.dataService.updatePolicyStatus(mapping, request).subscribe(
      response => {
        if (!response.errors || response.errors.length === 0) {
          this.createMessage('success', callingFunction, request.request.name);
          this.router.navigateByUrl(this.router.url);
        } else {
          if(response.errors[0].message === "Cannot activate unpublished policy."){
            this.dataService.publishPolicy(mapping, request).subscribe(
              response => {
                if (!response.errors || response.errors.length === 0) {
                  this.createMessage('success', callingFunction, request.request.name);
                  this.router.navigateByUrl(this.router.url);
                } else {
                  this.createMessage('error', callingFunction, response.errors);
                }
              },
              error => this.createMessage('error', callingFunction, response.errors)
            );
          }else{
            this.createMessage('error', callingFunction, response.errors);
          }          
        }
      },
      error => this.createMessage('error', callingFunction)
    );
  }

  public updateDetails(callingFunction: any, data: PolicyModel) {
    let routeParts = "";
    if(this.router.url.split('/').length >= 6){
      routeParts = this.router.url.split('/')[4];
    }else{
      routeParts = this.router.url.split('/')[3];
    }
    let mapping = appConstants.masterdataMapping[`${routeParts}`];
    let passParam = callingFunction;
    let requestBody = null;
    if(routeParts === "ftmdetails"){
      if(callingFunction === "Activate"){
        passParam = true;
        requestBody = {"approvalStatus": passParam, [mapping.idKey] : data[mapping.idKey], "isItForRegistrationDevice": true}
      }else if(callingFunction === "De-activate"){
        passParam = false;
        requestBody = {"approvalStatus": passParam, [mapping.idKey] : data[mapping.idKey], "isItForRegistrationDevice": true}
      }      
    }else{
      requestBody = {"approvalStatus": passParam, [mapping.idKey] : data[mapping.idKey], "isItForRegistrationDevice": true};
    }
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      requestBody
    );
    this.dataService.updateDetails(mapping, request).subscribe(
      response => {
        if (!response.errors || response.errors.length === 0) {
          this.createMessage('success', callingFunction, response.response);
          this.router.navigateByUrl(this.router.url);
        } else {
          this.createMessage('error', callingFunction, response.errors);
        }
      },
      error => this.createMessage('error', callingFunction)
    );
  }

  edit(data: any, url: string, idKey: string) {
    this.auditService.audit(9, 'ADM-084', {
      buttonName: 'edit',
      masterdataName: this.router.url.split('/')[
        this.router.url.split('/').length - 2
      ]
    });
    url = url.replace('$id', data[idKey]);
    this.router.navigateByUrl(url + '?editable=true');
  }

  policyEdit(data: any, url: string, idKey: string) {
    this.auditService.audit(9, 'ADM-084', {
      buttonName: 'edit',
      masterdataName: this.router.url.split('/')[
        this.router.url.split('/').length - 2
      ]
    });
    url = url.replace('$id', data[idKey]);
    this.router.navigateByUrl(url + '?editable=true');
  }

  partnerEdit(data: any, url: string, idKey: string) {
    this.auditService.audit(9, 'ADM-084', {
      buttonName: 'edit',
      masterdataName: this.router.url.split('/')[
        this.router.url.split('/').length - 2
      ]
    });
    url = url.replace('$id', data[idKey]);
    this.router.navigateByUrl(url + '?editable=true');
  }

  deviceDetail(data: any, url: string, idKey: string) {
    this.auditService.audit(9, 'ADM-084', {
      buttonName: 'edit',
      masterdataName: this.router.url.split('/')[
        this.router.url.split('/').length - 2
      ]
    });
    url = url.replace('$id', data[idKey]);
    this.router.navigateByUrl(url);
  }

  submitPartnerAPIKeyRequest(data: any){
    this.partnerSubmitReq = new PartnerSubmitReq(
      data.policyName,
      this.useCaseDescription
    );
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      this.partnerSubmitReq
    );
    console.log("Request is:"+ request);
    this.dataService.submitRequest(request , data.partnerID).subscribe(dataa =>{

      if (!dataa['errors']) {
        this.createMessage('success', 'activate', data.organizationName);
      } else {
        this.createMessage('error', 'activate');
      }
      
    });
  }

  viewCertificate(data: any){
    this.dataService.viewCertificate(data).subscribe(response =>{      
      if(response.errors.length <= 0) {
        this.showCertificate('activate', response.response.certificateData);
      } else {
        let obj = {};
        obj = {
          title: 'Certificate',
          message: response.errors[0].message,
          btnTxt: 'Ok'
        };
        this.showCertificateDetails(obj);
      }
      
    });
  }

  uploadCertificatepopup(data: any){
    let re = /view/gi;
    let id = "";
    if(this.router.url.split('/').includes('ftmdetails')) {
      id = data.ftpChipDetailId;
    }else{
      id = data.id;
    }
    this.router.navigateByUrl(this.router.url.replace(re, "upload/"+id));
  }

  activatePartner(data: any){
    this.partnerStatus = new PartnerStatus(
      this.active
      //data.partnerStatus,
    );
    const request = new RequestModel(
      appConstants.registrationUpdatePartnerId,
      null,
      this.partnerStatus
    );
    console.log("Request is:"+ request);
    this.dataService.activatePartnerStatus(request , data.partnerID).subscribe(dataa =>{

      if (!dataa['errors']) {
        this.createMessage('success', 'activate', data.organizationName);
      } else {
        this.createMessage('error', 'activate');
      }
      
    });
  }

  activateAPIKeyStatus(data: any){
    this.partnerStatus = new PartnerStatus(
      //data.partnerStatus,
      this.active
    );
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      this.partnerStatus
    );
    console.log("Request is:"+ request);
    this.dataService.deactivateAPIKey(request , data.partnerID, data.partnerAPIKey).subscribe(dataa =>{

      if (!dataa['errors']) {
        this.createMessage('success', 'activate', data.partnerAPIKey);
      } else {
        this.createMessage('error', 'activate');
      }
      
    });
  }

  deactivateAPIKeyStatus(data: any){
    this.partnerStatus = new PartnerStatus(
      //data.partnerStatus,
      this.deactive
    );
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      this.partnerStatus
    );
    console.log("Request is:"+ request);
    this.dataService.deactivateAPIKey(request , data.partnerID, data.partnerAPIKey).subscribe(dataa =>{

      if (!dataa['errors']) {
        this.createMessage('success', 'activate', data.partnerAPIKey);
      } else {
        this.createMessage('error', 'activate');
      }
      
    });
  }

  activatePolicy(data: any, url: string, idKey: string) {
    if (this.router.url.indexOf('single-view') >= 0) {
      this.auditService.audit(10, 'ADM-086', {
        buttonName: 'activate',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 3
        ]
      });
    } else {
      this.auditService.audit(9, 'ADM-089', {
        buttonName: 'activate',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });
    }
    this.confirmationPopup('activate', data.name).afterClosed().subscribe(res => {
      if (res) {
        this.auditService.audit(18, 'ADM-100', 'activate');
        const policyObject = data;
        policyObject.isActive = true;
        policyObject.status = true;
        this.updatePolicy('activate', policyObject);
      } else {
        this.auditService.audit(19, 'ADM-101', 'activate');
      }
    });
  }

  deactivatePolicy(data: any, url: string, idKey: string) {
    if (this.router.url.indexOf('single-view') >= 0) {
      console.log(this.router.url.split('/'));
      this.auditService.audit(10, 'ADM-087', {
        buttonName: 'deactivate',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 3
        ]
      });
    } else {
      this.auditService.audit(9, 'ADM-090', {
        buttonName: 'deactivate',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });
    }
    this.confirmationPopup('deactivate', data.name).afterClosed().subscribe(res => {
      if (res) {
        this.auditService.audit(18, 'ADM-102', 'deactivate');
        const policyObject = data;
        policyObject.isActive = false;
        policyObject.status = false;
        this.updatePolicy('deactivate', policyObject);
      } else {
        this.auditService.audit(19, 'ADM-103', 'deactivate');
      }
    });
  }

  approve(data: any, url: string, idKey: string) {
    if (this.router.url.indexOf('single-view') >= 0) {
      this.auditService.audit(10, 'ADM-086', {
        buttonName: 'approve',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 3
        ]
      });
    } else {
      this.auditService.audit(9, 'ADM-089', {
        buttonName: 'approve',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });
    }
    this.approveData('Approve', data).afterClosed().subscribe(res => {
      if (res) {
        this.auditService.audit(18, 'ADM-100', 'approve');
        const policyObject = data;
        policyObject.status = true;
        this.updateDetails('Activate', policyObject);
      } else {
        this.auditService.audit(19, 'ADM-101', 'approve');
      }
    });
  }

  reject(data: any, url: string, idKey: string) {
    if (this.router.url.indexOf('single-view') >= 0) {
      this.auditService.audit(10, 'ADM-086', {
        buttonName: 'reject',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 3
        ]
      });
    } else {
      this.auditService.audit(9, 'ADM-089', {
        buttonName: 'reject',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });
    }
    this.approveData('Reject', data).afterClosed().subscribe(res => {
      if (res) {
        this.auditService.audit(18, 'ADM-100', 'reject');
        const policyObject = data;
        policyObject.status = true;
        this.updateDetails('De-activate', policyObject);
      } else {
        this.auditService.audit(19, 'ADM-101', 'reject');
      }
    });
  }

  public ApproveorRejectPolicyFunction(callingFunction: string, data: PolicyModel) {
    const routeParts = this.router.url.split("/")[3];
    let mapping = appConstants.masterdataMapping[`${routeParts}`];
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      data
    );
    this.dataService.ApproveorRejectPolicyFunction(mapping, request).subscribe(
      response => {
        if (!response.errors || response.errors.length === 0) {
          let obj = {
            case: 'MESSAGE',
            title: 'Success',
            message: response.response,
            btnTxt: "Ok"
          }
          this.showMessage(obj);
          //this.createMessage('success', callingFunction, request.request.name);
          this.router.navigateByUrl(this.router.url);
        } else {
          let obj = {
            case: 'MESSAGE',
            title: 'Error',
            message: response.errors[0].message,
            btnTxt: "Ok"
          }
          this.showMessage(obj);
        }
      },
      error => this.createMessage('error', callingFunction)
    );
  }

  ApproveorRejectPolicy(data: any, url: string, idKey: string) {
    if (this.router.url.indexOf('single-view') >= 0) {
      this.auditService.audit(10, 'ADM-086', {
        buttonName: 'activate',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 3
        ]
      });
    } else {
      this.auditService.audit(9, 'ADM-089', {
        buttonName: 'activate',
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });
    }
    this.confirmationPolicy('activate', data).afterClosed().subscribe(res => {
      if (res) {
        this.auditService.audit(18, 'ADM-100', 'activate');
        const policyObject = data;
        policyObject.status = "Approved";
        this.ApproveorRejectPolicyFunction('Approved', policyObject);
      } else if(res == false) {
        this.auditService.audit(19, 'ADM-101', 'activate');
        const policyObject = data;
        policyObject.status = "Rejected";
        this.ApproveorRejectPolicyFunction('Rejected', policyObject);
      }
    });
  }
}
