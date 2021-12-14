import {
  Component,
  OnInit,
  ElementRef,
  ViewChildren,
  Input
} from '@angular/core';

import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { DataStorageService } from 'src/app/core/services/data-storage.service';
import { RequestModel } from 'src/app/core/models/request.model';
import { FormGroup, FormBuilder } from '@angular/forms';

import {
  MatKeyboardRef,
  MatKeyboardComponent,
  MatKeyboardService
} from 'ngx7-material-keyboard';

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

@Component({
  selector: 'app-mater-data-common-body',
  templateUrl: './mater-data-common-body.component.html',
  styleUrls: ['./mater-data-common-body.component.scss']
})
export class MaterDataCommonBodyComponent implements OnInit {
  private keyboardRef: MatKeyboardRef<MatKeyboardComponent>;
  @ViewChildren('keyboardRef', { read: ElementRef })
  private attachToElementMesOne: any;
  selectedField: HTMLElement;
  primaryForm: FormGroup;
  secondaryForm: FormGroup;
  popupMessages: any;
  pageName: string;
  disableForms: boolean;
  copyPrimaryWord: any;
  copySecondaryWord: any;

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

  primaryKeyboard: string;
  secondaryKeyboard: string;
  keyboardType: string;
  listBelowSectionData:any;
  fetchRequest = {} as CenterRequest;
  subscribed: any;

  constructor(
    private dataStorageService: DataStorageService,
    private router: Router,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private translateService: TranslateService,
    private headerService: HeaderService
  ) { 
  }

  ngOnInit() {
    this.loadConfig();
  }

  loadConfig() {
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
        this.primaryData = {"name": "", "desc": "", "policies": JSON.stringify({}), "policyGroupName": "", "policyType": "DataShare", "version": "1.1"};
        this.getPolicyGroup("policyGroupName");
      }
      else if(url === "authpolicy"){
        this.pageName = "Auth Policy";        
        this.primaryData = {"name": "", "desc": "", "policies": JSON.stringify({}), "policyGroupName": "", "policyType": "Auth", "version": "1.1"};
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
    console.log("loadSecondaryForm>>>"+JSON.stringify(this.primaryData));
    let url = "";
    if(this.router.url.split('/').length > 7){
      url = this.router.url.split('/')[4];
    }else{
      url = this.router.url.split('/')[3];
    }   
    if(url === "devicedetails"){        
      this.getSbidetails();   
      this.getSbidetailFilterValues("sbiId");       
    }
    else if(url === "policymapping"){
      this.getAPIKeydetails();
    }
  }

  getSbidetailFilterValues(key){
    if(this.router.url.includes("editable")){
      this.showSecondaryForm = true;
      this.showSecondarySBIPanel = true;
    }
    const filterObject = new FilterValuesModel('swVersion', 'unique', '');
    let filterRequest = new FilterRequest([filterObject], this.primaryLang, []);
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
              //console.log("response.response.data>>>"+response.response.data);
            }
          }
        },
        error => {
          //this.displayMessage(this.popupMessages['errorMessages'][1]);
        }
      );
  }

  mapSBIVersion(){
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
            this.sbiId = "";
            this.getSbidetails();
          });
      }else{
        this.showErrorPopup(response.errors[0].message);
      }
    });
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
    this.fetchRequest.filters.push({columnName: "isActive", type: "equals", value: "true"});
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
              this.listBelowSectionData = response.response.data ? [...response.response.data] : [];
            }
          }
        },
        error => {
          //this.displayMessage(this.popupMessages['errorMessages'][1]);
        }
      );
  }

  generateAPIKey(){
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
        this.dropDownValues[key] = response.response.filters;
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
    let filterRequest = new FilterRequest([filterObject], this.primaryLang, []);
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
              element["fieldCode"] = element["name"];
            });
            this.dropDownValues[key] = response.response;
          }
          
        });
      });
  }

  captureValue(event: any, formControlName: string, type: string) { 
    if (type === 'primary') {
      this.primaryData[formControlName] = event.target.value;
    } else if (type === 'secondary') {
      this.secondaryData[formControlName] = event.target.value;
    }else{
      this[formControlName] = event.target.value;
    }
  }

  captureDatePickerValue(event: any, formControlName: string, type: string) {
    let dateFormat = new Date(event.target.value);
    let formattedDate = dateFormat.getFullYear() + "-" + ("0"+(dateFormat.getMonth()+1)).slice(-2) + "-" + ("0" + dateFormat.getDate()).slice(-2) +"T00:00:00.000Z";
    if (type === 'primary') {
      this.primaryData[formControlName] = formattedDate;
    } else if (type === 'secondary') {
      this.secondaryData[formControlName] = formattedDate;
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
        this.primaryData["policyName"] = this.primaryData["policyId"];
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
