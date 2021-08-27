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

  @Input() primaryData: any;
  @Input() secondaryData: any;
  @Input() fields: any;

  @Input() primaryLang: string;
  @Input() secondaryLang: string;
  @Input() masterdataType: any;

  dropDownValues = new Dropdown();

  languageNames = {
    ara: 'عربى',
    fra: 'French',
    eng: 'English'
  };
  showSecondaryForm: boolean;
  isCreateForm:boolean;

  primaryKeyboard: string;
  secondaryKeyboard: string;
  keyboardType: string;

  constructor(
    private dataStorageService: DataStorageService,
    private router: Router,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
  ) { }

  ngOnInit() {
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
      }
      else if(url === "devicedetails"){
        this.pageName = "Device Details";        
        this.primaryData = {"deviceProviderId": "", "deviceSubTypeCode": "", "deviceTypeCode": "", "isItForRegistrationDevice": true, "make": "", "model": "", "partnerOrganizationName": ""};
        this.getPartnerDropdownValues("Device_Provider", "deviceProviderId");
        this.getDeviceType("deviceTypeCode");        
      }
      else if(url === "datasharepolicy"){
        this.pageName = "Data Share Policy";        
        this.primaryData = {"name": "", "desc": "", "policies": {}, "policyGroupName": "", "policyType": "DataShare", "version": "1.1"};
        this.getPolicyGroup("policyGroupName");
      }
      else if(url === "authpolicy"){
        this.pageName = "Auth Policy";        
        this.primaryData = {"name": "", "desc": "", "policies": {}, "policyGroupName": "", "policyType": "Auth", "version": "1.1"};
        this.getPolicyGroup("policyGroupName");
      }
      else if(url === "policymapping"){
        this.pageName = "Map Policy";
        this.primaryData = {"partnerId":"","policyId":"", "requestDetail" :""};
        this.getPartnerDropdownValues("Policy_Mapping", "partnerId");
      }
    }else{
      if(url === "center-type"){
        this.pageName = "Center Type";
      }
    }
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
      optinalFilterObject = [
       {
        "columnName": "partnerTypeCode",
        "fromValue": "",
        "toValue": "",
        "type": "equals",
        "value": "Credential_Partner"
      },
      {
        "columnName": "partnerTypeCode",
        "fromValue": "",
        "toValue": "",
        "type": "equals",
        "value": "Auth_Partner"
      },
    {
        "columnName": "partnerTypeCode",
        "fromValue": "",
        "toValue": "",
        "type": "equals",
        "value": "Online_Verification_Partner"
      },
    {
        "columnName": "partnerTypeCode",
        "fromValue": "",
        "toValue": "",
        "type": "equals",
        "value": "Manual_Adjudication"
      },
    {
        "columnName": "partnerTypeCode",
        "fromValue": "",
        "toValue": "",
        "type": "equals",
        "value": "ABIS_Partner"
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
      }
    }    
  }

  submit() {
    let self = this;
    self.executeAPI();
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
        url = "partnermanager/partners/"+this.primaryData["partnerId"]+"/apikey/request";
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
    console.log();
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
