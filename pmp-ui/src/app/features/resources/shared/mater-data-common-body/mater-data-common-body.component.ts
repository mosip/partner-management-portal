import {
  Component,
  OnInit,
  ElementRef,
  ViewChildren,
  Input
} from '@angular/core';

import { Router } from '@angular/router';
import { DataStorageService } from 'src/app/core/services/data-storage.service';
import { RequestModel } from 'src/app/core/models/request.model';
import { FormGroup, FormBuilder } from '@angular/forms';

import { MatKeyboardRef, MatKeyboardComponent } from 'ngx7-material-keyboard';

import { DialogComponent } from 'src/app/shared/dialog/dialog.component';
import { MatDialog } from '@angular/material';
import { Dropdown } from 'src/app/core/models/dropdown';
import { FilterRequest } from 'src/app/core/models/filter-request.model';
import { FilterValuesModel } from 'src/app/core/models/filter-values.model';
import * as appConstants from '../../../../app.constants';
import { OptionalFilterValuesModel } from 'src/app/core/models/optional-filter-values.model';
import { TranslateService } from '@ngx-translate/core';
import { FilterModel } from 'src/app/core/models/filter.model';
import { CenterRequest } from 'src/app/core/models/centerRequest.model';
import { HeaderService } from 'src/app/core/services/header.service';

import * as Ajv from "ajv";

@Component({
  selector: 'app-mater-data-common-body',
  templateUrl: './mater-data-common-body.component.html',
  styleUrls: ['./mater-data-common-body.component.scss']
})
export class MaterDataCommonBodyComponent implements OnInit {
  public keyboardRef: MatKeyboardRef<MatKeyboardComponent>;
  @ViewChildren('keyboardRef', { read: ElementRef })
  public attachToElementMesOne: any;
  selectedField: HTMLElement;
  primaryForm: FormGroup;
  secondaryForm: FormGroup;
  popupMessages: any;
  pageName: string;
  disableForms: boolean;
  copyPrimaryWord: any;
  copySecondaryWord: any;
  searchResult: any[] = [];

  @Input() primaryData: any = {};
  @Input() secondaryData: any;
  @Input() fields: any;

  @Input() primaryLang: string;
  @Input() secondaryLang: string;
  @Input() masterdataType: any;

  dropDownValues = new Dropdown();
  apiKeyLabel:string='';
  sbiId:string='';

  languageNames = {
    ara: 'عربى',
    fra: 'French',
    eng: 'English'
  };
  showSecondaryForm: boolean = false;
  showSecondaryAPIPanel: boolean = false;
  showSecondarySBIPanel: boolean = false;
  isCreateForm:boolean;
  hideContainer:boolean = false;
  primaryKeyboard: string;
  secondaryKeyboard: string;
  keyboardType: string;
  listBelowSectionData:any;
  fetchRequest = {} as CenterRequest;
  subscribed: any;
  displayedColumns: string[] = ['label', 'crDtimes', 'status', 'action'];
  displayedSBIColumns: string[] = ['sbiId', 'action'];
  setreadonly:boolean = false;
  constructor(
    public dataStorageService: DataStorageService,
    public router: Router,
    public formBuilder: FormBuilder,
    public dialog: MatDialog,
    public translateService: TranslateService,
    public headerService: HeaderService
  ) { 
  }

  ngOnInit() {
    this.loadConfig();
  }

  loadConfig() {

    let ajv = new Ajv.default({ allErrors: true });
    let validate = ajv.compile({"properties":{"allowedKycAttributes":{"type":"array","additionalItems":false,"items":{"type":"object","properties":{"attributeName":{"type":"string"}},"required":["attributeName"],"additionalProperties":false}},"allowedAuthTypes":{"type":"array","additionalItems":false,"items":{"type":"object","properties":{"authType":{"type":"string"},"authSubType":{"type":"string"},"mandatory":{"type":"boolean"}},"required":["authType","mandatory"],"additionalProperties":false}},"authTokenType":{"type":"string","enum":["random","partner","policy"]}},"required":["authTokenType","allowedAuthTypes","allowedKycAttributes"],"additionalProperties":false});
    let valid = validate({"dataSharePolicies":{"typeOfShare":"direct","validForInMinutes":"30","transactionsAllowed":"2","encryptionType":"Partner Based","shareDomain":"datashare-service","source":"ID Repository"},"shareableAttributes":[{"attributeName":"fullName","source":[{"attribute":"fullName","filter":[{"language":"eng"}]}],"encrypted":false},{"attributeName":"dateOfBirth","source":[{"attribute":"dateOfBirth"}],"encrypted":false,"format":"YYYY"},{"attributeName":"gender","source":[{"attribute":"gender"}],"encrypted":false},{"attributeName":"phone","source":[{"attribute":"phone"}],"encrypted":false},{"attributeName":"email","source":[{"attribute":"email"}],"encrypted":false},{"attributeName":"addressLine1","source":[{"attribute":"addressLine1"}],"encrypted":false},{"attributeName":"addressLine2","source":[{"attribute":"addressLine2"}],"encrypted":false},{"attributeName":"addressLine3","source":[{"attribute":"addressLine3"}],"encrypted":false},{"attributeName":"region","source":[{"attribute":"region"}],"encrypted":false},{"attributeName":"province","source":[{"attribute":"province"}],"encrypted":false},{"attributeName":"city","source":[{"attribute":"city"}],"encrypted":false},{"attributeName":"UIN","source":[{"attribute":"UIN"}],"encrypted":false},{"attributeName":"postalCode","source":[{"attribute":"postalCode"}],"encrypted":false},{"attributeName":"biometrics","group":"CBEFF","source":[{"attribute":"individualBiometrics","filter":[{"type":"Face"},{"type":"Finger","subType":["Left Thumb","Right Thumb"]}]}],"encrypted":true,"format":"extraction"}]});
    if (!valid){}; /*console.log("validate.errors>>>"+JSON.stringify(validate.errors))*/
    
    let url = "";
    this.isCreateForm = false;
    this.masterdataType = url;
    if(this.router.url.split('/').length > 7){
      url = this.router.url.split('/')[4];
    }else{
      url = this.router.url.split('/')[3];
    }   
    if(!this.primaryData){
      this.isCreateForm = true;
      if(url === "policygroup"){
        this.pageName = "Policy Group";
        this.primaryData = {"desc":"","name":""};
      }
      if(url === "ftmdetails"){
        this.pageName = "FTM Details";
        this.primaryData = {"make":"","model":"", "ftpProviderId":"","isItForRegistrationDevice":true};
        this.getPartnerDropdownValues("FTM_Provider", "ftpProviderId");
      }
      else if(url === "sbidetails"){
        this.pageName = "SBI Details";        
        this.primaryData = {"deviceDetailId":this.router.url.split('/')[6],"swBinaryHash":"", "swCreateDateTime":"", "swExpiryDateTime":"", "swVersion":"","isItForRegistrationDevice":true};
        this.dropDownValues["isItForRegistrationDevice"] = [{fieldID: "True", fieldValue: "True", fieldCode: true},{fieldID: "False", fieldValue: "False", fieldCode: false}];
        this.getPartnerDropdownValues("Device_Provider", "providerId");
      }
      else if(url === "devicedetails"){
        this.pageName = "Device Details";        
        this.primaryData = {"deviceProviderId": "", "deviceSubTypeCode": "", "deviceTypeCode": "", "isItForRegistrationDevice": true, "make": "", "model": "", "partnerOrganizationName": ""};
        this.getPartnerDropdownValues("Device_Provider", "deviceProviderId");
        this.getDeviceType("deviceTypeCode");     
      }
      else if(url === "datasharepolicy"){
        this.pageName = "Data Share Policy";        
        this.primaryData = {"name": "", "desc": "", "policies": "", "policyGroupName": "", "policyType": "DataShare", "version": "1.1"};
        this.getPolicyGroup("policyGroupName");
      }
      else if(url === "authpolicy"){
        this.pageName = "Auth Policy";        
        this.primaryData = {"name": "", "desc": "", "policies": "", "policyGroupName": "", "policyType": "Auth", "version": "1.1"};
        this.getPolicyGroup("policyGroupName");
      }
      else if(url === "policymapping"){
        this.pageName = "Map Policy";
        this.primaryData = {"partnerId":"","policyId":"", "requestDetail" :""};
        this.getPartnerDropdownValues("Policy_Mapping", "partnerId");
      }
    }

    setTimeout(()=>{
        this.loadSecondaryForm();
    }, 500);

    this.translateService
      .getTranslation(this.primaryLang)
      .subscribe(response => {
        this.popupMessages = response;
      });
  }


  loadSecondaryForm(){
    let url = "";
    if(this.router.url.split('/').length > 7){
      url = this.router.url.split('/')[4];
    }else{
      url = this.router.url.split('/')[3];
    }   
    if(url === "devicedetails"){ 
      if(this.primaryData.deviceProviderId){
        this.setreadonly = true;  
      }      
      this.getSbidetails();   
      this.getSbidetailFilterValues("sbiId");       
    }else if(url === "sbidetails"){ 
      if(this.primaryData.providerId){
        this.setreadonly = true;  
      }       
    }else if(url === "ftmdetails"){ 
      if(this.primaryData.ftpProviderId){
        this.setreadonly = true;  
      }      
    }else if(url === "policymapping"){
      this.getAPIKeydetails();
    }
  }

  getSbidetailFilterValues(key){    
    if(this.router.url.includes("editable")){
      this.showSecondaryForm = true;
      this.showSecondarySBIPanel = true;
      if(this.primaryData.approvalStatus === "approved")
        this.hideContainer = true;
    }
    const filterObject = new FilterValuesModel('swVersion', 'unique', '');
    const optionFilter = [{"value": this.primaryData.deviceProviderId,"values": [],"fromValue": "","toValue": "","columnName": "providerId","type": "equals"}, {"value": "true","values": [], "fromValue": "", "toValue": "","columnName": "isActive","type": "equals"}]
    //[{"value": "180522","fromValue": "","toValue": "","columnName": "providerId","type": "equals"}]
    let filterRequest = new FilterRequest([filterObject], this.primaryLang, optionFilter);
    let request = new RequestModel('', null, filterRequest);
    this.dataStorageService
      .getFiltersForAllDropDown('partnermanager/securebiometricinterface', request)
      .subscribe(response => {
        this.dropDownValues[key] = response.response.filters;
      });
  }

  getSbidetails(){
    let self = this;
    const filterModel = new FilterModel(
      "deviceDetailId",
      "equals",
      this.primaryData.id
    );
    this.fetchRequest.filters = [filterModel];
    this.fetchRequest.languageCode = this.headerService.getlanguageCode();
    this.fetchRequest.sort = [];
    this.fetchRequest.pagination = { pageStart: 0, pageFetch: 10 };
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      this.fetchRequest
    );
    this.dataStorageService
      .getSbidetails(request)
      .subscribe(
        response => {
          if (response.response) {
            if (response.response.data) {
              self.listBelowSectionData = response.response.data ? [...response.response.data] : [];
            }else if (response.response.data === null){
              self.listBelowSectionData = null;
            }
          }
        },
        error => {
          //this.displayMessage(this.popupMessages['errorMessages'][1]);
        }
      );
  }

  mapSBIVersion(){
    if(!this.sbiId){
      this.showErrorPopup("SBIVersion"+this.popupMessages.genericerror.fieldNameValidation);
    }else{
      let SBIData = {"deviceDetailId": this.primaryData.id,"sbiId": this.sbiId};  
      let request = new RequestModel(
        "",
        null,
        SBIData
      ); 
      this.dataStorageService.mapSBIVersion(request).subscribe(response => { 
        if (!response.errors || (response.errors.length == 0)) {
          this.showMessage(response.response)
            .afterClosed()
            .subscribe(() => {
              this.dropDownValues["sbiId"] = [];
              this.sbiId = "";
              this.getSbidetails();
              this.getSbidetailFilterValues("sbiId");  
            });
        }else{
          this.showErrorPopup(response.errors[0].message);
        }
      });
    }
  }

  unmapSBIVersion(data){
    let SBIData = {"deviceDetailId": data.deviceDetailId,"sbiId": data.sbiId};  
    let request = new RequestModel(
      "",
      null,
      SBIData
    ); 
    this.dataStorageService.unmapSBIVersion(request).subscribe(response => { 
      if (!response.errors || (response.errors.length == 0)) {
        this.showMessage(response.response)
          .afterClosed()
          .subscribe(() => {
            this.getSbidetails();
          });
      }else{
        this.showErrorPopup(response.errors[0].message);
      }
    });
    
  }

  getAPIKeydetails(){ 
    if(this.router.url.includes("editable")){
      this.showSecondaryForm = true;
      this.showSecondaryAPIPanel = true;
      if(this.primaryData.statusCode === "approved")
        this.hideContainer = true;
    }
    const filterModel = new FilterModel(
      "partnerId",
      "equals",
      this.primaryData.partnerId
    );
    this.fetchRequest.filters = [filterModel];
    this.fetchRequest.languageCode = this.headerService.getlanguageCode();
    this.fetchRequest.sort = [];
    this.fetchRequest.pagination = { pageStart: 0, pageFetch: 10 };
    this.fetchRequest.filters.push({columnName: "policyId", type: "equals", value: this.primaryData.policyId});
    const request = new RequestModel(
      appConstants.registrationCenterCreateId,
      null,
      this.fetchRequest
    );
    this.dataStorageService
      .getAPIKeydetails(request)
      .subscribe(
        response => {
          if (response.response) {
            if (response.response.data) {        
              response.response.data.sort((a, b) => (a.isActive < b.isActive ? -1 : 1));
              response.response.data.sort((a, b) => (a.crDtimes < b.crDtimes ? -1 : 1));      
              response.response.data.forEach(element => {
                element["crDtimes"] = new Date(element["crDtimes"]).toISOString().slice(0,10);
              });
              this.listBelowSectionData = response.response.data ? [...response.response.data] : [];
            }else if (response.response.data === null){
              this.listBelowSectionData = null;
            }
          }
        },
        error => {
          //this.displayMessage(this.popupMessages['errorMessages'][1]);
        }
      );
  }

  generateAPIKey(){
    if(!this.apiKeyLabel){
      this.showErrorPopup("Label"+this.popupMessages.genericerror.fieldNameValidation);
    }else{
      let APIKeyData = {"policyName": this.primaryData.policyName,"label": this.apiKeyLabel};  
      let request = new RequestModel(
        "",
        null,
        APIKeyData
      ); 
      this.dataStorageService.generateAPIKey(this.primaryData.partnerId, request).subscribe(response => { 
        if (!response.errors || (response.errors.length == 0)) {
          this.showMessage("APIKEY : "+response.response.apiKey)
            .afterClosed()
            .subscribe(() => {
              this.apiKeyLabel = "";
              this.getAPIKeydetails();
            });
        }else{
          this.showErrorPopup(response.errors[0].message);
        }
      });
    }    
  }

  deleteAPIKey(data){
    let APIKeyData = {"label": data.label,"status": "De-Active"};  
    let request = new RequestModel(
      "",
      null,
      APIKeyData
    ); 
    this.dataStorageService.deleteAPIKey(this.primaryData, request).subscribe(response => { 
      if (!response.errors || (response.errors.length == 0)) {
        this.showMessage(response.response)
          .afterClosed()
          .subscribe(() => {
            this.getAPIKeydetails();
          });
      }else{
        this.showErrorPopup(response.errors[0].message);
      }
    });    
  }
  
  onKey(value) {
    this.searchResult = this.search(value);
  }

  search(value: string) {
    let filter = value.toLowerCase();
    return this.dropDownValues.partnerTypeCode.primary.filter(option => option.fieldCode.toLowerCase().startsWith(filter));
  }

  getPartnerDropdownValues(partnerTypeCode, key) {
    const filterObject = new FilterValuesModel('name', 'unique', '');
    let optinalFilterObject;
    if(partnerTypeCode !== "Policy_Mapping"){
      optinalFilterObject = [{
        "columnName": "partnerTypeCode",
        "fromValue": "",
        "toValue": "",
        "type": "equals",
        "value": partnerTypeCode
      }];
    }else{
      optinalFilterObject = [{
        "value": "",
        "values": [          
                   "Credential_Partner","Auth_Partner","Online_Verification_Partner","Manual_Adjudication","ABIS_Partner"
        ],
        "fromValue": "",
        "toValue": "",
        "columnName": "partnerTypeCode",
        "type": "in"
      },
      {
        "value": "true",
        "values": [         
                   
        ],
        "fromValue": "",
        "toValue": "",
        "columnName": "isActive",
        "type": "equals"
      }];
    }
    let filterRequest = new FilterRequest([filterObject], this.primaryLang, optinalFilterObject);
    let request = new RequestModel('', null, filterRequest);
    this.dataStorageService
      .getFiltersForAllDropDown('partnermanager/partners', request)
      .subscribe(response => {
         if(response.response.filters)   
        this.searchResult = response.response.filters.sort((a, b) => (a.name && b.name) ? a.id.localeCompare(b.name) : 0);
        this.dropDownValues.partnerTypeCode.primary = response.response.filters.sort((a, b) => (a.name && b.name) ? a.name.localeCompare(b.name) : 0);
      });
  }

  getDeviceType(key) {
    const filterObject = new FilterValuesModel('name', 'unique', '');
    let filterRequest = new FilterRequest([filterObject], this.primaryLang, []);
    filterRequest["purpose"] = "REGISTRATION";
    let request = new RequestModel('', null, filterRequest);
    this.dataStorageService
      .getFiltersForAllDropDown('partnermanager/devicedetail/deviceType', request)
      .subscribe(response => {
        this.dropDownValues[key] = response.response.filters;
      });
  }

  getDeviceDetails(key) {
    const filterObject = new FilterValuesModel('deviceProviderId', 'unique', '');
    let filterRequest = new FilterRequest([filterObject], this.primaryLang, []);
    filterRequest["purpose"] = "REGISTRATION";
    let request = new RequestModel('', null, filterRequest);
    this.dataStorageService
      .getFiltersForAllDropDown('partnermanager/devicedetail', request)
      .subscribe(response => {
        this.dropDownValues[key] = response.response.filters;
      });
  }

  getDeviceSubType(key, deviceType : any) {
    const filterObject = new FilterValuesModel('deviceType', 'unique', deviceType);
    let filterRequest = new FilterRequest([filterObject], this.primaryLang, []);
    filterRequest["purpose"] = "REGISTRATION";
    let request = new RequestModel('', null, filterRequest);
    this.dataStorageService
      .getFiltersForAllDropDown('partnermanager/devicedetail/deviceSubType', request)
      .subscribe(response => {       
        response.response.filters.forEach(element => {
          element["fieldValue"] = element["fieldCode"];
        });
        this.dropDownValues[key] = response.response.filters;
      });
  }

  getPolicyGroup(key) {
    const filterObject = new FilterValuesModel('name', 'unique', '');
    let optinalFilterObject = new OptionalFilterValuesModel('isActive', 'equals', 'true');
    let filterRequest = new FilterRequest([filterObject], this.primaryLang, [optinalFilterObject]);
    filterRequest["purpose"] = "REGISTRATION";
    let request = new RequestModel('', null, filterRequest);
    this.dataStorageService
      .getFiltersForAllDropDown('policymanager/policies/group', request)
      .subscribe(response => {
        this.dropDownValues[key] = response.response.filters;
      });
  }

  getPolicy(key, partnerId : any) {
    this.dataStorageService
      .getPartnerInfo('partnermanager/partners/'+partnerId)
      .subscribe(response => {
        this.dataStorageService
        .getPartnerInfo('policymanager/policies/active/group/'+response.response.policyGroup)
        .subscribe(response => {
          if(response.errors.length > 0){
            this.dropDownValues[key] = [];
            this.showErrorPopup(response.errors[0].message);
          }else{
            response.response.forEach(element => {
              element["fieldValue"] = element["name"];
              element["fieldCode"] = element["id"];
            });
            this.dropDownValues[key] = response.response;
          }
          
        });
      });
  }

  captureValue(event: any, formControlName: string, type: string) { 
    if (type === 'primary') {
      let self = this;
      this.primaryData[formControlName] = event.target.value;
      if(event.target.value.endsWith("}")){
        this.primaryData[formControlName] = JSON.stringify(JSON.parse(self.primaryData[formControlName]), undefined, 4);
      }        
    } else if (type === 'secondary') {
      this.secondaryData[formControlName] = event.target.value;
    }else{
      this[formControlName] = event.target.value;
    }
  }

  captureDatePickerValue(event: any, formControlName: string, type: string) {
    if(event.target.value){
      let dateFormat = new Date(event.target.value);
      let formattedDate = dateFormat.getFullYear() + "-" + ("0"+(dateFormat.getMonth()+1)).slice(-2) + "-" + ("0" + dateFormat.getDate()).slice(-2) +"T00:00:00.000Z";
      if (type === 'primary') {
        this.primaryData[formControlName] = formattedDate;
      } else if (type === 'secondary') {
        this.secondaryData[formControlName] = formattedDate;
      }
    }else{
      if (type === 'primary') {
        this.primaryData[formControlName] = "";
      } else if (type === 'secondary') {
        this.secondaryData[formControlName] = "";
      }
    }
  }

  captureDropDownValue(event: any, formControlName: string, type: string) {      
    if (event.source.selected) {
      this.primaryData[formControlName] = event.source.value;
      if(formControlName === "deviceTypeCode"){
        this.getDeviceSubType("deviceSubTypeCode", event.source.value);
      }else if(formControlName === "partnerId"){
        this.getPolicy("policyId", event.source.value);
      }else{
        this[formControlName] = event.source.value;
        if(formControlName === "policyId"){
          this.primaryData["policyName"] = event.source.viewValue;
        }
      }
    }    
  }  

  submit() {
    let self = this;
    let mandatoryFieldName = [];
    let mandatoryFieldLabel = [];
    for (let i = 0; i < self.fields.length; i++) {
      if (self.fields[i].showInSingleView) {
        if(self.fields[i].ismandatory === "true"){
          mandatoryFieldName.push(self.fields[i].name);  
          mandatoryFieldLabel.push(self.fields[i].label[self.primaryLang]);          
        }
      }
    }
    let len = mandatoryFieldName.length;
    for (let i = 0; i < len; i++) {
      if(!self.primaryData[mandatoryFieldName[i]]){
        self.showErrorPopup(mandatoryFieldLabel[i]+self.popupMessages.genericerror.fieldNameValidation);
        break;
      }else if(len === (i+1)){
        self.executeAPI();
      }
    }
  }

  executeAPI(){
    let url = "";
    if(this.router.url.split('/').length > 7){
      url = this.router.url.split('/')[4];
    }else{
      url = this.router.url.split('/')[3];
    }
    url = appConstants.masterdataMapping[url].apiName;
    if(url === "policymanager/policies/group"){
      if(this.primaryData.id)
        url = url+"/"+this.primaryData.id;
      else
        url = url;
    }
    else if(url === "policymanager/policies"){
      this.primaryData["policies"] =  JSON.parse(this.primaryData["policies"]);
    }
    if(this.primaryData.id || this.primaryData.ftpChipDetailId){ 
      this.primaryData["isItForRegistrationDevice"] = true;
      let request = new RequestModel(
        "",
        null,
        this.primaryData
      ); 
      this.dataStorageService.updateData(url, request).subscribe(response => { 
        if(url === "policymanager/policies"){
          this.primaryData["policies"] =  JSON.stringify(this.primaryData["policies"]);
        }       
        if (!response.errors || (response.errors.length == 0)) {
          let url = this.pageName+" Updated Successfully";
          this.showMessage(url)
            .afterClosed()
            .subscribe(() => {
              this.changePage();
            });
        } else {
          this.showErrorPopup(response.errors[0].message);
        }
      });
    }else{      
      if(url === "partnermanager/partners/apikey/request"){        
        this.primaryData["useCaseDescription"] = this.primaryData["requestDetail"];
        let request = new RequestModel(
          "",
          null,
          this.primaryData
        );
        url = "partnermanager/partners/"+this.primaryData["partnerId"]+"/policy/map";
        this.dataStorageService.requestAPIKey(url, request).subscribe(response => {
          if (!response.errors || (response.errors.length == 0)) {
            let url = response.response.message;
            this.showMessage(url)
              .afterClosed()
              .subscribe(() => {
                this.changePage();
              });
          }else {
            if(response.errors.length > 0){
              this.showErrorPopup(response.errors[0].message);
            }else{
              this.showErrorPopup(response.errors.message);
            }
          }
        });
      }else{
        let request = new RequestModel(
          "",
          null,
          this.primaryData
        );
        this.dataStorageService.createData(url, request).subscribe(response => {
          if(url === "policymanager/policies"){
            this.primaryData["policies"] =  JSON.stringify(this.primaryData["policies"]);
          }
          if (!response.errors || (response.errors.length == 0)) {
            let url = this.pageName+" Created Successfully";
            this.showMessage(url)
              .afterClosed()
              .subscribe(() => {
                this.changePage();
              });
          }else {
            if(response.errors.length > 0){
              this.showErrorPopup(response.errors[0].message);
            }else{
              this.showErrorPopup(response.errors.message);
            }
          }
        });
      }      
    }
  }

  changePage() {
    let url = "";
    if(this.router.url.split('/').length > 7){
      url = this.router.url.split('/')[3];
      let childurl = this.router.url.split('/')[4];
      let id = this.router.url.split('/')[6];
      this.router.navigateByUrl(
      `pmp/resources/${url}/${childurl}/view/${id}`
      );
    }else{
      url = this.router.url.split('/')[3];
      this.router.navigateByUrl(
        `pmp/resources/${url}/view`
      );
    }
  }

  showMessage(message: string) {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '550px',
      data: {
        case: 'MESSAGE',
        title: 'Success',
        message: message,
        btnTxt: 'Ok'
      }
    });
    return dialogRef;
  }

  showErrorPopup(message: string) {
    this.dialog
      .open(DialogComponent, {
        width: '550px',
        data: {
          case: 'MESSAGE',
          title: 'Error',
          message: message,
          btnTxt: 'Ok'
        },
        disableClose: true
      });
  }
}
