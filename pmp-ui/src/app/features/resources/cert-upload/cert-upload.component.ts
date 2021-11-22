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
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
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
  popupMessages = [];
  createForm: FormGroup;
  partnerDomain = ["FTM","DEVICE", "AUTH"];
  fileName = "";
  fileData : any;
  showCancelBtn = true;
  fetchRequest = {} as CenterRequest;

  constructor(
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private dataStorageService: DataStorageService,
    private appService: AppConfigService,
    private dialog: MatDialog,
    private location: Location,
    private router: Router,
    private translate: TranslateService,
    private auditService: AuditService, 
    private headerService: HeaderService
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
      .subscribe(response => (this.popupMessages = response.singleView));
    this.activatedRoute.params.subscribe(response => {
      this.id = response.id;
      this.masterdataType = response.type;
      this.mapping = appConstants.masterdataMapping[response.type];
    });
    this.initializeForm();
  }

  initializeForm() {
    
    if(this.masterdataType === "uploadcacert"){
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
      const file = event.target.files[0];
      self.createForm.get('certificateData').setValue(file);
      self.fileName = file.name;
      const fileReader: FileReader = new FileReader();
      fileReader.onload = (event: Event) => {
        self.fileData = fileReader.result; // This is valid
      };
      fileReader.readAsText(file);
    }
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

  showMessage(response){
    let data = {};
    let self = this;
    let responseTemp = response;
    if(response.errors.length > 0){
      data = {
        case: 'MESSAGE',
        title: "Failure !",
        message: response.errors[0].message,
        btnTxt: "DONE"
      };
    }else{
      data = {
        case: 'MESSAGE',
        title: "Success",
        message: response.response.status,
        btnTxt: "DONE"
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
