import { Component } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { DataStorageService } from 'src/app/core/services/data-storage.service';
import * as appConstants from 'src/app/app.constants';
import { AppConfigService } from 'src/app/app-config.service';
import { RequestModel } from 'src/app/core/models/request.model';
import { MatDialog } from '@angular/material';
import { DialogComponent } from 'src/app/shared/dialog/dialog.component';
import { Location } from '@angular/common';
import { TranslateService } from '@ngx-translate/core';
import { AuditService } from 'src/app/core/services/audit.service';
import { HeaderService } from 'src/app/core/services/header.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { CenterRequest } from 'src/app/core/models/centerRequest.model';
import { FilterModel } from 'src/app/core/models/filter.model';

@Component({
  selector: 'cert-upload',
  templateUrl: './cert-upload.component.html',
  styleUrls: ['./cert-upload.component.scss']
})
export class CertUploadComponent {
  mapping: any;
  id: string;
  primaryLangCode: string;
  showSpinner = true;
  subscribed: any;
  masterdataType: string;
  popupMessages:any;
  createForm: FormGroup;
  partnerDomain = ["FTM","DEVICE", "AUTH"];
  fileName = "";
  fileData : any;
  showCancelBtn = true;
  showCALabel = false;
  fetchRequest = {} as CenterRequest;
  uploadcertificate:any;
  files: any[] = [];
  langJson:any;

  constructor(
    public formBuilder: FormBuilder,
    public activatedRoute: ActivatedRoute,
    public dataStorageService: DataStorageService,
    public appService: AppConfigService,
    public dialog: MatDialog,
    public location: Location,
    public router: Router,
    public translate: TranslateService,
    public auditService: AuditService, 
    public headerService: HeaderService
  ) {
    this.subscribed = router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.initializeComponent();
      }
    });
  }

  async initializeComponent() {
    this.showSpinner = true;
    this.primaryLangCode = this.headerService.getlanguageCode();
    this.translate
      .getTranslation(this.primaryLangCode)
      .subscribe(response => {
        this.uploadcertificate  = response.uploadcertificate;
        this.popupMessages = response.genericerror;
        this.langJson = response;
      });
    this.activatedRoute.params.subscribe(response => {
      this.id = response.id;
      this.masterdataType = response.type;
      this.mapping = appConstants.masterdataMapping[response.type];
    });
    this.initializeForm();
  }

  initializeForm() {
    
    if(this.masterdataType === "uploadcacert"){
      this.showCALabel = true;
      this.showCancelBtn = false;
    }
    
    this.createForm = this.formBuilder.group({
      partnerId : [''],
      partnerDomain: [''],
      certificateData: [''],
    });
  }

  onFileSelect(event) {
    let self = this;
    if (event.target.files.length > 0) {
      self.fileName = "";
      const file = event.target.files[0];
      if(this.getFileExtension(file.name) === "pem" || this.getFileExtension(file.name) === "cer"){      
        self.createForm.get('certificateData').setValue(file);
        self.fileName = file.name;
        const fileReader: FileReader = new FileReader();
        fileReader.onload = (event: Event) => {
          self.fileData = fileReader.result; // This is valid
        };
        fileReader.readAsText(file); 
      }else{
        self.showErrorPopup(this.uploadcertificate.supportedDoc);
      }
    }

    for (const item of event.target.files) {
      this.files.splice(0, 1);
      this.files.push(item);
    }
  }

  getFileExtension(filename){
    const extension = filename.split('.').pop();
    return extension;
  }

  submit(){
    this.saveData();
  }

  saveData(){
    let self = this;
    let url = "";
    const formData = {};
    if(this.router.url.split('/').includes('ftmdetails')) {
      const filterModel = new FilterModel(
        this.mapping.idKey,
        'equals',
        this.id
      );
      this.fetchRequest.filters = [filterModel];
      this.fetchRequest.languageCode = this.primaryLangCode;
      this.fetchRequest.sort = [];
      this.fetchRequest.pagination = { pageStart: 0, pageFetch: 10 };
      const request = new RequestModel(
        appConstants.registrationCenterCreateId,
        null,
        this.fetchRequest
      );
      this.dataStorageService.getDataByTypeAndId(this.mapping, request).subscribe(response => {
        formData['ftpProviderId'] = response.response.data[0].ftpProviderId;
        formData['ftpChipDeatilId'] = response.response.data[0].ftpChipDetailId;
        formData['isItForRegistrationDevice'] = true;
        formData['certificateData'] = self.fileData.replaceAll("\\n", "\n");
        formData['organizationName'] = response.response.data[0].partnerOrganizationName;
        formData['partnerDomain'] = "FTM";
        const primaryRequest = new RequestModel(
          "",
          null,
          formData
        );
        self.dataStorageService.uploadFTMChipCertificate(primaryRequest).subscribe(response => {
          self.showMessage(response);
        });      
      });      
    }else{
      if(!self.createForm.get('partnerDomain').value){
        self.showErrorPopup(self.uploadcertificate.partnerDomain+self.popupMessages.fieldNameValidation);
      }else if(!self.fileName){
        self.showErrorPopup(self.uploadcertificate.chooseFile+self.popupMessages.fieldNameValidation);
      }else{
        console.log("this.mapping>>>"+JSON.stringify(this.mapping));
        formData['partnerId'] = self.id;
        formData['partnerDomain'] = self.createForm.get('partnerDomain').value;
        formData['certificateData'] = self.fileData.replaceAll("\\n", "\n");
        const primaryRequest = new RequestModel(
          "",
          null,
          formData
        );
        if(this.masterdataType === "uploadcacert"){
          url = "ca/";
        }
        self.dataStorageService.uploadCertificate(primaryRequest, url).subscribe(response => {
          self.showMessage(response);
        });
      }     
    }    
  }

  showErrorPopup(message: string) {
    this.dialog
      .open(DialogComponent, {
        width: '550px',
        data: {
          case: 'MESSAGE',
          title: this.langJson.generickeys.error,
          message: message,
          btnTxt: 'Ok'
        },
        disableClose: true
      });
  }

  showMessage(response){
    let data = {};
    let self = this;
    let responseTemp = response;
    if(response.errors.length > 0){
      data = {
        case: 'MESSAGE',
        title: this.langJson.generickeys.failure,
        message: this.langJson.serverError[response.errors[0].errorCode],
        btnTxt: "DONE"
      };
    }else{
      data = {
        case: 'MESSAGE',
        title: this.langJson.generickeys.success,
        message: this.langJson.generickeys.updatedSuccessfully,
        btnTxt: this.langJson.generickeys.ok
      };
    }
    const dialogRef = self.dialog.open(DialogComponent, {
      width: '650px',
      data
    });
    dialogRef.afterClosed().subscribe(response => {   
      if(responseTemp.errors.length > 0){
        
      }else{
        let url = this.router.url.split('/')[3];
        if(url === "uploadcacert"){
          location.reload();
        }else{
          if(url !== "selfcert"){
            this.router.navigateByUrl(`pmp/resources/${url}/view`);
          }else{
            this.router.navigateByUrl(`pmp/home`);
          }
        }
      }     
    });
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
      if(url !== "selfcert"){
        this.router.navigateByUrl(`pmp/resources/${url}/view`);
      }else{
        this.router.navigateByUrl(`pmp/home`);
      }      
    }
  }
}
