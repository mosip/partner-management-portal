import { Component, OnInit } from '@angular/core';
import { AuditService } from '../services/audit.service';
import { DataStorageService } from '../services/data-storage.service';
import { HeaderService } from '../services/header.service';
import { RequestModel } from 'src/app/core/models/request.model';
import { DialogComponent } from 'src/app/shared/dialog/dialog.component';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/core/services/common.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
	partnerIdDetail: any;
	labels:any;
  constructor(public dialog: MatDialog, public dataService: DataStorageService, public headerService: HeaderService, public auditService: AuditService, public router: Router, public commonService: CommonService, public translateService : TranslateService) { }

 	ngOnInit() {

	  	if (this.headerService.getRoles()) {  		
				let partnerId = this.headerService.getUsername();
				let primaryLang = this.headerService.getlanguageCode();
				let self = this;
				this.translateService
	      .getTranslation(primaryLang)
	      .subscribe(response => {
	        this.labels = response["dashboard"];
	      });
	      this.dataService
	      .partnerEmailVerify(this.headerService.getEmailId())
	      .subscribe(({ response }) => {
	        if(response.emailExists){
						this.dataService.getPartnerIdDetail(partnerId).subscribe(response => {
							this.partnerIdDetail = response.response;
						});
					}else{
						if(this.headerService.getPartnerType()){
							if(response.policyRequiredPartnerTypes.indexOf(this.headerService.getPartnerType()) > -1) {
							  self.openFilterDialog({"case": "filter"});
							}else{
							  let request = new RequestModel(
					        "",
					        null,
					        {"partnerId": this.headerService.getUsername(), "organizationName": this.headerService.getOrganizationName(), "address": this.headerService.getAddress(), "contactNumber": this.headerService.getContactNumber(), "emailId": this.headerService.getEmailId(), "partnerType": this.headerService.getPartnerType(), "langCode": this.headerService.getNotificationLanguage()}
					      );   
					      this.dataService
						      .partnerRegistration(request)
						      .subscribe(({ response }) => {
						      	location.reload();			      	
						      });
							}
						}
					}
				});
	    }
  	}

  openFilterDialog(action): void {
    this.dialog
      .open(DialogComponent, {
        data: action,
        width: '700px',
        autoFocus: false,
        disableClose: true,
        restoreFocus: false
      })
      .afterClosed()
      .subscribe(result => {
        console.log('dislog is closed');
      });
  }

  redirecttoupload(){
  	this.router.navigateByUrl('/pmp/resources/selfcert/upload/'+this.partnerIdDetail.partnerID);
  }

  viewCertificate(){
  	let data = {"id": this.partnerIdDetail.partnerID};
		this.commonService["viewCertificate"](data);
  }  
}
